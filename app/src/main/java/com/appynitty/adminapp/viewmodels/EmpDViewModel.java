package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.repositories.EmpDRepository;

import java.util.List;

public class EmpDViewModel extends ViewModel {
    private static final String TAG = "EmpDViewModel";

    public MutableLiveData<Integer> totalEntries = new MutableLiveData<>();
    public MutableLiveData<List<EmpDModelDTO>> empDResponseLiveData;
    public MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();

    public EmpDRepository empDRepository = EmpDRepository.getInstance();

    public EmpDViewModel(String appId){
        mProgressMutableData.postValue(View.VISIBLE);

        empDRepository.getEmpDList(appId, new EmpDRepository.IEmpDResponse() {
            @Override
            public void onResponse(MutableLiveData<List<EmpDModelDTO>> empDResponse) {
                mProgressMutableData.setValue(View.INVISIBLE);
                totalEntries.setValue(empDResponse.getValue().size());
                empDResponseLiveData.setValue(empDResponse.getValue());
                Log.e(TAG,"onResponse: " + empDResponse.getValue());

            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.setValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }

    public MutableLiveData<List<EmpDModelDTO>> getEmpDResponseLiveData() {
        if (empDResponseLiveData == null) {
            empDResponseLiveData = new MutableLiveData<>();
        }
        return empDResponseLiveData;
    }

}
