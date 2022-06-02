package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.models.AttendanceDTO;
import com.appynitty.adminapp.repositories.AttendanceRepository;

import java.util.List;

public class AttendanceViewModel extends ViewModel {
    private static final String TAG = "AttendanceViewModel";

    public MutableLiveData<Integer> totalEntries = new MutableLiveData<>();
    public MutableLiveData<List<AttendanceDTO>> attendanceResponseLiveData;
    public MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    public AttendanceRepository attendanceRepository = AttendanceRepository.getInstance();

    public AttendanceViewModel() {
        mProgressMutableData.postValue(View.VISIBLE);
        attendanceRepository.getListOfAttendance(new AttendanceRepository.IAttendanceResponse() {
            @Override
            public void onResponse(MutableLiveData<List<AttendanceDTO>> attendanceResponse) {
                mProgressMutableData.setValue(View.INVISIBLE);
                totalEntries.setValue(attendanceResponse.getValue().size());
                attendanceResponseLiveData.setValue(attendanceResponse.getValue());
                Log.e(TAG, "onResponse: " + attendanceResponse.getValue().get(0).getUserName()
                        + attendanceResponse.getValue().get(1).getStartDate() + attendanceResponse.getValue().get(2).getEndDate()
                        + attendanceResponse.getValue().get(3).getHouseCount()
                        + attendanceResponse.getValue().get(4).getDumpYardCount()
                        + attendanceResponse.getValue().get(5).getLiquidCount()
                        + attendanceResponse.getValue().get(6).getStreetCount());
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.setValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + t.getMessage());

            }
        });
    }

    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.card_filter :
        }
    }


    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }

}
