package com.example.walkwithme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WalkInfoActivity extends AppCompatActivity {

    //아래부터recycler
    private FusedLocationProviderClient fusedLocationClient;
    private String kakaoApiKey = "bd2e505109d0952fa9d393afd54028aa";
    private RecyclerView recyclerView3;
    private PlaceAdapter placeAdapter;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    //위부터recycler

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

        //산책 시작 버튼 클릭시 타이머화면으로 인텐트
        Button button2 = (Button) findViewById(R.id.start_walk_btn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InWalk.class);
                startActivity(intent);
            }
        });

        //아래부터recycler
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // 위치 권한 확인 및 요청
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        } else {
            requestLocation();
        }

        // RecyclerView 초기화
        recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        placeAdapter = new PlaceAdapter();
        recyclerView3.setAdapter(placeAdapter);
        //위부터recycler

        //텍스트 데이터 가져오기
        TextView textName = findViewById(R.id.nameTextView);
        TextView textAdr = findViewById(R.id.addressTextView);
        TextView textCate = findViewById(R.id.categoryTextView);
        TextView textDis = findViewById(R.id.distanceTextView);

        String Name = textName.getText().toString();
        String Adr = textAdr.getText().toString();
        String Cate = textCate.getText().toString();
        String Dis = textDis.getText().toString();


        Place p = new Place(Name, Adr, Cate, Dis);

        textName.setText(p.getName());
        textAdr.setText(p.getAddress());
        textCate.setText(p.getCategory());
        textDis.setText(p.getDistance());
        //여기에 가져오기만 하면 될거같은데 모다겠다

    }
    private class GetDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return NetworkUtils.fetchData("http://kshind1.dothome.co.kr/walk.php");
        }

        @Override
        protected void onPostExecute(String result) {
            TextView nameTextView = (TextView) findViewById(R.id.cource_name);
            TextView lenTextView = (TextView) findViewById(R.id.walk_len);
            TextView hourTextView = (TextView) findViewById(R.id.walk_hour);
            TextView caloryTextView = (TextView) findViewById(R.id.waste_calory);
            TextView usernameTextView = (TextView) findViewById(R.id.user_name);
            TextView stepTextView = (TextView) findViewById(R.id.current_step);
            DataProcessor.processJsonData(result, nameTextView, lenTextView, hourTextView, caloryTextView, usernameTextView, stepTextView);
        }
    }
    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        // 위치 정보를 가져왔으면 API 호출
                        searchNearbyPlaces(location.getLatitude(), location.getLongitude(), "공원");
                    }
                });
    }

    private void searchNearbyPlaces(double latitude, double longitude, String keyword) {
        String url = "https://dapi.kakao.com/v2/local/search/keyword?y=" + latitude +
                "&x=" + longitude +
                "&radius=1000&query=" + keyword;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "KakaoAK " + kakaoApiKey)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    runOnUiThread(() -> processResponse(responseData));
                }
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                // Handle failure
            }
        });
    }

    private void processResponse(String responseData) {
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            JSONArray placesArray = jsonObject.getJSONArray("documents");

            List<Place> places = new ArrayList<>();
            for (int i = 0; i < placesArray.length(); i++) {
                JSONObject placeObject = placesArray.getJSONObject(i);
                String placeName = placeObject.getString("place_name");
                String placeAddress = placeObject.getString("road_address_name");
                String placeCategory = placeObject.getString("category_name");
                String placeDistance = placeObject.getString("distance");


                places.add(new Place(placeName, placeAddress,placeCategory,placeDistance));
            }

            runOnUiThread(() -> placeAdapter.setPlaces(places));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static class Place {
        private final String name;
        private final String address;
        private final String category;
        private final String distance;


        public Place(String name, String address, String category, String distance) {
            this.name = name;
            this.address = address;
            this.category = category;
            this.distance = distance;

        }

        public String getName() {
            return name;
        }

        public String getAddress() {return address;}

        public String getCategory() {return category;}

        public String getDistance() {
            return distance+"m";
        }


    }

    private static class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {
        private List<Place> places = new ArrayList<>();
        private static final int MAX_ITEMS = 3;

        public void setPlaces(List<Place> places) {
            // 최대 3개의 아이템만 유지
            this.places = places.subList(0, Math.min(places.size(), MAX_ITEMS));
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PlaceAdapter.PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item, parent, false);
            return new PlaceAdapter.PlaceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceAdapter.PlaceViewHolder holder, int position) {
            Place place = places.get(position);
            holder.nameTextView.setText(place.getName());
            holder.addressTextView.setText(place.getAddress());
            holder.categoryTextView.setText(place.getCategory());
            holder.distanceTextView.setText(place.getDistance());

            //상세 화면 보기로 전환
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.itemView.getContext(), WalkInfoActivity.class);
                    ContextCompat.startActivity(holder.itemView.getContext(), intent, null);
                }
            });

        }

        @Override
        public int getItemCount() {
            return places.size();
        }

        public static class PlaceViewHolder extends RecyclerView.ViewHolder {
            TextView nameTextView;
            TextView addressTextView;
            TextView categoryTextView;
            TextView distanceTextView;

            public PlaceViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.nameTextView);
                addressTextView = itemView.findViewById(R.id.addressTextView);
                categoryTextView = itemView.findViewById(R.id.categoryTextView);
                distanceTextView = itemView.findViewById(R.id.distanceTextView);

            }
        }
    }

}