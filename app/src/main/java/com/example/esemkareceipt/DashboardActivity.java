package com.example.esemkareceipt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

public class DashboardActivity extends AppCompatActivity {

    TextView v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        v = findViewById(R.id.textView);
//        v.setText("bla");

//        new ProfileTask().execute();

    }

//    private class ProfileTask extends AsyncTask<Void, Void, User> {
//        @Override
//        protected User doInBackground(Void... voids) {
//
//            try {
//                URL url = new URL("http://10.0.2.2:5000/api/me");
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                    StringBuilder response = new StringBuilder();
//                    String line;
//
//                    while ((line = br.readLine()) != null) {
//                        response.append(line);
//                    }
//
//                    JSONObject json = new JSONObject(response.toString());
//                    return new User(
//                            Integer.parseInt(json.optString("id")),
//                            json.optString("username"),
//                            json.optString("fullName"),
//                            json.optString("image"),
//                            json.optString("password")
//                    );
//                }
//
//                conn.disconnect();
//            } catch (Exception e) {
//                Log.e("ME_ERROR", "Error: " + e.getMessage());
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(User user) {
//            if (user != null) {
////                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
////                finish();
//                v.setText(user.toString());
//            }
//        }
//    }

}