package com.example.walkwithme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentManager manager;
    FragmentTransaction transaction;
    HomeFragment fragment1;
    WalkFragment fragment2;
    String TAG = MainActivity.class.getSimpleName();
    private ListView listView = null;
    private ListAdapter adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //리스트 뷰
        listView = (ListView) findViewById(R.id.listview);
        ListViewAdapter adapter = new ListViewAdapter();

        //listView.setAdapter(adapter);


        System.out.println("listView: " + listView);
        System.out.println("adapter: " + adapter);


        adapter.addItem(R.drawable.rectangle_40, "전남대학교 용지", "저수지");


        //액션바 없애기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //하단 네비게이션 바
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.menu_home:
                        setFrag(0);
                        break;
                    case R.id.menu_walk:
                        setFrag(1);
                        break;
                }

                return false;
            }
        });

        fragment1 = new HomeFragment();
        fragment2 = new WalkFragment();

        setFrag(0); //첫화면 설정

        //메뉴선택시 화면 변경 인텐트
        LinearLayout linear1 = (LinearLayout) findViewById(R.id.menu_layout1);
        LinearLayout linear2 = (LinearLayout) findViewById(R.id.menu_layout2);
        LinearLayout linear3 = (LinearLayout) findViewById(R.id.menu_layout3);

        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WalkActivity.class);
                startActivity(intent);
            }
        });


    }


    /* 리스트뷰 어댑터 */
    public class ListViewAdapter extends BaseAdapter {
        ArrayList<WalkItem> items = new ArrayList<WalkItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(int img, String name, String type) {
            WalkItem item = new WalkItem();

            item.setImg_pic(img);
            item.setName(name);
            item.setType(type);

            System.out.println("addItem 실행중");
        }

        @Override
        public Object getItem(int position) {
            System.out.println("getItem 실행중");
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {

            System.out.println("getItemID 실행중");
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final WalkItem walkItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.activity_list_item, viewGroup, false);

                ImageView img_pic = (ImageView) convertView.findViewById(R.id.img_pic);
                TextView txt_name = (TextView) convertView.findViewById(R.id.txt_name);
                TextView txt_type = (TextView) convertView.findViewById(R.id.txt_type);

                img_pic.setImageResource(walkItem.getImg_pic());
                txt_name.setText(walkItem.getName());
                txt_type.setText(walkItem.getType());

                System.out.println("getView 실행");

            }

            Log.d(TAG, "getView() - [ "+position+" ] "+walkItem.getName());

            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, walkItem.getName()+" 입니당! ", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;  //뷰 객체 반환
        }
    }


    //프래그먼트 교체
    private void setFrag(int n){
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        switch (n){
            case 0:
                transaction.replace(R.id.main_frame,fragment1);
                transaction.commit();
                break;
            case 1:
                transaction.replace(R.id.main_frame,fragment2);
                transaction.commit();
                break;
        }
    }

}