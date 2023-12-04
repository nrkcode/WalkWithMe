package com.example.walkwithme;// DataProcessor.java
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DataProcessor {

    public static void processJsonData(String jsonData, TextView nameTextView, TextView lenTextView, TextView hourTextView, TextView caloryTextView , TextView usernameTextView, TextView stepTextView) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            StringBuilder builder1 = new StringBuilder();
            StringBuilder builder2 = new StringBuilder();
            StringBuilder builder3 = new StringBuilder();
            StringBuilder builder4 = new StringBuilder();
            StringBuilder builder5 = new StringBuilder();
            StringBuilder builder6 = new StringBuilder();

            JSONObject item = jsonArray.getJSONObject(jsonArray.length()-1);

            String name = item.getString("cource_name");
            String len = item.getString("walk_len");
            String hour = item.getString("walk_hour");
            String calory = item.getString("waste_calory");
            String username = item.getString("user_name");
            String currentstep = item.getString("user_walked");

            builder1.append(name);
            builder2.append(len).append("km");
            builder3.append(hour).append("분");
            builder4.append(calory).append("Kcal");
            builder5.append("안녕하세요! ").append(username).append("님");
            builder6.append(currentstep);

            nameTextView.setText(builder1.toString());
            lenTextView.setText(builder2.toString());
            hourTextView.setText(builder3.toString());
            caloryTextView.setText(builder4.toString());
            usernameTextView.setText(builder5.toString());
            stepTextView.setText(builder6.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
