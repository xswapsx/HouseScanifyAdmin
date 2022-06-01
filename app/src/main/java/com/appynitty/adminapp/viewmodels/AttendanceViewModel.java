package com.appynitty.adminapp.viewmodels;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.AttendanceDTO;
import com.appynitty.adminapp.repositories.AttendanceRepository;

import java.util.List;

public class AttendanceViewModel extends ViewModel {
    private static final String TAG = "AttendanceViewModel";

    public MutableLiveData<Integer> totalEntries = new MutableLiveData<>();
    public MutableLiveData<List<AttendanceDTO>> attendanceResponseLiveData;
    public MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    public AttendanceRepository attendanceRepository = AttendanceRepository.getInstance();

    public AttendanceViewModel(){
        mProgressMutableData.postValue(View.VISIBLE);
    }

}
