package com.example.walkwithme;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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



}