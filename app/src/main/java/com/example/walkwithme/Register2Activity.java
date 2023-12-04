package com.example.walkwithme;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Register2Activity extends AppCompatActivity {
    //이전 인텐트에서 받아온 데이터를 저장하는 변수
    private String user_gender,user_age,user_name;
    //현재 인텐트에서 받아온 데이터를 저장할 변수
    private String user_bindo = "";
    private String user_duration = "";
    private String user_with = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        //빈도수 토글버튼 on/off기능 구현
        ToggleButton user_bindo1 = findViewById(R.id.user_bindo1);
        ToggleButton user_bindo2 = findViewById(R.id.user_bindo2);
        ToggleButton user_bindo3 = findViewById(R.id.user_bindo3);
        ToggleButton user_bindo4 = findViewById(R.id.user_bindo4);
        ToggleButton user_bindo5 = findViewById(R.id.user_bindo5);

        user_bindo1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_bindo2.setChecked(false);
                    user_bindo3.setChecked(false);
                    user_bindo4.setChecked(false);
                    user_bindo5.setChecked(false);
                    user_bindo1.setBackgroundResource(R.drawable.select_box);
                    user_bindo = "거의 안함";
                } else {
                    user_bindo = "";
                    user_bindo1.setBackgroundResource(R.drawable.select_box);
                }
            }
        });
        user_bindo2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_bindo1.setChecked(false);
                    user_bindo3.setChecked(false);
                    user_bindo4.setChecked(false);
                    user_bindo5.setChecked(false);
                    user_bindo2.setBackgroundResource(R.drawable.select_box);
                    user_bindo = "주1~2회";
                } else {
                    user_bindo = "";
                    user_bindo2.setBackgroundResource(R.drawable.select_box);
                }
            }
        });
        user_bindo3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_bindo1.setChecked(false);
                    user_bindo2.setChecked(false);
                    user_bindo4.setChecked(false);
                    user_bindo5.setChecked(false);
                    user_bindo3.setBackgroundResource(R.drawable.select_box);
                    user_bindo = "주3~4회";
                } else {
                    user_bindo = "";
                    user_bindo3.setBackgroundResource(R.drawable.select_box);
                }
            }
        });
        user_bindo4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_bindo1.setChecked(false);
                    user_bindo2.setChecked(false);
                    user_bindo3.setChecked(false);
                    user_bindo5.setChecked(false);
                    user_bindo4.setBackgroundResource(R.drawable.select_box);
                    user_bindo = "주4~5회";
                } else {
                    user_bindo = "";
                    user_bindo4.setBackgroundResource(R.drawable.select_box);
                }
            }
        });
        user_bindo5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_bindo1.setChecked(false);
                    user_bindo2.setChecked(false);
                    user_bindo3.setChecked(false);
                    user_bindo4.setChecked(false);
                    user_bindo5.setBackgroundResource(R.drawable.select_box);
                    user_bindo = "매일";
                } else {
                    user_bindo = "";
                    user_bindo5.setBackgroundResource(R.drawable.select_box);
                }
            }
        });
        //시간 토글버튼 on/off기능 구현
        ToggleButton user_duration1 = findViewById(R.id.user_duration1);
        ToggleButton user_duration2 = findViewById(R.id.user_duration2);
        ToggleButton user_duration3 = findViewById(R.id.user_duration3);
        ToggleButton user_duration4 = findViewById(R.id.user_duration4);

        user_duration1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_duration2.setChecked(false);
                    user_duration3.setChecked(false);
                    user_duration4.setChecked(false);
                    user_duration1.setBackgroundResource(R.drawable.select_box);
                    user_duration = "30분 이하";
                } else {
                    user_duration = "";
                    user_duration1.setBackgroundResource(R.drawable.select_box);
                }
            }
        });
        user_duration2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_duration1.setChecked(false);
                    user_duration3.setChecked(false);
                    user_duration4.setChecked(false);
                    user_duration2.setBackgroundResource(R.drawable.select_box);
                    user_duration = "30~60분";
                } else {
                    user_duration = "";
                    user_duration2.setBackgroundResource(R.drawable.select_box);
                }
            }
        });
        user_duration3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_duration1.setChecked(false);
                    user_duration2.setChecked(false);
                    user_duration4.setChecked(false);
                    user_duration3.setBackgroundResource(R.drawable.select_box);
                    user_duration = "1~2시간";
                } else {
                    user_duration = "";
                    user_duration3.setBackgroundResource(R.drawable.select_box);
                }
            }
        });
        user_duration4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_duration2.setChecked(false);
                    user_duration3.setChecked(false);
                    user_duration1.setChecked(false);
                    user_duration4.setBackgroundResource(R.drawable.select_box);
                    user_duration = "2시간 이상";
                } else {
                    user_duration = "";
                    user_duration4.setBackgroundResource(R.drawable.select_box);
                }
            }
        });

        //누구와 함께 on/off기능 구현
        ToggleButton user_with1 = findViewById(R.id.user_with1);
        ToggleButton user_with2 = findViewById(R.id.user_with2);
        ToggleButton user_with3 = findViewById(R.id.user_with3);

        user_with1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_with2.setChecked(false);
                    user_with3.setChecked(false);
                    user_with1.setBackgroundResource(R.drawable.select_box);
                    user_with = "혼자";
                } else {
                    user_with = "";
                    user_with1.setBackgroundResource(R.drawable.select_box);
                }
            }
        });
        user_with2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_with1.setChecked(false);
                    user_with3.setChecked(false);
                    user_with2.setBackgroundResource(R.drawable.select_box);
                    user_with = "배우자(연인)과";
                } else {
                    user_with = "";
                    user_with2.setBackgroundResource(R.drawable.select_box);
                }
            }
        });
        user_with3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_with1.setChecked(false);
                    user_with2.setChecked(false);
                    user_with3.setBackgroundResource(R.drawable.select_box);
                    user_with = "친구와";
                } else {
                    user_with = "";
                    user_with3.setBackgroundResource(R.drawable.select_box);
                }
            }
        });



        //다음, 이전 인텐트로의 이동
        Button register2buttonback = findViewById(R.id.register2buttonback);
        Button register2buttonfront = findViewById(R.id.register2buttonfront);

        //이전 인텐트에서 데이터 받아오기기
        Intent lastintent = getIntent();
        user_name = lastintent.getStringExtra("user_name");
        user_age = lastintent.getStringExtra("user_age");
        user_gender = lastintent.getStringExtra("user_gender");

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

                //다음 인텐트로 데이터 전달
                intent.putExtra("user_name", user_name);
                intent.putExtra("user_age", user_age);
                intent.putExtra("user_gender", user_gender);
                intent.putExtra("user_bindo", user_bindo);
                intent.putExtra("user_duration", user_duration);
                intent.putExtra("user_with", user_with);

                startActivity(intent);
            }
        });


        //액션바 없애기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
