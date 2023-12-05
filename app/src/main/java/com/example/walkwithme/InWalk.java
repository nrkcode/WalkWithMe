package com.example.walkwithme;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.view.ViewGroup;
import android.widget.TextView;

import android.os.Build.VERSION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;
import androidx.annotation.RequiresApi;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class InWalk extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener, SensorEventListener {
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private Chronometer chronometer; //타이머 view
    private boolean running; // 진행 중인지 확인하는 변수
    private long pauseOffset; // 정지 오프셋
    private String ss,hh,mm;
    SensorManager sensorManager;
    Sensor stepCountSensor;
    TextView stepCountView;
    TextView walkDistance;
    TextView kcalCountView;
    private List<MapPoint> pathPoints;

    Dialog dilaog01; // 커스텀 다이얼로그

    private int currentSteps = 0;//현재 걸음수
    private double current_distance = 0;//현재 걸은거리
    private double currentKcal = 0;//현재 소모 칼로리

    private String timeee = "";

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

        dilaog01 = new Dialog(InWalk.this);       // Dialog 초기화
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog01.setContentView(R.layout.activity_finish_window);             // xml 레이아웃 파일과 연결


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
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h = (int)(time / 3600000);
                int m = (int)(time - h * 3600000) / 60000;
                int s = (int)(time - h * 3600000 - m * 60000) / 1000;
                hh = h < 10 ? "0" + h : String.valueOf(h);
                mm = m < 10 ? "0" + m : String.valueOf(m);
                ss = s < 10 ? "0" + s : String.valueOf(s);
                timeee = hh + ":" + mm + ":" + ss;


                cArg.setText(timeee);
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
                    //chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
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
                //chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                chronometer.stop();
                running = false;

                showDialog01(); // 아래 showDialog01() 함수 호출
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

        int internetPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int fineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (internetPermission == PackageManager.PERMISSION_DENIED || fineLocationPermission == PackageManager.PERMISSION_DENIED || coarseLocationPermission == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        mapView.setCurrentLocationEventListener(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        pathPoints = new ArrayList<>();

        // Custom Marker
        MapPOIItem customMarker = new MapPOIItem();
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.480426, 126.900177);
        customMarker.setItemName("우리집 근처");
        customMarker.setTag(1);
        customMarker.setMapPoint(mapPoint);
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        customMarker.setCustomImageResourceId(R.drawable.custom_marker_red);
        customMarker.setCustomImageAutoscale(false);
        customMarker.setCustomImageAnchor(0.5f, 1.0f);
        mapView.addPOIItem(customMarker);

        // 폴리라인 그리기
        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(0xFFFF3300); // Color.argb(128, 255, 51, 0);
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.479928, 126.900169));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.480624, 126.900735));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.481667, 126.900713));
        mapView.addPolyline(polyline);

        // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
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
        pathPoints.add(mapPoint);
        drawPath();
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


    // dialog01을 디자인하는 함수
    public void showDialog01(){
        dilaog01.show(); // 다이얼로그 띄우기
        dilaog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경
        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.

        // 아니오 버튼
        Button noBtn = dilaog01.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dilaog01.dismiss(); // 다이얼로그 닫기
            }
        });
        // 네 버튼
        dilaog01.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                // 원하는 기능 구현
                Intent intent = new Intent(getApplicationContext(), walkFinishActivity.class);
                intent.putExtra("currentSteps", currentSteps);
                intent.putExtra("current_distance", current_distance);
                intent.putExtra("currentKcal", currentKcal);
                intent.putExtra("hour", hh);
                intent.putExtra("minute",mm);
                intent.putExtra("second",ss);
                intent.putExtra("finishTime",timeee);
                startActivity(intent);
                chronometer.setBase(SystemClock.elapsedRealtime());
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void drawPath() {
        if (pathPoints.size() < 2) {
            return;
        }

        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(0xFFFF3300); // Color.argb(128, 255, 51, 0);

        for (MapPoint point : pathPoints) {
            polyline.addPoint(point);
        }

        mapView.addPolyline(polyline);

        // 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
    }

}