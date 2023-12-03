package com.example.walkwithme;// DataProcessor.java
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataProcessor {

    public static void processJsonData(String jsonData, TextView nameTextView, TextView lenTextView, TextView hourTextView, TextView caloryTextView) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            StringBuilder builder1 = new StringBuilder();
            StringBuilder builder2= new StringBuilder();
            StringBuilder builder3 = new StringBuilder();
            StringBuilder builder4 = new StringBuilder();

            JSONObject item = jsonArray.getJSONObject(jsonArray.length()-1);

            String name = item.getString("cource_name");
            String len = item.getString("walk_len");
            String hour = item.getString("walk_hour");
            String calory = item.getString("waste_calory");

            builder1.append(name);
            builder2.append(len).append("km");
            builder3.append(hour).append("분");
            builder4.append(calory).append("Kcal");

            nameTextView.setText(builder1.toString());
            lenTextView.setText(builder2.toString());
            hourTextView.setText(builder3.toString());
            caloryTextView.setText(builder4.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
