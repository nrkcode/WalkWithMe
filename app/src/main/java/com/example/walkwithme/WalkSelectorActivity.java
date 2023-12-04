package com.example.walkwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class WalkSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_selector);

        FrameLayout frame1 = (FrameLayout) findViewById(R.id.btn_rcnd);
        FrameLayout frame2 = (FrameLayout) findViewById(R.id.btn_rdnd2);
        frame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 프레임 레이아웃 클릭 시 산책 리스트 화면인 activity_recommendation.xml로 인텐트
                Intent intent = new Intent(getApplicationContext(), RecommendationActivity.class);
                startActivity(intent);
            }
        });
        frame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 프레임 레이아웃 클릭 시 산책중 화면인 activity_walk.xml로 인텐트
                Intent intent = new Intent(getApplicationContext(), InWalk.class);
                startActivity(intent);
            }
        });

    }

}
