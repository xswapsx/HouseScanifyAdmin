package com.appynitty.adminapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.DashboardAdapter;
import com.appynitty.adminapp.databinding.ActivityDashboard1Binding;
import com.appynitty.adminapp.models.DashboardDTO;
import com.appynitty.adminapp.models.DutyDTO;
import com.appynitty.adminapp.models.UlbDTO;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.viewmodels.DashboardViewModel;
import com.appynitty.adminapp.viewmodels.DutyOnOffViewModel;
import com.pixplicity.easyprefs.library.Prefs;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.ArrayList;
import java.util.List;


public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";
    private String empType = "";
    private Context context;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager layoutManager;
    private DashboardAdapter adapter;
    private DashboardViewModel dashboardViewModel;
    private DutyOnOffViewModel dutyViewModel;
    private ActivityDashboard1Binding binding;
    private ImageView btnLogout;
    private List<UlbDTO> ulbList;
    AlertDialog.Builder builder;
    AlertDialog alert;
    boolean doubleBackToExitPressedOnce = false;

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
        dutyViewModel = ViewModelProviders.of(this).get(DutyOnOffViewModel.class);
        binding.setDashboardViewModel(dashboardViewModel);
        binding.setDutyViewModel(dutyViewModel);

        builder = new AlertDialog.Builder(this);
        empType = Prefs.getString(MainUtils.EMP_TYPE);
        if (!empType.isEmpty() && empType.matches("SA")) {
            binding.empType.setText(R.string.subAdmin);
            binding.dutyLayout.setVisibility(View.VISIBLE);
            hideViews(true);
        } else {
            binding.empType.setText(R.string.adminTitle);
            binding.crdUserRightsBtn.setVisibility(View.VISIBLE);
            binding.dutyLayout.setVisibility(View.GONE);
            binding.crdUserRightsBtn.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            hideViews(false);
        }

        refreshLayout = findViewById(R.id.refresh_layout);
        layoutManager = new LinearLayoutManager(context);
        btnLogout = findViewById(R.id.ivLogout);
        ulbList = new ArrayList<>();


        dutyViewModel.getDutyDTOMutableLiveData().observe(this, new Observer<DutyDTO>() {
            @Override
            public void onChanged(DutyDTO dutyDTO) {
                Log.e(TAG, "onChanged: isAttendanceOff? ans: " + dutyDTO.getIsAttendenceOff());
                if (!dutyDTO.getIsAttendenceOff()) {
                    DynamicToast.makeSuccess(DashboardActivity.this, dutyDTO.getStatus()).show();
                    hideViews(false);
                } else if (dutyDTO.getIsAttendenceOff()) {
                    hideViews(true);
                }

            }
        });
        binding.searchUlb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
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

        dashboardViewModel.getUserRightStatus().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                startActivity(new Intent(context, UserRightsActivity.class));
            }
        });
        dashboardViewModel.getDashboardResponse().observe(this, new Observer<List<DashboardDTO>>() {

            @Override
            public void onChanged(List<DashboardDTO> dashboardResults) {
                ulbList.clear();
                for (int i = 0; i < dashboardResults.size(); i++) {
                    ulbList.add(new UlbDTO(dashboardResults.get(i).getUlb(),
                            dashboardResults.get(i).getAppid()));
                }
                setOnRecycler(ulbList);
            }
        });
        setOnClick();
    }

    private void hideViews(Boolean s) {
        if (s) {
            binding.recyclerView.setVisibility(View.GONE);
            binding.liLUlbList.setVisibility(View.GONE);
            binding.liLUlbCount.setVisibility(View.GONE);
            binding.cardDashItem.setVisibility(View.GONE);
        } else {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.liLUlbList.setVisibility(View.VISIBLE);
            binding.liLUlbCount.setVisibility(View.VISIBLE);
            binding.cardDashItem.setVisibility(View.VISIBLE);
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

    private void setOnRecycler(List<UlbDTO> ulbList) {
        adapter = new DashboardAdapter(context, ulbList);
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }

    private void filter(String text) {
        List<UlbDTO> filteredList = new ArrayList<>();

        for (UlbDTO item : ulbList) {
            if (item.getUlbName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
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

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}