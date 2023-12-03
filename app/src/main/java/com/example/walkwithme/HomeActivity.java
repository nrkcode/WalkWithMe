package com.example.walkwithme;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


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

        //메뉴선택시 화면 변경 인텐트
        LinearLayout linear1 = (LinearLayout) findViewById(R.id.menu_layout1);
        LinearLayout linear2 = (LinearLayout) findViewById(R.id.menu_layout2);
        LinearLayout linear3 = (LinearLayout) findViewById(R.id.menu_layout3);

        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WalkSelectorActivity.class);
                startActivity(intent);
            }
        });

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


