package com.example.walkwithme;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Register3Activity extends AppCompatActivity {
    //이전 인텐트에서 받아온 데이터를 저장하는 변수
    private String user_gender,user_age,user_name,user_bindo,user_duration,user_with;
    //현재 인텐트에서 받아온 데이터를 저장할 변수
    private String user_pref_place = "";
    private String user_pref_facility = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        //선호 장소 토글버튼 온오프 구현
        ToggleButton user_pref_place1 = findViewById(R.id.user_pref_place1);
        ToggleButton user_pref_place2 = findViewById(R.id.user_pref_place2);
        ToggleButton user_pref_place3 = findViewById(R.id.user_pref_place3);
        ToggleButton user_pref_place4 = findViewById(R.id.user_pref_place4);
        ToggleButton user_pref_place5 = findViewById(R.id.user_pref_place5);
        ToggleButton user_pref_place6 = findViewById(R.id.user_pref_place6);
        ToggleButton user_pref_place7 = findViewById(R.id.user_pref_place7);
        ToggleButton user_pref_place8 = findViewById(R.id.user_pref_place8);
        ToggleButton user_pref_place9 = findViewById(R.id.user_pref_place9);

        user_pref_place1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_place2.setChecked(false);
                    user_pref_place3.setChecked(false);
                    user_pref_place4.setChecked(false);
                    user_pref_place5.setChecked(false);
                    user_pref_place6.setChecked(false);
                    user_pref_place7.setChecked(false);
                    user_pref_place8.setChecked(false);
                    user_pref_place9.setChecked(false);
                    user_pref_place1.setBackgroundResource(R.drawable.select_box);
                    user_pref_place1.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_place = "공원";
                } else {
                    user_pref_place = "";
                    user_pref_place1.setBackgroundResource(R.drawable.select_box);
                    user_pref_place1.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        user_pref_place2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_place1.setChecked(false);
                    user_pref_place3.setChecked(false);
                    user_pref_place4.setChecked(false);
                    user_pref_place5.setChecked(false);
                    user_pref_place6.setChecked(false);
                    user_pref_place7.setChecked(false);
                    user_pref_place8.setChecked(false);
                    user_pref_place9.setChecked(false);
                    user_pref_place2.setBackgroundResource(R.drawable.select_box);
                    user_pref_place2.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_place = "운동장";
                } else {
                    user_pref_place = "";
                    user_pref_place2.setBackgroundResource(R.drawable.select_box);
                    user_pref_place2.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        user_pref_place3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_place1.setChecked(false);
                    user_pref_place2.setChecked(false);
                    user_pref_place4.setChecked(false);
                    user_pref_place5.setChecked(false);
                    user_pref_place6.setChecked(false);
                    user_pref_place7.setChecked(false);
                    user_pref_place8.setChecked(false);
                    user_pref_place9.setChecked(false);
                    user_pref_place3.setBackgroundResource(R.drawable.select_box);
                    user_pref_place3.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_place = "저수지";
                } else {
                    user_pref_place = "";
                    user_pref_place3.setBackgroundResource(R.drawable.select_box);
                    user_pref_place3.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        user_pref_place4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_place1.setChecked(false);
                    user_pref_place2.setChecked(false);
                    user_pref_place3.setChecked(false);
                    user_pref_place5.setChecked(false);
                    user_pref_place6.setChecked(false);
                    user_pref_place7.setChecked(false);
                    user_pref_place8.setChecked(false);
                    user_pref_place9.setChecked(false);
                    user_pref_place4.setBackgroundResource(R.drawable.select_box);
                    user_pref_place4.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_place = "테마공원";
                } else {
                    user_pref_place = "";
                    user_pref_place4.setBackgroundResource(R.drawable.select_box);
                    user_pref_place4.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        user_pref_place5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_place1.setChecked(false);
                    user_pref_place2.setChecked(false);
                    user_pref_place3.setChecked(false);
                    user_pref_place4.setChecked(false);
                    user_pref_place6.setChecked(false);
                    user_pref_place7.setChecked(false);
                    user_pref_place8.setChecked(false);
                    user_pref_place9.setChecked(false);
                    user_pref_place5.setBackgroundResource(R.drawable.select_box);
                    user_pref_place5.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_place = "자연공원";
                } else {
                    user_pref_place = "";
                    user_pref_place5.setBackgroundResource(R.drawable.select_box);
                    user_pref_place5.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        user_pref_place6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_place1.setChecked(false);
                    user_pref_place2.setChecked(false);
                    user_pref_place3.setChecked(false);
                    user_pref_place4.setChecked(false);
                    user_pref_place5.setChecked(false);
                    user_pref_place7.setChecked(false);
                    user_pref_place8.setChecked(false);
                    user_pref_place9.setChecked(false);
                    user_pref_place6.setBackgroundResource(R.drawable.select_box);
                    user_pref_place6.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_place = "식물원";
                } else {
                    user_pref_place = "";
                    user_pref_place6.setBackgroundResource(R.drawable.select_box);
                    user_pref_place6.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        user_pref_place7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_place1.setChecked(false);
                    user_pref_place2.setChecked(false);
                    user_pref_place3.setChecked(false);
                    user_pref_place4.setChecked(false);
                    user_pref_place5.setChecked(false);
                    user_pref_place6.setChecked(false);
                    user_pref_place8.setChecked(false);
                    user_pref_place9.setChecked(false);
                    user_pref_place7.setBackgroundResource(R.drawable.select_box);
                    user_pref_place7.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_place = "휴양림";
                } else {
                    user_pref_place = "";
                    user_pref_place7.setBackgroundResource(R.drawable.select_box);
                    user_pref_place7.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        user_pref_place8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_place1.setChecked(false);
                    user_pref_place2.setChecked(false);
                    user_pref_place3.setChecked(false);
                    user_pref_place4.setChecked(false);
                    user_pref_place5.setChecked(false);
                    user_pref_place6.setChecked(false);
                    user_pref_place7.setChecked(false);
                    user_pref_place9.setChecked(false);
                    user_pref_place8.setBackgroundResource(R.drawable.select_box);
                    user_pref_place8.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_place = "저수지";
                } else {
                    user_pref_place = "";
                    user_pref_place8.setBackgroundResource(R.drawable.select_box);
                    user_pref_place8.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        user_pref_place9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_place1.setChecked(false);
                    user_pref_place2.setChecked(false);
                    user_pref_place3.setChecked(false);
                    user_pref_place4.setChecked(false);
                    user_pref_place5.setChecked(false);
                    user_pref_place6.setChecked(false);
                    user_pref_place7.setChecked(false);
                    user_pref_place8.setChecked(false);
                    user_pref_place9.setBackgroundResource(R.drawable.select_box);
                    user_pref_place9.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_place = "유원지";
                } else {
                    user_pref_place = "";
                    user_pref_place9.setBackgroundResource(R.drawable.select_box);
                    user_pref_place9.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        // 시설 토글버튼 온오프 구현
        ToggleButton user_pref_facility1 = findViewById(R.id.user_pref_facility1);
        ToggleButton user_pref_facility2 = findViewById(R.id.user_pref_facility2);
        ToggleButton user_pref_facility3 = findViewById(R.id.user_pref_facility3);
        ToggleButton user_pref_facility4 = findViewById(R.id.user_pref_facility4);
        ToggleButton user_pref_facility5 = findViewById(R.id.user_pref_facility5);
        ToggleButton user_pref_facility6 = findViewById(R.id.user_pref_facility6);
        ToggleButton user_pref_facility7 = findViewById(R.id.user_pref_facility7);

        user_pref_facility1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_facility2.setChecked(false);
                    user_pref_facility3.setChecked(false);
                    user_pref_facility4.setChecked(false);
                    user_pref_facility5.setChecked(false);
                    user_pref_facility6.setChecked(false);
                    user_pref_facility7.setChecked(false);
                    user_pref_facility1.setBackgroundResource(R.drawable.select_box);
                    user_pref_facility1.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_facility = "놀이터";
                } else {
                    user_pref_facility = "";
                    user_pref_facility1.setBackgroundResource(R.drawable.select_box);
                    user_pref_facility1.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        user_pref_facility2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_facility1.setChecked(false);
                    user_pref_facility3.setChecked(false);
                    user_pref_facility4.setChecked(false);
                    user_pref_facility5.setChecked(false);
                    user_pref_facility6.setChecked(false);
                    user_pref_facility7.setChecked(false);
                    user_pref_facility2.setBackgroundResource(R.drawable.select_box);
                    user_pref_facility2.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_facility = "바닥분수";
                } else {
                    user_pref_facility = "";
                    user_pref_facility2.setBackgroundResource(R.drawable.select_box);
                    user_pref_facility2.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        user_pref_facility3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_facility1.setChecked(false);
                    user_pref_facility2.setChecked(false);
                    user_pref_facility4.setChecked(false);
                    user_pref_facility5.setChecked(false);
                    user_pref_facility6.setChecked(false);
                    user_pref_facility7.setChecked(false);
                    user_pref_facility3.setBackgroundResource(R.drawable.select_box);
                    user_pref_facility3.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_facility = "도서관";
                } else {
                    user_pref_facility = "";
                    user_pref_facility3.setBackgroundResource(R.drawable.select_box);
                    user_pref_facility3.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        user_pref_facility4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_facility1.setChecked(false);
                    user_pref_facility2.setChecked(false);
                    user_pref_facility3.setChecked(false);
                    user_pref_facility5.setChecked(false);
                    user_pref_facility6.setChecked(false);
                    user_pref_facility7.setChecked(false);
                    user_pref_facility4.setBackgroundResource(R.drawable.select_box);
                    user_pref_facility4.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_facility = "화장실";
                } else {
                    user_pref_facility = "";
                    user_pref_facility4.setBackgroundResource(R.drawable.select_box);
                    user_pref_facility4.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        user_pref_facility5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_facility1.setChecked(false);
                    user_pref_facility2.setChecked(false);
                    user_pref_facility3.setChecked(false);
                    user_pref_facility4.setChecked(false);
                    user_pref_facility6.setChecked(false);
                    user_pref_facility7.setChecked(false);
                    user_pref_facility5.setBackgroundResource(R.drawable.select_box);
                    user_pref_facility5.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_facility = "주차장";
                } else {
                    user_pref_facility = "";
                    user_pref_facility5.setBackgroundResource(R.drawable.select_box);
                    user_pref_facility5.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        user_pref_facility6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_facility1.setChecked(false);
                    user_pref_facility2.setChecked(false);
                    user_pref_facility3.setChecked(false);
                    user_pref_facility4.setChecked(false);
                    user_pref_facility5.setChecked(false);
                    user_pref_facility7.setChecked(false);
                    user_pref_facility6.setBackgroundResource(R.drawable.select_box);
                    user_pref_facility6.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_facility = "경로당";
                } else {
                    user_pref_facility = "";
                    user_pref_facility6.setBackgroundResource(R.drawable.select_box);
                    user_pref_facility6.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        user_pref_facility7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user_pref_facility1.setChecked(false);
                    user_pref_facility2.setChecked(false);
                    user_pref_facility3.setChecked(false);
                    user_pref_facility4.setChecked(false);
                    user_pref_facility5.setChecked(false);
                    user_pref_facility6.setChecked(false);
                    user_pref_facility7.setBackgroundResource(R.drawable.select_box);
                    user_pref_facility7.setTextColor(Color.parseColor("#ffffff"));
                    user_pref_facility = "마을회관";
                } else {
                    user_pref_facility = "";
                    user_pref_facility7.setBackgroundResource(R.drawable.select_box);
                    user_pref_facility7.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        //다음 이전 인텐트로의 이동
        Button register3buttonback = findViewById(R.id.register3buttonback);
        Button register3buttonfront = findViewById(R.id.register3buttonfront);

        Intent lastintent = getIntent();
        user_name = lastintent.getStringExtra("user_name");
        user_age = lastintent.getStringExtra("user_age");
        user_gender = lastintent.getStringExtra("user_gender");
        user_bindo = lastintent.getStringExtra("user_bindo");
        user_duration = lastintent.getStringExtra("user_duration");
        user_with = lastintent.getStringExtra("user_with");

        register3buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register2Activity.class);
                intent.putExtra("user_name", user_name);
                intent.putExtra("user_age", user_age);
                intent.putExtra("user_gender", user_gender);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit);
            }
        });

        register3buttonfront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register4Activity.class);
                new RegisterUserTask().execute(user_name,user_gender,user_age,user_bindo,user_duration,user_with,user_pref_place,user_pref_facility);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit);
            }
        });


        //액션바 없애기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private class RegisterUserTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                // 서버 URL
                URL url = new URL("http://kshind1.dothome.co.kr/register_user.php");

                // HTTP 연결 설정
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                Log.d("RegisterUserTask", "Params: " + Arrays.toString(params));
                // POST 데이터 작성
                // AsyncTask 클래스 내부의 doInBackground 메서드
                String postData = "user_name=" + URLEncoder.encode(params[0], String.valueOf(StandardCharsets.UTF_8))
                        + "&user_age=" + URLEncoder.encode(params[1], String.valueOf(StandardCharsets.UTF_8))
                        + "&user_gender=" + URLEncoder.encode(params[2], String.valueOf(StandardCharsets.UTF_8))
                        + "&user_bindo=" + URLEncoder.encode(params[3], String.valueOf(StandardCharsets.UTF_8))
                        + "&user_duration=" + URLEncoder.encode(params[4], String.valueOf(StandardCharsets.UTF_8))
                        + "&user_with=" + URLEncoder.encode(params[5], String.valueOf(StandardCharsets.UTF_8))
                        + "&user_pref_place=" + URLEncoder.encode(params[6], String.valueOf(StandardCharsets.UTF_8))
                        + "&user_pref_facility=" + URLEncoder.encode(params[7], String.valueOf(StandardCharsets.UTF_8));


                // 데이터 전송
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = postData.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);

                }

                // HTTP 응답 확인
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 성공적으로 데이터를 전송한 경우
                    Log.d("RegisterUserTask", "User registered successfully");
                    Log.d("RegisterUserTask", "Server response: " + responseCode);
                } else {
                    // 데이터 전송 실패
                    Log.e("RegisterUserTask", "Error: " + responseCode);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}