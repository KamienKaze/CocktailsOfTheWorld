package com.example.cocktailsoftheworld;

import java.net.URL;
import java.util.ArrayList;

public class Cocktail {
    private int id;
    private String name;
    private String altName;
    private String tags;
    private String category;
    private String isAlcoholic;
    private String glass;
    private String instructions;
    private URL imageUrl;
    private ArrayList<String> ingredients;
    private ArrayList<String> measures;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getAltName() {
        return altName;
    }
    public String getTags() {
        return tags;
    }
    public String getCategory() {
        return category;
    }
    public String getAlcoholic() {
        return isAlcoholic;
    }
    public String getGlass() {
        return glass;
    }
    public String getInstructions() {
        return instructions;
    }
    public URL getImageUrl() {
        return imageUrl;
    }
    public ArrayList<String> getIngredients() {
        return ingredients;
    }
    public ArrayList<String> getMeasures() {
        return measures;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAltName(String altName) {
        this.altName = altName;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setIsAlcoholic(String isAlcoholic) {
        this.isAlcoholic = isAlcoholic;
    }
    public void setGlass(String glass) {
        this.glass = glass;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public void setImageUrl(URL imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
    public void setMeasures(ArrayList<String> measures) {
        this.measures = measures;
    }
}
