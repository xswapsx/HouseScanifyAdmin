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
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddEmpRepository {
    private static final String TAG = "AddEmpRepository";
    String appId = Prefs.getString(MainUtils.APP_ID, null);
    private static final AddEmpRepository instance = new AddEmpRepository();
    Gson gson = new Gson();
    public static AddEmpRepository getInstance(){
        return instance;
    }

    AddEmpDTO addEmpDTO = new AddEmpDTO();

    public void addEmpRemote(MutableLiveData<AddEmpDTO> addEmpBody, IAddEmpResponse iAddEmpResponse){
        AddEmpWebService empWebService = RetrofitClient.createService(AddEmpWebService.class, MainUtils.BASE_URL);
        Call<AddEmpResult> initiateAddEmp = empWebService.addNewEmpHS(MainUtils.CONTENT_TYPE,appId,addEmpDTO);

        /*JSONArray jsonArray = new JSONArray();
        try {
            JSONObject jsonObject = jsonArray.toJSONObject(jsonArray);
            jsonObject.put("qrEmpId", addEmpBody.getValue().getQrEmpId().trim());
            jsonObject.put("qrEmpName", addEmpBody.getValue().getQrEmpName().trim());
            jsonObject.put("qrEmpMobileNumber", addEmpBody.getValue().getQrEmpMobileNumber().trim());
            jsonObject.put("qrEmpAddress", addEmpBody.getValue().getQrEmpAddress().trim());
            jsonObject.put("qrEmpLoginId", addEmpBody.getValue().getQrEmpLoginId().trim());
            jsonObject.put("qrEmpPassword", addEmpBody.getValue().getQrEmpPassword().trim());
            jsonObject.put("imoNo", addEmpBody.getValue().getImoNo().trim());
            jsonObject.put("isActive", addEmpBody.getValue().getIsActive().trim());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG, String.valueOf(jsonArray));*/

        JSONArray mJsonArray = new JSONArray();

        try {
            JSONObject mJsonObject = mJsonArray.getJSONObject(0);
            addEmpDTO.setQrEmpId(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getQrEmpId().trim())));
            addEmpDTO.setQrEmpName(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getQrEmpName().trim())));
            addEmpDTO.setQrEmpMobileNumber(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getQrEmpMobileNumber().trim())));
            addEmpDTO.setQrEmpAddress(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getQrEmpAddress().trim())));
            addEmpDTO.setQrEmpLoginId(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getQrEmpLoginId().trim())));
            addEmpDTO.setQrEmpPassword(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getQrEmpPassword().trim())));
            addEmpDTO.setImoNo(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getImoNo().trim())));
            addEmpDTO.setIsActive(Objects.requireNonNull(mJsonObject.getString(addEmpBody.getValue().getIsActive().trim())));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG, String.valueOf(mJsonArray));

        /*addEmpDTO.setQrEmpId(Objects.requireNonNull(addEmpBody.getValue().getQrEmpId().trim()));
        addEmpDTO.setQrEmpName(Objects.requireNonNull(addEmpBody.getValue().getQrEmpName().trim()));
        addEmpDTO.setQrEmpMobileNumber(Objects.requireNonNull(addEmpBody.getValue().getQrEmpMobileNumber().trim()));
        addEmpDTO.setQrEmpAddress(Objects.requireNonNull(addEmpBody.getValue().getQrEmpAddress().trim()));
        addEmpDTO.setQrEmpLoginId(Objects.requireNonNull(addEmpBody.getValue().getQrEmpLoginId().trim()));
        addEmpDTO.setQrEmpPassword(Objects.requireNonNull(addEmpBody.getValue().getQrEmpPassword().trim()));
        addEmpDTO.setImoNo(Objects.requireNonNull(addEmpBody.getValue().getImoNo().trim()));
        addEmpDTO.setIsActive(Objects.requireNonNull(addEmpBody.getValue().getIsActive().trim()));*/




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

   /* Retrofit retrofit =  new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MainUtils.BASE_URL)
            .build();

    AddEmpWebService empWebService = retrofit.create(AddEmpWebService.class);
    Call<AddEmpResult> call;

    {
        try {
            call = (Call<AddEmpResult>) empWebService.addNewEmpHS(MainUtils.CONTENT_TYPE, appId,addEmpDTO).execute().body();
            call.enqueue(new Callback<AddEmpResult>() {
                @Override
                public void onResponse(Call<AddEmpResult> call, Response<AddEmpResult> response) {
                    if (response.code() == 200) {

                        Log.e(TAG, "onResponse: " + response.body().getMessage());
                    } else if (response.code() == 500) {

                        Log.e(TAG, "onResponse: " + response.body());
                    }
                }

                @Override
                public void onFailure(Call<AddEmpResult> call, Throwable t) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    public interface IAddEmpResponse {
        void onResponse(AddEmpResult addEmpResponse);

        void onFailure(Throwable t);
    }
}
