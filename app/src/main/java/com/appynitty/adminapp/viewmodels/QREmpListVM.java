package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.QREmployeeDTO;
import com.appynitty.adminapp.repositories.QREmpListRepo;

import java.util.ArrayList;
import java.util.List;

public class QREmpListVM extends ViewModel {
    private static final String TAG = "QREmpListVM";
    public QREmpListRepo qrEmpListRepo = QREmpListRepo.getInstance();
    public MutableLiveData<String> text = new MutableLiveData<>();
    public MutableLiveData<ArrayList<QREmployeeDTO>> QREmpListLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> mProgressLiveData = new MutableLiveData<>();

    public QREmpListVM() {
        mProgressLiveData.postValue(View.VISIBLE);
        qrEmpListRepo.getQREmpsList(new QREmpListRepo.IQREmpListResponse() {

            @Override
            public void onResponse(MutableLiveData<ArrayList<QREmployeeDTO>> qrEmpListLiveData) {
                mProgressLiveData.postValue(View.INVISIBLE);
                QREmpListLiveData.setValue(qrEmpListLiveData.getValue());
            }

            @Override
            public void onFailure(Throwable s) {
                mProgressLiveData.postValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + s);
            }
        });
    }

    public MutableLiveData<ArrayList<QREmployeeDTO>> getQREmpListLiveData() {
        return QREmpListLiveData;
    }

    public MutableLiveData<Integer> getmProgressLiveData() {
        return mProgressLiveData;
    }
}
