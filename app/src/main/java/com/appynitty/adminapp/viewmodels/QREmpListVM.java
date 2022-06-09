package com.appynitty.adminapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.QREmployeeDTO;
import com.appynitty.adminapp.repositories.QREmpListRepo;

import java.util.List;

public class QREmpListVM extends ViewModel {
    private static final String TAG = "QREmpListVM";
    public QREmpListRepo qrEmpListRepo = QREmpListRepo.getInstance();
    public MutableLiveData<String> text = new MutableLiveData<>();
    public MutableLiveData<List<QREmployeeDTO>> QREmpListLiveData = new MutableLiveData<>();

    public void init() {
        qrEmpListRepo.getQREmpsList(new QREmpListRepo.IQREmpListResponse() {
            @Override
            public void onResponse(MutableLiveData<List<QREmployeeDTO>> qrEmpListLiveData) {
                QREmpListLiveData.setValue(qrEmpListLiveData.getValue());
            }

            @Override
            public void onFailure(Throwable s) {
                Log.e(TAG, "onFailure: " + s);
            }
        });
    }

    public MutableLiveData<List<QREmployeeDTO>> getQREmpListLiveData() {
        return QREmpListLiveData;
    }
}
