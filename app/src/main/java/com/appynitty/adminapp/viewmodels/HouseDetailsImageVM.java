package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.HouseDetailsImageDTO;
import com.appynitty.adminapp.repositories.HouseDetailsImageRepo;

import java.util.List;

public class HouseDetailsImageVM extends ViewModel {
    private static final String TAG = "HouseDatailsImageVM";
    public HouseDetailsImageRepo houseDetailsImageRepo = HouseDetailsImageRepo.getInstance();
    public MutableLiveData<Integer> mProgressLiveData = new MutableLiveData<>();
    public MutableLiveData<List<HouseDetailsImageDTO>> houseQrImagesLiveData = new MutableLiveData<>();
    public MutableLiveData<List<HouseDetailsImageDTO>> dumpyQrImagesLiveData = new MutableLiveData<>();
    public MutableLiveData<List<HouseDetailsImageDTO>> streetQrImagesLiveData = new MutableLiveData<>();
    public MutableLiveData<List<HouseDetailsImageDTO>> liquidQrImagesLiveData = new MutableLiveData<>();

    public HouseDetailsImageVM() {
        mProgressLiveData.postValue(View.VISIBLE);
        houseDetailsImageRepo.getHouseQrImageData(new HouseDetailsImageRepo.IQrImageResponse() {
            @Override
            public void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData) {
                mProgressLiveData.postValue(View.INVISIBLE);
                houseQrImagesLiveData.setValue(QrImageLiveData.getValue());
//                Log.e(TAG, "onResponse: " + QrImageLiveData.getValue());
            }

            @Override
            public void onFailure(Throwable s) {
                mProgressLiveData.postValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + s);
            }
        });
    }

    public MutableLiveData<List<HouseDetailsImageDTO>> getHouseQrImagesLiveData() {
        return houseQrImagesLiveData;
    }
}
