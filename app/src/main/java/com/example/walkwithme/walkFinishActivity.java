package com.example.walkwithme;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class walkFinishActivity extends AppCompatActivity {
    TextView finishTime;
    TextView finish_currentSteps;
    TextView finish_current_distance;
    TextView finish_currentKcal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_finish);

        //이전 인텐트에서 변수 받아오기
        Intent intent = getIntent();
        int currentSteps = intent.getIntExtra("currentSteps", 123);
        double current_distance = intent.getDoubleExtra("current_distance", 123);
        double currentKcal = intent.getDoubleExtra("currentKcal", 123);
        String ss = intent.getStringExtra("hour");
        String hh = intent.getStringExtra("mm");
        String mm = intent.getStringExtra("ss");

        String timeee = intent.getStringExtra("finishTime");

        //가져온 변수값 입력하기
        finishTime = findViewById(R.id.finishTime);
        finish_currentSteps = findViewById(R.id.finish_currentSteps);
        finish_current_distance = findViewById(R.id.finish_current_distance);
        finish_currentKcal = findViewById(R.id.finish_currentKcal);

        finishTime.setText(timeee);
        finish_currentSteps.setText(String.valueOf(currentSteps));
        finish_current_distance.setText(String.valueOf(current_distance));
        finish_currentKcal.setText(String.valueOf(currentKcal));

        //인텐트로 홈화면 이동
        Button button = (Button) findViewById(R.id.register3buttonfront);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        //액션바 없애기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

}