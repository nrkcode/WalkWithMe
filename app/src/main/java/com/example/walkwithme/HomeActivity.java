package com.example.walkwithme;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // AsyncTask 실행
        new GetDataTask().execute();
        //액션바 없애기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    private class GetDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return NetworkUtils.fetchData("http://kshind1.dothome.co.kr/walk.php");
        }

        @Override
        protected void onPostExecute(String result) {
            TextView nameTextView = (TextView) findViewById(R.id.cource_name);
            TextView lenTextView = (TextView) findViewById(R.id.walk_len);
            TextView hourTextView = (TextView) findViewById(R.id.walk_hour);
            TextView caloryTextView = (TextView) findViewById(R.id.waste_calory);
            DataProcessor.processJsonData(result, nameTextView, lenTextView, hourTextView, caloryTextView);
        }
    }



}


