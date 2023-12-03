package com.example.walkwithme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Register3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        Button register3buttonback = findViewById(R.id.register3buttonback);
        Button register3buttonfront = findViewById(R.id.register3buttonfront);

        register3buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register2Activity.class);
                startActivity(intent);
            }
        });

        register3buttonfront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register4Activity.class);
                startActivity(intent);
            }
        });


        //액션바 없애기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}