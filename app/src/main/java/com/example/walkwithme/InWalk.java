package com.example.walkwithme;

import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.os.Build.VERSION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import androidx.appcompat.app.AppCompatActivity;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InWalk extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener, SensorEventListener {
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private Chronometer chronometer; //타이머 view
    private boolean running; // 진행 중인지 확인하는 변수
    private long pauseOffset; // 정지 오프셋
    SensorManager sensorManager;
    Sensor stepCountSensor;
    TextView stepCountView;
    TextView walkDistance;
    TextView kcalCountView;

    int currentSteps = 0;//현재 걸음수
    double current_distance = 0;//현재 걸은거리
    double currentKcal = 0;//현재 소모 칼로리

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        stepCountView = findViewById(R.id.stepCountView);
        walkDistance = findViewById(R.id.walkDistance);
        kcalCountView = findViewById(R.id.kcalCountView);

        // 활동 퍼미션 체크 (만보기쪽)
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

    // 걸음 센서 연결
        // * 옵션
        // - TYPE_STEP_DETECTOR:  리턴 값이 무조건 1, 앱이 종료되면 다시 0부터 시작
        // - TYPE_STEP_COUNTER : 앱 종료와 관계없이 계속 기존의 값을 가지고 있다가 1씩 증가한 값을 리턴
        //

        // 디바이스에 걸음 센서의 존재 여부 체크
        if (stepCountSensor == null) {
            Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT).show();
        }

        chronometer = findViewById(R.id.chronometer);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){ // 시분초 표현
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h   = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
                String hh = h < 10 ? "0"+h: h+"";
                String mm = m < 10 ? "0"+m: m+"";
                String ss = s < 10 ? "0"+s: s+"";
                cArg.setText(hh+":"+mm+":"+ss);
            }
        });
        chronometer.setFormat("%s"); // 타이머 포멧 등록

        Button startBtnf = findViewById(R.id.start_btn_f);
        ImageButton startBtn = findViewById(R.id.start_btn);
        ImageButton resetBtn = findViewById(R.id.reset_btn);
        ImageView startImg = findViewById(R.id.start_img);
        ImageButton stopBtn = findViewById(R.id.stop_btn);
        ImageView walkImg = findViewById(R.id.walk_img);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopBtn.setVisibility(stopBtn.VISIBLE);
                walkImg.setVisibility(walkImg.VISIBLE);
                startBtn.setVisibility(startBtn.INVISIBLE);

                if(!running){
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    running = true;
                }
            }
        });
        startBtnf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBtnf.setVisibility(startBtnf.GONE);
                startBtn.setVisibility(startBtn.GONE);
                startImg.setVisibility(startImg.GONE);
                chronometer.setVisibility(chronometer.VISIBLE);
                stopBtn.setVisibility(stopBtn.VISIBLE);
                walkImg.setVisibility(walkImg.VISIBLE);
                resetBtn.setVisibility(resetBtn.VISIBLE);



                if(!running){
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    running = true;
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBtn.setVisibility(startBtn.VISIBLE);
                stopBtn.setVisibility(stopBtn.GONE);
                if(running){
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                chronometer.stop();
                running = false;
            }
        });


        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("키해시는 :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 권한ID를 가져옵니다
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);

        int permission2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        int permission3 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        // 권한이 열려있는지 확인
        if (permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED || permission3 == PackageManager.PERMISSION_DENIED) {
            // 마쉬멜로우 이상버전부터 권한을 물어본다
            if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 권한 체크(READ_PHONE_STATE의 requestCode를 1000으로 세팅
                requestPermissions(
                        new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        1000);
            }
            return;
        }

        //지도를 띄우자
        // java code
        mapView = new MapView(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

    }

    // 권한 체크 이후로직
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        // READ_PHONE_STATE의 권한 체크 결과를 불러온다
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
        if (requestCode == 1000) {
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            // 권한 체크에 동의를 하지 않으면 안드로이드 종료
            if (check_result == false) {
                finish();
            }
        }
    }
    // CurrentLocationEventListener 메소드 구현
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        // 현재 위치 업데이트 시 호출되는 메소드
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
        // 현재 위치 디바이스 헤딩 업데이트 시 호출되는 메소드
    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {
        // 현재 위치 업데이트 실패 시 호출되는 메소드
    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {
        // 현재 위치 업데이트 취소 시 호출되는 메소드
    }

    // MapViewEventListener 메소드 구현
    @Override
    public void onMapViewInitialized(MapView mapView) {
        // 지도 초기화 시 호출되는 메소드
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
        // 지도 중심 좌표 이동 시 호출되는 메소드
    }
    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
        // 지도 줌 레벨 변경 시 호출되는 메소드
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        // 지도 싱글 탭 시 호출되는 메소드
    }
    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
        // 지도 싱글 탭 시 호출되는 메소드
    }
    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
        // 지도 싱글 탭 시 호출되는 메소드
    }
    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        // 지도 싱글 탭 시 호출되는 메소드
    }
    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        // 지도 싱글 탭 시 호출되는 메소드
    }
    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        // 지도 싱글 탭 시 호출되는 메소드
    }

    public void onStart() {
        super.onStart();
        if(stepCountSensor !=null) {
            // 센서 속도 설정
            // * 옵션
            // - SENSOR_DELAY_NORMAL: 20,000 초 딜레이
            // - SENSOR_DELAY_UI: 6,000 초 딜레이
            // - SENSOR_DELAY_GAME: 20,000 초 딜레이
            // - SENSOR_DELAY_FASTEST: 딜레이 없음
            //
            sensorManager.registerListener(this,stepCountSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 걸음 센서 이벤트 발생시
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){

            if(event.values[0]==1.0f){
                // 센서 이벤트가 발생할때 마다 걸음수 증가
                currentSteps = currentSteps + 1;
                stepCountView.setText(String.valueOf(currentSteps));

                current_distance=currentSteps*0.29;
                walkDistance.setText(String.valueOf(current_distance));

                currentKcal = currentSteps * 0.025;
                kcalCountView.setText(String.valueOf(currentKcal));
            }

        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}