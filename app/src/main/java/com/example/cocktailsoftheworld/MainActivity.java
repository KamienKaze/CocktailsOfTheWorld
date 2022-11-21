package com.example.cocktailsoftheworld;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    ArrayList<JSONObject> jsonObjects = new ArrayList<>();
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        linearLayout = findViewById(R.id.layout_list);

        new getDrinks().start();


    }

    public void loadDrinkIcons(ArrayList<JSONObject> drinks) {

        for(int i = 0; i < drinks.size(); i++) {



        }


        View view = getLayoutInflater().inflate(R.layout.drink_icon, null);



    }

    class getDrinks extends Thread {

        @Override
        public void run() {

            char startingLetter = 'a';

            while(startingLetter <= 'z') {

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
                    jsonObjects.add(jsonObject);

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

            for(int i = 0; i < jsonObjects.size(); i++) {
                try {
                    JSONObject jsonObject = jsonObjects.get(i);

                    if(jsonObject.getString("drinks") != "null") {
                        JSONArray jsonArray = jsonObject.getJSONArray("drinks");

                        for (int j = 0; j < jsonArray.length(); j++) {

                            jsonObject = jsonArray.getJSONObject(j);
                            System.out.println(jsonObject.getString("idDrink"));

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}