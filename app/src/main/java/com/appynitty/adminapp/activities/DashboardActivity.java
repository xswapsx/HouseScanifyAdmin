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
import com.appynitty.adminapp.services.LocationService;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.utils.MyApplication;
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
    Boolean switchState = false;

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
        binding.tvUserName.setText(getString(R.string.greetings) + " " + Prefs.getString(MainUtils.USER_NAME) + " !");
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

        dutyViewModel.checkAttendance();  //checking attendance from server

        if (Prefs.contains(MainUtils.IS_ATTENDANCE_OFF)) {
            if (Prefs.getBoolean(MainUtils.IS_ATTENDANCE_OFF)) {
                binding.btnSwitch.setChecked(false);
            } else {
                binding.btnSwitch.setChecked(true);
                hideViews(false);
            }
        }

        refreshLayout = findViewById(R.id.refresh_layout);
        layoutManager = new LinearLayoutManager(context);
        btnLogout = findViewById(R.id.ivLogout);
        ulbList = new ArrayList<>();

        dutyViewModel.getAttendanceChk().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Prefs.putBoolean(MainUtils.IS_ATTENDANCE_OFF, aBoolean);
                Log.e(TAG, "onChanged: Duty_off:" + aBoolean);
                if (aBoolean) {
                    binding.btnSwitch.setChecked(false);   // if attendance is off
                    hideViews(true);
                    ((MyApplication) MainUtils.mainApplicationConstant).stopLocationTracking();

                } else {
                    binding.btnSwitch.setChecked(true);     // if attendance if on
                    hideViews(false);
                }

            }
        });

        dutyViewModel.getDutyDTOMutableLiveData().observe(this, new Observer<DutyDTO>() {
            @Override
            public void onChanged(DutyDTO dutyDTO) {
                Log.e(TAG, "onChanged: isAttendanceOff? ans: " + dutyDTO.getIsAttendenceOff());
                if (dutyDTO.getOnFailureMsg() != null) {
                    Log.e(TAG, "onChanged: " + dutyDTO.getOnFailureMsg().contains("Failed to connect"));
                    if (Prefs.getBoolean(MainUtils.IS_ATTENDANCE_OFF)) {
                        binding.btnSwitch.setChecked(true);
                    } else {
                        binding.btnSwitch.setChecked(false);
                    }
                    if (dutyDTO.getOnFailureMsg().contains("Failed to connect")) {
                        DynamicToast.makeWarning(DashboardActivity.this, "Please check your internet connection\nand try again later.").show();
                        binding.btnSwitch.setChecked(!Prefs.getBoolean(MainUtils.IS_ATTENDANCE_OFF));  //here we setChecked as
                        // !Prefs.getBoolean(MainUtils.IS_ATTENDANCE_OFF) because attendanceOff will be true so need the opposite of true so add this
                    }
                } else {
                    if (!dutyDTO.getIsAttendenceOff()) {
                        DynamicToast.makeSuccess(DashboardActivity.this, dutyDTO.getStatus()).show();
                        Prefs.putBoolean(MainUtils.IS_ATTENDANCE_OFF, dutyDTO.getIsAttendenceOff());
                        hideViews(false);
                        if (!MainUtils.isMyServiceRunning(MainUtils.mainApplicationConstant, LocationService.class))
                            ((MyApplication) MainUtils.mainApplicationConstant).startLocationTracking();
                    } else if (dutyDTO.getIsAttendenceOff()) {
                        Prefs.putBoolean(MainUtils.IS_ATTENDANCE_OFF, dutyDTO.getIsAttendenceOff());
                        hideViews(true);
                        if (MainUtils.isMyServiceRunning(MainUtils.mainApplicationConstant, LocationService.class))
                            ((MyApplication) MainUtils.mainApplicationConstant).stopLocationTracking();
                    }
                }


            }
        });

        dutyViewModel.getDutyErrorLiveData().observe(DashboardActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                DynamicToast.makeError(context, s).show();
                binding.btnSwitch.setChecked(!Prefs.getBoolean(MainUtils.IS_ATTENDANCE_OFF));
            }
        });
        /*dutyViewModel.getStatusChk().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(context, "state: " + aBoolean, Toast.LENGTH_SHORT).show();
            }
        });*/
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
                if (Prefs.getBoolean(MainUtils.IS_ATTENDANCE_OFF) || empType.matches("A")) {
                    logoutUser(s);
                } else {
                    DynamicToast.makeWarning(DashboardActivity.this, "Please turn off the Dashboard first!").show();
                }

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

        binding.btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication) MainUtils.mainApplicationConstant).startLocationTracking();
                if (!Prefs.getBoolean(MainUtils.IS_ATTENDANCE_OFF)) {
//                    showDutyOffConfirmation();
                    MainUtils.showDialog(context, "Are you sure you want to turn of the dashboard?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dutyViewModel.changeDuty(false);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            binding.btnSwitch.setChecked(true);
                            dialogInterface.dismiss();
                        }
                    });

                } else if (Prefs.getBoolean(MainUtils.IS_ATTENDANCE_OFF)) {
                    dutyViewModel.changeDuty(true);
                }
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

        MainUtils.showDialog(context, "Are you sure you want to logout the app?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Prefs.remove(MainUtils.USER_ID);
                Prefs.remove(MainUtils.EMP_TYPE);
                Prefs.putBoolean(MainUtils.IS_LOGIN, false);
                Prefs.putBoolean(MainUtils.IS_ATTENDANCE_OFF, true);
//                        startActivity(new Intent(context, LoginActivity.class));

                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                DynamicToast.makeSuccess(context, s).show();
                context.startActivity(intent);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    /*@Override
    protected void onPostResume() {
        super.onPostResume();
        if (Prefs.getBoolean(MainUtils.IS_ATTENDANCE_OFF))
            ((MyApplication) MainUtils.mainApplicationConstant).stopLocationTracking();
        else
            ((MyApplication) MainUtils.mainApplicationConstant).stopLocationTracking();

    }*/

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