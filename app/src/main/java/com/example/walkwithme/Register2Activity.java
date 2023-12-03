package com.example.walkwithme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Register2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        Button register2buttonback = findViewById(R.id.register2buttonback);
        Button register2buttonfront = findViewById(R.id.register2buttonfront);

        register2buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register1Activity.class);
                startActivity(intent);
            }
        });

        register2buttonfront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register3Activity.class);
                startActivity(intent);
            }
        });


        //액션바 없애기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
