package com.example.esemkareceipt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.esemkareceipt.model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etEmail = findViewById(R.id.editTextText2);
        EditText etPassword = findViewById(R.id.editTextText3);
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(v -> {
            String username = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            new LoginTask().execute(username, password);
        });


    }

    private class LoginTask extends AsyncTask<String, Void, User> {
            @Override
            protected User doInBackground(String... params) {
                String username = params[0];
                String password = params[1];

                try {
                    URL url = new URL("http://10.0.2.2:5000/api/sign-in");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    String jsonBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
                    try (OutputStream os = conn.getOutputStream()) {
                        os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
                    }

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            response.append(line);
                        }

                        JSONObject json = new JSONObject(response.toString());
                        return new User(
                                Integer.parseInt(json.optString("id")),
                                json.optString("username"),
                                json.optString("fullName"),
                                json.optString("image"),
                                json.optString("password")
//                                json.optString("dateOfBirth")
                        );
                    }

                    conn.disconnect();
                } catch (Exception e) {
                    Log.e("LOGIN_ERROR", "Error: " + e.getMessage());
                }
                return null;
            }
            @Override
            protected void onPostExecute(User user) {
                if (user != null) {
                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Login gagal!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
