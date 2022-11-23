package com.example.cocktailsoftheworld;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Cocktail> allCocktails = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



        // Start new thread
        Thread thread = new getCocktails();
        thread.start();

        // Wait for Thread to die
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread = new loadAllIcons();
        thread.start();


    }

    // Get info about all drinks
    class getCocktails extends Thread {

        @Override
        public void run() {

            char startingLetter = 'a';
            int count = 0;

            while (startingLetter <= 'z') {

                try {
                    URL url = new URL("https://www.thecocktaildb.com/api/json/v1/1/search.php?f=" + startingLetter);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuilder stringBuilder = new StringBuilder();

                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        stringBuilder.append((char) cp);

                    }

                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());

                    if(jsonObject.getString("drinks") != "null") {

                        JSONArray jsonArray = new JSONArray(jsonObject.getString("drinks"));

                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject cocktail = jsonArray.getJSONObject(i);

                            int id = cocktail.getInt("idDrink");
                            String name = cocktail.getString("strDrink");
                            String altName = cocktail.getString("strDrinkAlternate");
                            String tags = cocktail.getString("strTags");
                            String category = cocktail.getString("strCategory");
                            String isAlcoholic = cocktail.getString("strAlcoholic");
                            String glass = cocktail.getString("strGlass");
                            String instructions = cocktail.getString("strInstructions");
                            URL imageUrl = new URL(cocktail.getString("strDrinkThumb"));

                            ArrayList<String> ingredients = new ArrayList<>();
                            ArrayList<String> measures = new ArrayList<>();
                            for(int j = 1; j < 16; j++) {
                                if(cocktail.getString(("strIngredient" + j)) == "null") {
                                    break;
                                }
                                String ingredient = cocktail.getString(("strIngredient" + j));
                                String measure = cocktail.getString(("strMeasure" + j));
                                ingredients.add(ingredient);
                                measures.add(measure);

                            }

                            Cocktail newCocktail = new Cocktail();
                            newCocktail.setId(id);
                            newCocktail.setName(name);
                            newCocktail.setAltName(altName);
                            newCocktail.setTags(tags);
                            newCocktail.setCategory(category);
                            newCocktail.setIsAlcoholic(isAlcoholic);
                            newCocktail.setGlass(glass);
                            newCocktail.setInstructions(instructions);
                            newCocktail.setImageUrl(imageUrl);
                            newCocktail.setIngredients(ingredients);
                            newCocktail.setMeasures(measures);

                            allCocktails.add(newCocktail);

                        }

                    }

                    inputStream.close();
                    httpURLConnection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startingLetter++;
            }
        }
    }

    class loadAllIcons extends Thread {

        @Override
        public void run() {

            LinearLayout linearLayout = findViewById(R.id.linear_layout);

            for(int i = 0; i < allCocktails.size(); i++) {
                Cocktail cocktail = allCocktails.get(i);

                URL imageUrl = cocktail.getImageUrl();
                String name = cocktail.getName();

                ViewGroup icon = (ViewGroup) getLayoutInflater().inflate(R.layout.drink_icon, null);

                TextView textView = (TextView) icon.getChildAt(1);
                ImageView imageView = (ImageView) icon.getChildAt(0);

                textView.setText(name);

                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Log.e(TAG, "Coś poszło nie tak");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        linearLayout.addView(icon);

                        icon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, DrinkPage.class);
                                intent.putExtra("name", cocktail.getName());
                                intent.putExtra("imgSrc", cocktail.getImageUrl().toString());
                                intent.putExtra("isAlcoholic", cocktail.getAlcoholic());
                                intent.putExtra("categories", cocktail.getCategory());
                                intent.putExtra("glass", cocktail.getGlass());
                                intent.putExtra("instructions", cocktail.getInstructions());
                                intent.putExtra("ingredients", cocktail.getIngredients());
                                intent.putExtra("measures", cocktail.getMeasures());
                                startActivity(intent);
                            }
                        });
                    }
                });
            }

        }
    }
}