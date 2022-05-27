package com.appynitty.adminapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.DashboardAdapter;
import com.appynitty.adminapp.utils.MainUtils;
import com.pixplicity.easyprefs.library.Prefs;


public class DashboardActivity extends AppCompatActivity {
    private Context context;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerDash;
    private LinearLayoutManager layoutManager;
    private DashboardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard1);
        init();
    }

    private void init() {
        context = this;
        refreshLayout = findViewById(R.id.refresh_layout);
        recyclerDash = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(context);
        setOnClick();
        setOnRecycler();

        if (!Prefs.getBoolean(MainUtils.IS_LOGIN, false)) {
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);

        }
    }

    private void setOnClick() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void setOnRecycler() {
        adapter = new DashboardAdapter(context);
        refreshLayout.setRefreshing(false);
        recyclerDash.setLayoutManager(layoutManager);
        recyclerDash.setAdapter(adapter);
    }
}