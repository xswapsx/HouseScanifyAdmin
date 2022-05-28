package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.DashboardDTO;
import com.appynitty.adminapp.repositories.DashboardRepository;

import java.util.List;

public class DashboardViewModel extends ViewModel {
    private static final String TAG = "DashboardViewModel";
    public MutableLiveData<String> logoutLiveData = new MutableLiveData<>();
    public MutableLiveData<DashboardDTO> dashboardRequestLiveData;
    public MutableLiveData<List<DashboardDTO>> dashboardResponseLiveData;
    public MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    public DashboardRepository dashboardRepository = DashboardRepository.getInstance();


    public DashboardViewModel() {
        mProgressMutableData.postValue(View.VISIBLE);
        dashboardRepository.getListOfULBs(new DashboardRepository.IDashboardResponse() {

            @Override
            public void onResponse(MutableLiveData<List<DashboardDTO>> dashboardResponse) {
                mProgressMutableData.setValue(View.INVISIBLE);
                dashboardResponseLiveData.setValue(dashboardResponse.getValue());
                Log.e(TAG, "onResponse: " + dashboardResponse.getValue().get(0).getUlb());
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.setValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void onClick(View view) {

        logoutLiveData.setValue("Logout successfully!");
    }

    public MutableLiveData<String> getLogoutLiveData() {
        return logoutLiveData;
    }

    public MutableLiveData<List<DashboardDTO>> getDashboardResponse() {
        if (dashboardResponseLiveData == null) {
            dashboardResponseLiveData = new MutableLiveData<>();
        }
        return dashboardResponseLiveData;
    }

    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }
}
