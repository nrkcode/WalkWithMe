package com.example.walkwithme;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WalkInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_info);

        //액션바 없애기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //뒤로가기 버튼
        Button button = (Button) findViewById(R.id.back2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecommendationActivity.class);
                startActivity(intent);
            }
        });
    }

}