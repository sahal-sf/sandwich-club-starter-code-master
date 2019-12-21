package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.provider.FontRequest;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import android.widget.TextView;
import java.util.*;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this).load(sandwich.getImage()).into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich SandwichDetail) {
        List<String> AlsoKnownList = SandwichDetail.getAlsoKnownAs();
        String AlsoKnown = "";
        if(AlsoKnownList.size() > 0) {
            for (int i = 0; i < AlsoKnownList.size() - 1; i++) {
                AlsoKnown += AlsoKnownList.get(i) + ", ";
            }
            AlsoKnown += AlsoKnownList.get(AlsoKnownList.size() - 1);
        }
        ((TextView)findViewById(R.id.also_known_tv)).setText(AlsoKnown);

        List<String> IngredientsList = SandwichDetail.getIngredients();
        String Ingredients = "";
        if(IngredientsList.size() > 0) {
            for (int i = 0; i < IngredientsList.size() - 1; i++) {
                Ingredients += IngredientsList.get(i) + ", ";
            }
            Ingredients += IngredientsList.get(IngredientsList.size() - 1);
        }
        ((TextView)findViewById(R.id.ingredients_tv)).setText(Ingredients);

        ((TextView)findViewById(R.id.origin_tv)).setText(SandwichDetail.getPlaceOfOrigin());
        ((TextView)findViewById(R.id.description_tv)).setText(SandwichDetail.getDescription());
    }
}
