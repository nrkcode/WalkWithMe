package com.example.walkwithme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Register1Activity extends AppCompatActivity {
    //전달할 데이터의 변수 정의
    private String user_gender = "";
    private String user_age = "";
    private String user_name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        //토글버튼 on/off 기능 구현
        ToggleButton maleButton = findViewById(R.id.maleButton);
        ToggleButton femaleButton = findViewById(R.id.femaleButton);

        maleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    femaleButton.setChecked(false);
                    maleButton.setBackgroundResource(R.drawable.text_box);
                    user_gender = "남";
                } else {
                    user_gender = "";
                    maleButton.setBackgroundResource(R.drawable.text_box);
                }
            }
        });

        femaleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    maleButton.setChecked(false);
                    femaleButton.setBackgroundResource(R.drawable.text_box);
                    user_gender = "여";
                } else {
                    user_gender = "";
                    femaleButton.setBackgroundResource(R.drawable.text_box);
                }
            }
        });
        EditText user_name = findViewById(R.id.user_name);
        EditText user_age = findViewById(R.id.user_age);
        Button register1button = findViewById(R.id.register1button);
        register1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //다음 intent로 데이터 전달
                Intent intent = new Intent(getApplicationContext(), Register2Activity.class);
                intent.putExtra("user_name", user_name.getText().toString());
                intent.putExtra("user_age", user_age.getText().toString());
                intent.putExtra("user_gender", user_gender);
                startActivity(intent);
            }
        });

        Button btn = (Button) findViewById(R.id.back1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(intent);
            }
        });

        //액션바 없애기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}