package com.example.esemkareceipt;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esemkareceipt.adapter.CategoriesAdapter;
import com.example.esemkareceipt.adapter.RecipesAdapter;
import com.example.esemkareceipt.model.Category;
import com.example.esemkareceipt.model.Recipe;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecipeActivity extends AppCompatActivity implements RecipesAdapter.OnItemClickListener {

    private final ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    RecipesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // EXTRA
        var i = getIntent();
        var b = i.getExtras();

        if (b != null) {
            String id = (String) b.get("CATEGORY_ID");
            RecyclerView rv = findViewById(R.id.rv_recipes);
            rv.setLayoutManager(new LinearLayoutManager(this));
            adapter = new RecipesAdapter(recipeArrayList, this);
            rv.setAdapter(adapter);

            new RecipesTask().execute(id);
        }
    }

    @Override
    public void onItemClick(Recipe recipe) {

    }


    private class RecipesTask extends AsyncTask<String, Void, ArrayList<Recipe>> {

        @Override
        protected ArrayList<Recipe> doInBackground(String... strings) {

            String c = strings[0];

            try {

                URL url = new URL("http://10.0.2.2:5000/api/recipes?categoryId=" + c);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    var br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    var r = new StringBuilder();
                    String line;

                    while((line = br.readLine()) != null) {
                        r.append(line);
                    }


                    var jta = new JSONArray(r.toString());
                    ArrayList<Recipe> recipes = new ArrayList<>();

                    for (int i = 0; i < jta.length(); i++) {
                        var obj = jta.getJSONObject(i);

                        var categoryObj = obj.getJSONObject("category");

                        Category category = new Category(categoryObj.getInt("id"),
                                categoryObj.getString("name"),
                                categoryObj.getString("icon"));

                        List<String> ingredients = new ArrayList<>();

                        JSONArray ingredientsArray = obj.optJSONArray("ingredients");

                        if (ingredientsArray != null) {
                            for (int j = 0; j < ingredientsArray.length(); j++) {
                                ingredients.add(ingredientsArray.getString(j));
                            }
                        }

                        List<String> steps = new ArrayList<>();
                        JSONArray stepsArray = obj.optJSONArray("steps");
                        if (stepsArray != null) {
                            for (int j = 0; j < stepsArray.length(); j++) {
                                steps.add(stepsArray.getString(j));
                            }
                        }

                        recipes.add(new Recipe(obj.getInt("id"), category, obj.getString("title"), obj.getString("description"), obj.getString("image"), obj.getInt("priceEstimate"), obj.getInt("cookingTimeEstimate"), ingredients, steps));
                    }

                    return recipes;

                }


            } catch (Exception e) {
                Log.e("RECIPES_ERROR", "Error: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {

            if (recipes != null) {
                adapter.updateRecipes(recipes);
            } else {
                Toast.makeText(RecipeActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
            }

        }
    }


}