package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.HouseDetailsImageDTO;
import com.appynitty.adminapp.repositories.HouseDetailsImageRepo;

import java.util.List;

public class HouseDetailsImageVM extends ViewModel {
    private static final String TAG = "HouseDatailsImageVM";
    private int sImgCount = 0;
    public HouseDetailsImageRepo houseDetailsImageRepo = HouseDetailsImageRepo.getInstance();
    public MutableLiveData<Integer> mProgressLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> imageCountLiveData = new MutableLiveData<>();
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
                for (HouseDetailsImageDTO house : QrImageLiveData.getValue()
                ) {
                    if (!house.getqRCodeImage().matches("/Images/default_not_upload.png")) {
                        sImgCount += 1;

                    }
                }
                imageCountLiveData.setValue(sImgCount);
                Log.e(TAG, "onResponse: ImageCount " + sImgCount);
            }

            @Override
            public void onFailure(Throwable s) {
                mProgressLiveData.postValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + s);
            }
        });

    }

    public void callHouseApi() {
        mProgressLiveData.postValue(View.VISIBLE);
        houseDetailsImageRepo.getHouseQrImageData(new HouseDetailsImageRepo.IQrImageResponse() {
            @Override
            public void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData) {
                mProgressLiveData.postValue(View.INVISIBLE);
                houseQrImagesLiveData.setValue(QrImageLiveData.getValue());
                for (HouseDetailsImageDTO house : QrImageLiveData.getValue()
                ) {
                    if (!house.getqRCodeImage().matches("/Images/default_not_upload.png")) {
                        sImgCount += 1;

                    }
                }
                imageCountLiveData.setValue(sImgCount);
                Log.e(TAG, "onResponse: ImageCount " + sImgCount);
            }

            @Override
            public void onFailure(Throwable s) {
                mProgressLiveData.postValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + s);
            }
        });
    }

    public void callDumpYardApi() {
        mProgressLiveData.postValue(View.VISIBLE);
        houseDetailsImageRepo.getDumpyQrImageData(new HouseDetailsImageRepo.IQrImageResponse() {
            @Override
            public void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData) {
                mProgressLiveData.postValue(View.INVISIBLE);
                if (QrImageLiveData.getValue().size() == 0) {
                    imageCountLiveData.setValue(0);
                } else {
                    dumpyQrImagesLiveData.setValue(QrImageLiveData.getValue());
                    for (HouseDetailsImageDTO house : QrImageLiveData.getValue()
                    ) {
                        if (house.getqRCodeImage() != null) {
                            if (!house.getqRCodeImage().matches("/Images/default_not_upload.png")) {
                                sImgCount += 1;

                            }
                        }
                    }
                    imageCountLiveData.setValue(sImgCount);
                    Log.e(TAG, "onResponse: ImageCount " + sImgCount);
                }


            }

            @Override
            public void onFailure(Throwable s) {
                mProgressLiveData.postValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + s);
            }
        });
    }

    public void callLiquidWasteApi() {
        mProgressLiveData.postValue(View.VISIBLE);
        houseDetailsImageRepo.getLiquidQrImageData(new HouseDetailsImageRepo.IQrImageResponse() {
            @Override
            public void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData) {
                mProgressLiveData.postValue(View.INVISIBLE);
                liquidQrImagesLiveData.setValue(QrImageLiveData.getValue());
                for (HouseDetailsImageDTO house : QrImageLiveData.getValue()
                ) {
                    if (house.getqRCodeImage() != null) {
                        if (!house.getqRCodeImage().matches("/Images/default_not_upload.png")) {
                            sImgCount += 1;

                        }
                    }
                }
                imageCountLiveData.setValue(sImgCount);
                Log.e(TAG, "onResponse: ImageCount " + sImgCount);
            }

            @Override
            public void onFailure(Throwable s) {
                mProgressLiveData.postValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + s);
            }
        });
    }

    public void callStreetWasteApi() {
        mProgressLiveData.postValue(View.VISIBLE);
        houseDetailsImageRepo.getStreetQrImageData(new HouseDetailsImageRepo.IQrImageResponse() {
            @Override
            public void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData) {
                mProgressLiveData.postValue(View.INVISIBLE);
                streetQrImagesLiveData.setValue(QrImageLiveData.getValue());
                for (HouseDetailsImageDTO house : QrImageLiveData.getValue()
                ) {
                    if (!house.getqRCodeImage().matches("/Images/default_not_upload.png")) {
                        sImgCount += 1;

                    }
                }
                imageCountLiveData.setValue(sImgCount);
                Log.e(TAG, "onResponse: ImageCount " + sImgCount);
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

    public MutableLiveData<List<HouseDetailsImageDTO>> getDumpyQrImagesLiveData() {
        return dumpyQrImagesLiveData;
    }

    public MutableLiveData<List<HouseDetailsImageDTO>> getLiquidQrImagesLiveData() {
        return liquidQrImagesLiveData;
    }

    public MutableLiveData<List<HouseDetailsImageDTO>> getStreetQrImagesLiveData() {
        return streetQrImagesLiveData;
    }

    public LiveData<Integer> getProgress() {
        return mProgressLiveData;
    }

    public LiveData<Integer> getImageCount() {
        return imageCountLiveData;
    }
}
