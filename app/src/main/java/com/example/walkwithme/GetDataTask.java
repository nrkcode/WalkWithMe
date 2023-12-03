package com.example.walkwithme;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDataTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... voids) {
        String result = "";

        try {
            URL url = new URL("http://kshind1.dothome.co.kr/walk.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            bufferedReader.close();
            inputStreamReader.close();
            httpURLConnection.disconnect();

            result = stringBuilder.toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    private void showData(String jsonData, TextView textView) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String name = item.getString("cource_name");
                String len = item.getString("walk_len");
                String hour = item.getString("walk_hour");
                String calory = item.getString("waste_calory");

                builder.append("Name: ").append(name).append("\n");
                builder.append("len: ").append(len).append("\n");
                builder.append("hour: ").append(hour).append("\n");
                builder.append("calory: ").append(calory).append("\n\n");
            }
            textView.setText(builder.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
