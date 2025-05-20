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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esemkareceipt.adapter.CategoriesAdapter;
import com.example.esemkareceipt.model.Category;
import com.example.esemkareceipt.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    TextView v;

    private final ArrayList<Category> categoryArrayList = new ArrayList<>();
    CategoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


//        v = findViewById(R.id.textView);
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoriesAdapter(categoryArrayList);
        rv.setAdapter(adapter);

        new CategoryTask().execute();

    }


    private class CategoryTask extends AsyncTask<Void, Void, List<Category>> {

        @Override
        protected List<Category> doInBackground(Void... voids) {

            try {
                URL url = new URL("http://10.0.2.2:5000/api/categories");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }

                    var jsonToArray = new JSONArray(response.toString());
                    List<Category> categories = new ArrayList<>();

                    for (int i = 0; i < jsonToArray.length(); i++) {
                        JSONObject obj = jsonToArray.getJSONObject(i);
                        categories.add(new Category(obj.getInt("id"),  obj.getString("name"),  obj.getString("icon")));
                    }

                    return categories;
                }

                conn.disconnect();
            } catch (Exception e) {
                Log.e("ME_ERROR", "Error: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            if (categories != null) {
                adapter.updateCategories(categories);
            } else {
                Toast.makeText(DashboardActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
            }
        }
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