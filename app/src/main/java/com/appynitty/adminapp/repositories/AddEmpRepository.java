package com.appynitty.adminapp.repositories;


import android.provider.Settings;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.AddEmpDTO;
import com.appynitty.adminapp.models.AddEmpResult;
import com.appynitty.adminapp.models.LoginResult;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.AddEmpWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmpRepository {
    private static final String TAG = "AddEmpRepository";

    private static final AddEmpRepository instance = new AddEmpRepository();

    public static AddEmpRepository getInstance(){
        return instance;
    }

    AddEmpDTO addEmpDTO = new AddEmpDTO();

    public void addEmpRemote(MutableLiveData<AddEmpDTO> addEmpBody, IAddEmpResponse iAddEmpResponse){
        AddEmpWebService empWebService = RetrofitClient.createService(AddEmpWebService.class, MainUtils.BASE_URL);
        JSONArray mJsonArray = new JSONArray();
        try {
            JSONObject mJsonObject = mJsonArray.getJSONObject(0);
            addEmpDTO.setQrEmpId(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getQrEmpId())));
            addEmpDTO.setQrEmpName(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getQrEmpName())));
            addEmpDTO.setQrEmpMobileNumber(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getQrEmpMobileNumber())));
            addEmpDTO.setQrEmpAddress(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getQrEmpAddress())));
            addEmpDTO.setQrEmpLoginId(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getQrEmpLoginId())));
            addEmpDTO.setQrEmpPassword(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getQrEmpPassword())));
            addEmpDTO.setImoNo(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getImoNo())));
            addEmpDTO.setIsActive(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getIsActive())));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG, String.valueOf(mJsonArray));

        /*addEmpDTO.setQrEmpName(Objects.requireNonNull(addEmpBody.getValue()).getQrEmpName().trim());
        addEmpDTO.setQrEmpMobileNumber(Objects.requireNonNull(addEmpBody.getValue()).getQrEmpMobileNumber().trim());
        addEmpDTO.setQrEmpAddress(Objects.requireNonNull(addEmpBody.getValue().getQrEmpAddress().trim()));
        addEmpDTO.setQrEmpLoginId(Objects.requireNonNull(addEmpBody.getValue().getQrEmpLoginId().trim()));
        addEmpDTO.setQrEmpPassword(Objects.requireNonNull(addEmpBody.getValue().getQrEmpPassword().trim()));
        addEmpDTO.setImoNo(Objects.requireNonNull(addEmpBody.getValue().getImoNo().trim()));*/
        String appId = MainUtils.PREFS.APP_ID;


        Call<AddEmpResult> initiateAddEmp = empWebService.addNewEmpHS(MainUtils.CONTENT_TYPE,appId,addEmpDTO);

        initiateAddEmp.enqueue(new Callback<AddEmpResult>() {
            @Override
            public void onResponse(Call<AddEmpResult> call, Response<AddEmpResult> response) {
                if (response.code() == 200) {
                    iAddEmpResponse.onResponse(response.body());
                    Log.e(TAG, "onResponse: " + response.body().getMessage());
                } else if (response.code() == 500) {
                    iAddEmpResponse.onResponse(response.body());
                    Log.e(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<AddEmpResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iAddEmpResponse.onFailure(t);
            }
        });


    }



    public interface IAddEmpResponse {
        void onResponse(AddEmpResult addEmpResponse);

        void onFailure(Throwable t);
    }
}
