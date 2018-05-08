package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    Sandwich mSandwich;

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
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView altNamesTv = findViewById(R.id.also_known_tv);
        TextView placeOfOriginTv = findViewById(R.id.origin_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);

        String placeOfOriginString = mSandwich.getPlaceOfOrigin();
        if (placeOfOriginString == null || placeOfOriginString.equals("")) {
            placeOfOriginTv.setText("Unknown");
        }else {
            placeOfOriginTv.setText(placeOfOriginString);
        }

        String ingredientsString = mSandwich.getIngredients().toString();
        if (ingredientsString == null || ingredientsString.equals("[]")) {
            ingredientsTv.setText("Unknown");
        } else {
            ingredientsString = removeBrackets(ingredientsString);
            ingredientsTv.setText(ingredientsString);
        }

        String altNamesString = mSandwich.getAlsoKnownAs().toString();
        if (altNamesString == null || altNamesString.equals("[]")) {
            altNamesTv.setText("Unknown");
        } else {
            altNamesString = removeBrackets(altNamesString);
            altNamesTv.setText(altNamesString);
        }

        descriptionTv.setText(mSandwich.getDescription());
    }

    public String removeBrackets(String str) {
        if (str != null && str.length() > 0 && str.charAt(0) == '[' && str.charAt(str.length() - 1) == ']') {
            str = str.substring(1, str.length() - 1);
        }
        return str;
    }
}
