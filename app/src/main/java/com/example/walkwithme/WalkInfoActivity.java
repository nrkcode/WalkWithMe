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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    private FusedLocationProviderClient fusedLocationClient;
    private String kakaoApiKey = "bd2e505109d0952fa9d393afd54028aa";
    private RecyclerView recyclerView3;
    private PlaceAdapter placeAdapter;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

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

    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: 누락된 권한을 요청하기 위해 여기에서 ActivityCompat#requestPermissions를 호출하는 것을 고려해보세요.
            // 그런 다음 사용자가 권한을 부여한 경우를 처리하기 위해
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
            // 를 오버라이딩하세요. 더 많은 세부 정보는 ActivityCompat#requestPermissions의 문서를 참조하세요.

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
                String placeCategory = placeObject.getString("category_name");
                places.add(new Place(placeName, placeCategory));
            }

            runOnUiThread(() -> placeAdapter.setPlaces(places));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static class Place {
        private final String name;
        private final String address;

        public Place(String name, String address) {
            this.name = name;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }
    }

    public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {
        private List<String> placeNamesList = new ArrayList<>(); // String 타입으로만 name 저장

        public String getPlaceName(int position) {
            if (position >= 0 && position < placeNamesList.size()) {
                return placeNamesList.get(position);
            } else {
                return null;
            }
        }

        public void setPlaces(List<Place> places) {
            // 기존에 저장된 이름들을 비웁니다.
            placeNamesList.clear();

            // 필터링된 데이터의 name만을 리스트에 추가합니다.
            placeNamesList.addAll(extractNames(places));

            notifyDataSetChanged();
        }

        private List<String> extractNames(List<Place> allPlaces) {
            // 여기에서 name만을 추출하여 리스트에 저장합니다.
            List<String> extractedNames = new ArrayList<>();
            for (Place place : allPlaces) {
                extractedNames.add(place.getName());
            }
            return extractedNames;
        }

        @NonNull
        @Override
        public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item, parent, false);
            return new PlaceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceAdapter.PlaceViewHolder holder, int position) {
            // 여기서는 리스트로 저장된 placeNamesList를 사용합니다.
            String placeName = placeNamesList.get(position);
            holder.nameTextView.setText(placeName);

            // 주소 등의 다른 정보는 이 부분에서 필요에 따라 추가하여 사용합니다.
            // 3번째 아이템에 대한 처리
            TextView textName = holder.itemView.findViewById(R.id.nameTextView);
            LinearLayout list = holder.itemView.findViewById(R.id.list_detail);

            if (list != null) {
                list.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int clickedPosition = holder.getAdapterPosition();

                        // Find the TextView within the LinearLayout
                        TextView nameTextView = holder.itemView.findViewById(R.id.nameTextView);

                        // Update the TextView with the data from the clicked item
                        String list_string = placeAdapter.getPlaceName(clickedPosition);
                        nameTextView.setText(list_string);
                    }
                });
            } else {
                // R.id.list_detail을 찾을 수 없는 경우에 대한 처리
                Log.e("WalkInfoActivity", "R.id.list_detail not found");
            }
        }


        @Override
        public int getItemCount() {
            return placeNamesList.size();
        }

        public class PlaceViewHolder extends RecyclerView.ViewHolder {
            TextView nameTextView;
            TextView addressTextView;

            public PlaceViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.nameTextView);
                // addressTextView 등의 다른 뷰도 필요에 따라 초기화합니다.
            }
        }
    }

    public String getItemName(int position) {
        return placeAdapter.getPlaceName(position);
    }
}
