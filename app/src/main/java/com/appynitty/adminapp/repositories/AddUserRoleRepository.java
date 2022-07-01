package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.AddEmpDTO;
import com.appynitty.adminapp.models.AddEmpResult;
import com.appynitty.adminapp.models.AddUserRoleRightDTO;
import com.appynitty.adminapp.models.AddUserRoleRightResult;
import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.models.UserRoleModelDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.AddUserRoleRightWebService;
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserRoleRepository {
    private static final String TAG = "AddUserRoleRepository";
    String appId = Prefs.getString(MainUtils.APP_ID, null);
    private static final AddUserRoleRepository instance = new AddUserRoleRepository();
    Gson gson = new Gson();

    public static AddUserRoleRepository getInstance() {
        return instance;
    }
    List<AddUserRoleRightDTO> addUserRoleRightDTOS = new ArrayList<>();

    public void addUserRoleSave(MutableLiveData<AddUserRoleRightDTO> addUserRoleRightBody,IAddUserRoleRightsResponse iAddUserRoleRightsResponse){
        AddUserRoleRightWebService addUserRoleRightWebService = RetrofitClient.createService(AddUserRoleRightWebService.class,MainUtils.BASE_URL);
        addUserRoleRightDTOS.add(addUserRoleRightBody.getValue());

        Call<AddUserRoleRightResult> initiateAddUserRole = addUserRoleRightWebService.addNewUserRoleRightHS(MainUtils.CONTENT_TYPE,addUserRoleRightDTOS);

        initiateAddUserRole.enqueue(new Callback<AddUserRoleRightResult>() {
            @Override
            public void onResponse(Call<AddUserRoleRightResult> call, Response<AddUserRoleRightResult> response) {
                if (response.code() == 200) {
                    iAddUserRoleRightsResponse.onResponse(response.body());
                    Log.e(TAG, "onResponse: " + response.body().getMessage());
                } else if (response.code() == 500) {
                    iAddUserRoleRightsResponse.onResponse(response.body());
                    Log.e(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<AddUserRoleRightResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iAddUserRoleRightsResponse.onFailure(t);
            }
        });


    }

public void userRoleRightUpdate(UserRoleModelDTO userRoleDetails,IAddUserRoleRightsResponse iAddUserRoleRightsResponse){
    Log.d(TAG, "updateUserRoleDetails: " + userRoleDetails.toString());
    List<UserRoleModelDTO> userRoleDetails1 = new ArrayList<>();
    userRoleDetails1.add(userRoleDetails);
    AddUserRoleRightWebService addUserRoleRightWebService = RetrofitClient.createService(AddUserRoleRightWebService.class,MainUtils.BASE_URL);
    Call<AddUserRoleRightResult> initiateUpdateUserRole = addUserRoleRightWebService.updateUserRoleRightHS(MainUtils.CONTENT_TYPE,userRoleDetails1);

    initiateUpdateUserRole.enqueue(new Callback<AddUserRoleRightResult>() {
        @Override
        public void onResponse(Call<AddUserRoleRightResult> call, Response<AddUserRoleRightResult> response) {
            Log.d(TAG, "onResponse: " + response.body().getMessage());
            iAddUserRoleRightsResponse.onResponse(response.body());
        }

        @Override
        public void onFailure(Call<AddUserRoleRightResult> call, Throwable t) {
            Log.d(TAG, "onFailure: " + t.getMessage());
        }
    });
}


    public interface IAddUserRoleRightsResponse {
        void onResponse(AddUserRoleRightResult addUserRoleRightResponse);

        void onFailure(Throwable t);
    }
}
