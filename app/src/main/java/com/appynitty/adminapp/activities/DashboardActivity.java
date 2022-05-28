package com.appynitty.adminapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.DashboardAdapter;
import com.appynitty.adminapp.databinding.ActivityDashboard1Binding;
import com.appynitty.adminapp.models.DashboardDTO;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.viewmodels.DashboardViewModel;
import com.pixplicity.easyprefs.library.Prefs;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.List;


public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";
    private Context context;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerDash;
    private LinearLayoutManager layoutManager;
    private DashboardAdapter adapter;
    private DashboardViewModel dashboardViewModel;
    private ActivityDashboard1Binding binding;
    private ImageView btnLogout;
    AlertDialog.Builder builder;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        context = this;
        binding = DataBindingUtil.setContentView(DashboardActivity.this, R.layout.activity_dashboard1);
        binding.setLifecycleOwner(this);
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        binding.setDashboardViewModel(dashboardViewModel);

        builder = new AlertDialog.Builder(this);


        refreshLayout = findViewById(R.id.refresh_layout);
        recyclerDash = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(context);
        btnLogout = findViewById(R.id.ivLogout);


        dashboardViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                binding.progressBar.setVisibility(visibility);
            }
        });

        dashboardViewModel.getLogoutLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                logoutUser(s);

            }
        });

        dashboardViewModel.getDashboardResponse().observe(this, new Observer<List<DashboardDTO>>() {
            @Override
            public void onChanged(List<DashboardDTO> dashboardResults) {
                Log.e(TAG, "onChanged: List of Ulbs:" + dashboardResults.get(0).getUlb());
            }
        });

        setOnClick();
        setOnRecycler();

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

    public void logoutUser(String s) {

        builder.setMessage("Do you want to logout ?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Prefs.remove(MainUtils.USER_ID);
                        Prefs.remove(MainUtils.EMP_TYPE);
                        Prefs.putBoolean(MainUtils.IS_LOGIN, false);
                        startActivity(new Intent(context, LoginActivity.class));

                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        DynamicToast.makeSuccess(context, s).show();
                        context.startActivity(intent);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        alert = builder.create();
        //Setting the title manually
        alert.setTitle("Logout!");
        alert.show();
    }
}