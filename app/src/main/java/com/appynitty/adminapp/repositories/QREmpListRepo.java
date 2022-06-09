package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.QREmployeeDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.QREmployeeListWebService;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QREmpListRepo {
    private static final String TAG = "QREmpListRepo";
    private static final QREmpListRepo instance = new QREmpListRepo();
    private MutableLiveData<ArrayList<QREmployeeDTO>> QrEmpListMutableLiveData = new MutableLiveData<>();
    String empType = Prefs.getString(MainUtils.EMP_TYPE);
    String userId = Prefs.getString(MainUtils.USER_ID);
    String appId = Prefs.getString(MainUtils.APP_ID);

    public static QREmpListRepo getInstance() {
        return instance;
    }

    public void getQREmpsList(IQREmpListResponse iqrEmpListResponse) {

        QREmployeeListWebService qrEmployeeListWebService = RetrofitClient.createService(QREmployeeListWebService.class, MainUtils.BASE_URL);
        Call<ArrayList<QREmployeeDTO>> qrEmployeeCall = qrEmployeeListWebService.getAllQREmployees(MainUtils.CONTENT_TYPE, empType, userId, appId);
        qrEmployeeCall.enqueue(new Callback<ArrayList<QREmployeeDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<QREmployeeDTO>> call, Response<ArrayList<QREmployeeDTO>> response) {
                if (response.code() == 200) {
                    QrEmpListMutableLiveData.setValue(response.body());
                    iqrEmpListResponse.onResponse(QrEmpListMutableLiveData);
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<QREmployeeDTO>> call, Throwable t) {
                iqrEmpListResponse.onFailure(t);
                Log.e(TAG, "onResponse: " + t.getMessage());
            }
        });


//        iqrEmpListResponse.onResponse();
    }

    public interface IQREmpListResponse {
        void onResponse(MutableLiveData<ArrayList<QREmployeeDTO>> qrEmpListLiveData);

        void onFailure(Throwable s);
    }
}
