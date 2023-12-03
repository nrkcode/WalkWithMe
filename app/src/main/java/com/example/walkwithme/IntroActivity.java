package com.example.walkwithme;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //인트로 2초
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Register1Activity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

        //액션바 없애기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}