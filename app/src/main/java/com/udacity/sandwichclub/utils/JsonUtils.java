package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich currentSandwich = null;

        try {
            JSONObject baseObject = new JSONObject(json);

            JSONObject nameObject = baseObject.getJSONObject("name");
            String mainNameString = nameObject.getString("mainName");

            JSONArray altNamesArray = nameObject.getJSONArray("alsoKnownAs");
            List<String> altNamesList = new ArrayList<>();
            for (int i = 0; i < altNamesArray.length(); i++) {
                String altName = altNamesArray.getString(i);
                altNamesList.add(altName);
            }

            String placeOfOriginString =  baseObject.getString("placeOfOrigin");
            String descriptionString = baseObject.getString("description");
            String imageString = baseObject.getString("image");

            JSONArray ingredientsArray = baseObject.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                String ingredientString = ingredientsArray.getString(i);
                ingredientsList.add(ingredientString);
            }

            currentSandwich = new Sandwich(mainNameString, altNamesList, placeOfOriginString, descriptionString, imageString, ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currentSandwich;
    }


}
