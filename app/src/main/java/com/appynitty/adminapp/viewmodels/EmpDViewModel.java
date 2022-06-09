package com.appynitty.adminapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.EmpDModel;

import java.util.List;

public class EmpDViewModel extends ViewModel {
    private static final String TAG = "EmpDViewModel";

    public MutableLiveData<Integer> totalEntries = new MutableLiveData<>();
    public MutableLiveData<List<EmpDModel>> empDResLiveData;
    public MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
}
