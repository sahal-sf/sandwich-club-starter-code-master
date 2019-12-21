package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.*;
import java.util.*;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject SandwichDetail = new JSONObject(json);
            Sandwich SandwichData = new Sandwich();
            JSONObject Name = SandwichDetail.getJSONObject("name");

            String Data = Name.getString("mainName");
            if(Data.isEmpty()){
                SandwichData.setMainName("Sorry There is no Information");
            }else{
                SandwichData.setMainName(Data);
            }

            JSONArray JSONArrayAlsoKnownAs = Name.getJSONArray("alsoKnownAs");
            List<String> List = ConvertJsonArray(JSONArrayAlsoKnownAs);
            if(List.isEmpty()){
                List<String> Empty = new ArrayList<>(1);
                Empty.add("Sorry There is no Information");
                SandwichData.setAlsoKnownAs(Empty);
            }else{
                SandwichData.setAlsoKnownAs(List);
            }

            Data = SandwichDetail.optString("placeOfOrigin");
            if(Data.isEmpty()){
                SandwichData.setPlaceOfOrigin("Sorry There is no Information");
            }else{
                SandwichData.setPlaceOfOrigin(Data);
            }

            Data = SandwichDetail.optString("description");
            if(Data.isEmpty()){
                SandwichData.setDescription("Sorry There is no Information");
            }else{
                SandwichData.setDescription(Data);
            }

            Data = SandwichDetail.optString("image");
            if(Data.isEmpty()){
                SandwichData.setImage("Sorry There is no Image");
            }else{
                SandwichData.setImage(Data);
            }

            JSONArray JSONArrayIngredients = SandwichDetail.getJSONArray("ingredients");
            List = ConvertJsonArray(JSONArrayIngredients);
            if(List.isEmpty()){
                List<String> Empty = new ArrayList<>(1);
                Empty.add("Sorry There is no Information");
                SandwichData.setIngredients(Empty);
            }else{
                SandwichData.setIngredients(List);
            }

            return SandwichData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> ConvertJsonArray(JSONArray JsonArray){
        List<String> List = new ArrayList<>(JsonArray.length());

        for (int i = 0; i < JsonArray.length(); i++) {
            try {
                List.add(JsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return List;
    }
}
