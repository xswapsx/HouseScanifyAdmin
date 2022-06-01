package com.appynitty.adminapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.fragments.AttendanceFragment;
import com.appynitty.adminapp.fragments.EmpDetailsFragment;
import com.appynitty.adminapp.fragments.HouseDetailsFragment;
import com.appynitty.adminapp.fragments.LiveDataFragment;
import com.appynitty.adminapp.viewmodels.UlbDataViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private Context context;
    private BottomNavigationView navigationView;
    private FrameLayout frameLayout;
    private LiveDataFragment liveDataFragment;
    private HouseDetailsFragment houseDetailsFragment;
    private AttendanceFragment attendanceFragment;
    private EmpDetailsFragment empDetailsFragment;
    UlbDataViewModel ulbDataViewModel;
    private String appId, ulbName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    public Bundle getUlbData() {
        Bundle ulbData = new Bundle();
        ulbData.putString("val1", appId);
        ulbData.putString("val2", ulbName);
        return ulbData;
    }

    private void init() {

        context = this;
        Intent intent = getIntent();
        appId = intent.getStringExtra("appId");
        ulbName = intent.getStringExtra("ulbName");
//        ulbDataViewModel = ViewModelProviders.of(this).get(UlbDataViewModel.class);
        navigationView = findViewById(R.id.bottom_navigation);
        frameLayout = findViewById(R.id.container_frame_layout);
        liveDataFragment = new LiveDataFragment();
        houseDetailsFragment = new HouseDetailsFragment();
        attendanceFragment = new AttendanceFragment();
        empDetailsFragment = new EmpDetailsFragment();

        setOnClick();

        loadFragment(new LiveDataFragment());

    }

    public HashMap<String, String> getResult() {
        HashMap<String, String> map = new HashMap<>();
        map.put("appId", appId);
        map.put("ulbName", ulbName);
        return map;
    }

    private void setOnClick() {
        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_live_date:
                        fragment = new LiveDataFragment();
                        /* viewPager.setCurrentItem(0);*/
                        break;
                    case R.id.nav_house_details:
                        fragment = new HouseDetailsFragment();
                        /*viewPager.setCurrentItem(1);*/
                        break;
                    case R.id.nav_attendance:
                        fragment = new AttendanceFragment();
                        /*viewPager.setCurrentItem(2);*/
                        break;
                    case R.id.nav_emp_details:
                        fragment = new EmpDetailsFragment();
                        /*viewPager.setCurrentItem(3);*/
                        break;
                }
                return loadFragment(fragment);
            }
        });

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_frame_layout, fragment);
            ft.commit();
            return true;
        }
        return false;
    }

}