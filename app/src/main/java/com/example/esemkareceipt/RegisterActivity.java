package com.example.esemkareceipt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.esemkareceipt.model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText username, fullName, password, date;
        Button btn = findViewById(R.id.buttonRegister);

        username = findViewById(R.id.et_username);
        fullName = findViewById(R.id.et_fullname);
        password = findViewById(R.id.editTextTextPassword);
        date = findViewById(R.id.et_date);

        btn.setOnClickListener(v -> {

            String a, b, c, d;
            a = username.getText().toString();
            b = fullName.getText().toString();
            c = password.getText().toString();
            d = date.getText().toString();

            new RegisterTask().execute(a, b, c, d);

        });
    }

    private class RegisterTask extends AsyncTask<String, Void, User> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected User doInBackground(String... strings) {

            String username = strings[0];
            String fullName = strings[1];
            String password = strings[2];
            String instant = strings[3];

            String formattedDate = LocalDate.parse(instant, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    .atStartOfDay()
                    .toInstant(ZoneOffset.UTC)
                    .toString();


            try {

                URL url = new URL("http://10.0.2.2:5000/api/sign-up");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                var jsonBody = "{" +
                        "\"username\":\"" + username + "\"," +
                        "\"fullName\":\"" + fullName + "\"," +
                        "\"password\":\"" + password + "\"," +
                        "\"dateOfBirth\":\"" +  formattedDate + "\"" +
                        "}";

                Log.d("REGISTER_DEBUG", "JSON Body: " + jsonBody);

                try(OutputStream os = conn.getOutputStream()) {
                    os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
                }

                Log.d("REGISTER_DEBUG", "Server Response Code: " + conn.getResponseCode());

                if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    JSONObject json = new JSONObject(sb.toString());
                    return  new User(
                            Integer.parseInt(json.optString("id")),
                            json.optString("username"),
                            json.optString("fullName"),
                            json.optString("image"),
                            json.optString("password")
                    );

                }

                conn.disconnect();


            } catch (Exception e) {
                Log.e("REGISTER_ERROR", "Error: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            if (user != null) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Register gagal!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}