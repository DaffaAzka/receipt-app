package com.example.esemkareceipt.model;

import java.util.List;

public class Recipe {
    int id;
    Category category;
    String title;
    String description;
    String image;
    int priceEstimate;
    int cookingTimeEstimate;
    List<String> ingredients;
    List<String> steps;

    public Recipe(int id, Category category, String title, String description, String image, int priceEstimate, int cookingTimeEstimate, List<String> ingredients, List<String> steps) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.description = description;
        this.image = image;
        this.priceEstimate = priceEstimate;
        this.cookingTimeEstimate = cookingTimeEstimate;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPriceEstimate() {
        return priceEstimate;
    }

    public void setPriceEstimate(int priceEstimate) {
        this.priceEstimate = priceEstimate;
    }

    public int getCookingTimeEstimate() {
        return cookingTimeEstimate;
    }

    public void setCookingTimeEstimate(int cookingTimeEstimate) {
        this.cookingTimeEstimate = cookingTimeEstimate;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}
