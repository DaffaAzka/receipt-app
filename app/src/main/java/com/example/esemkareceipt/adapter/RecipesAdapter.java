package com.example.esemkareceipt.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esemkareceipt.R;
import com.example.esemkareceipt.model.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {


    private ArrayList<Recipe> recipes;
    private RecipesAdapter.OnItemClickListener clickListener;

    public interface OnItemClickListener  {
        void onItemClick(Recipe recipe);
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView recipes_name, recipes_desc;
        ImageView recipes_image;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipes_name =itemView.findViewById(R.id.recipes_name);
            recipes_desc =itemView.findViewById(R.id.recipes_desc);
            recipes_image = itemView.findViewById(R.id.recipes_image);
        }
    }


    public RecipesAdapter(ArrayList<Recipe> recipes, RecipesAdapter.OnItemClickListener clickListener) {
        this.recipes = recipes;
        this.clickListener = clickListener;
    }



    @NonNull
    @Override
    public RecipesAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_list, parent, false);
        return new RecipesAdapter.RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.RecipeViewHolder holder, int position) {

        Recipe recipe = recipes.get(position);

        holder.recipes_name.setText(recipe.getTitle());
        holder.recipes_desc.setText(recipe.getDescription());

        new RecipesAdapter.ImageLoaderTask(holder.recipes_image).execute(recipe.getImage());

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(recipe);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateRecipes(ArrayList<Recipe> newC) {
        recipes = newC;
        notifyDataSetChanged();
    }

    private static class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {
        @SuppressLint("StaticFieldLeak")
        private final ImageView imageView;

        public ImageLoaderTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL("http://10.0.2.2:5000/images/recipes/" + urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

}
