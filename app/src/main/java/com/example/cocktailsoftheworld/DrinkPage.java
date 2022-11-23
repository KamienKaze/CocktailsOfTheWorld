package com.example.cocktailsoftheworld;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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
        ArrayList<String> ingridients = intent.getStringArrayListExtra("ingredients");
        ArrayList<String> measures = intent.getStringArrayListExtra("measures");

        TextView textName = findViewById(R.id.textName);
        imageView = findViewById(R.id.imageViewThumb);
        //TextView textAlcoholic = findViewById(R.id.textIsAlcoholic);
        //TextView textCategories = findViewById(R.id.textCategories);
        //TextView textGlass = findViewById(R.id.textGlass);
        //TextView textInstructions = findViewById(R.id.textInstructions);

        //tabela


        textName.setText(name);
        Thread thread = new loadIcon();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //textAlcoholic.setText(isAlcoholic);
        //textCategories.setText(categories);
        //textGlass.setText(glass);
        //textInstructions.setText(instructions);

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