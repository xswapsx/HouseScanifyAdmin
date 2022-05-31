package com.appynitty.adminapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.fragments.AttendanceFragment;
import com.appynitty.adminapp.fragments.EmpDetailsFragment;
import com.appynitty.adminapp.fragments.HouseDetailsFragment;
import com.appynitty.adminapp.fragments.LiveDataFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private Context context;
    private BottomNavigationView navigationView;
    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private FrameLayout frameLayout;
    private LiveDataFragment liveDataFragment;
    private HouseDetailsFragment houseDetailsFragment;
    private AttendanceFragment attendanceFragment;
    private EmpDetailsFragment empDetailsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        context = this;
        navigationView = findViewById(R.id.bottom_navigation);
        /*navigationView.isItemHorizontalTranslationEnabled();
        navigationView.layout(0,1,2,3);*/
        viewPager = findViewById(R.id.view_pager);
        frameLayout = findViewById(R.id.container_frame_layout);
//        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return false;
            }
        };
        viewPager.setAdapter(pagerAdapter);
        liveDataFragment = new LiveDataFragment();
        houseDetailsFragment = new HouseDetailsFragment();
        attendanceFragment = new AttendanceFragment();
        empDetailsFragment = new EmpDetailsFragment();

        setOnClick();

    }

    private void setOnClick() {
        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_live_date:
                        /* fragment = new LiveDataFragment();*/
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.nav_house_details:
                        /*fragment = new HouseDetailsFragment();*/
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.nav_attendance:
                        /* fragment = new AttendanceFragment();*/
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.nav_emp_details:
                        /* fragment = new EmpDetailsFragment();*/
                        viewPager.setCurrentItem(3);
                        break;
                }
                return loadFragment(null);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigationView.getMenu().findItem(R.id.nav_live_date).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.nav_house_details).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.nav_attendance).setChecked(true);
                        break;
                    case 3:
                        navigationView.getMenu().findItem(R.id.nav_emp_details).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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

    /*@Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_live_date:
                fragment = new LiveDataFragment();
                break;
            case R.id.nav_house_details:
                fragment = new HouseDetailsFragment();
                break;
            case R.id.nav_attendance:
                fragment = new AttendanceFragment();
                break;
            case R.id.nav_emp_details:
                fragment = new EmpDetailsFragment();
                break;
        }
        return loadFragment(fragment);
    }*/
}