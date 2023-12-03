package com.example.walkwithme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WalkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_selector);

        FrameLayout frame1 = (FrameLayout) findViewById(R.id.btn_rcnd);
        FrameLayout frame2 = (FrameLayout) findViewById(R.id.btn_rdnd2);
        frame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "산책 코스 추천 받기!", Toast.LENGTH_SHORT).show();
            }
        });
        frame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "산책 바로 시작하기!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
