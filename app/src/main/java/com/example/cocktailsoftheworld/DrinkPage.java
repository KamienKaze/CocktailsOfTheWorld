package com.example.cocktailsoftheworld;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DrinkPage extends AppCompatActivity {

    String url;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_page);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();



        String name = intent.getStringExtra("name");
        url = intent.getStringExtra("imgSrc");
        String isAlcoholic = intent.getStringExtra("isAlcoholic");
        String categories = intent.getStringExtra("categories");
        String glass = intent.getStringExtra("glass");
        String instructions = intent.getStringExtra("instructions");
        ArrayList<String> ingredients = intent.getStringArrayListExtra("ingredients");
        ArrayList<String> measures = intent.getStringArrayListExtra("measures");

        TextView textName = findViewById(R.id.textName);
        imageView = findViewById(R.id.imageViewThumb);
        TextView textCategories = findViewById(R.id.textCategory);
        TextView textGlass = findViewById(R.id.textGlass);
        TextView textInstructions = findViewById(R.id.textInstruction);
        TableLayout tableLayout = findViewById(R.id.table);

        //tabela
        for(int i = 0; i < ingredients.size(); i++) {
            ViewGroup row = (ViewGroup) getLayoutInflater().inflate(R.layout.table_row, null);

            TextView ingredient = (TextView) row.getChildAt(0);
            TextView measure = (TextView) row.getChildAt(1);

            ingredient.setText(ingredients.get(i));
            measure.setText(measures.get(i));

            tableLayout.addView(row);
        }


        textName.setText(name);
        Thread thread = new loadIcon();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textCategories.setText(categories + ", " + isAlcoholic);
        textGlass.setText(glass);
        textInstructions.setText(instructions);

    }

    class loadIcon extends Thread {
        @Override
        public void run() {
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {

            }
        }
    }
}