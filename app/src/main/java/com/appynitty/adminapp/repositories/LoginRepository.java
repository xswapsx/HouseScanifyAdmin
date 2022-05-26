package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.LoginResult;
import com.appynitty.adminapp.models.LoginUser;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.LoginWebService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private static final String TAG = "LoginRepository";
    private static LoginRepository instance;
    LoginUser loginUser = new LoginUser();

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public void loginRemote(MutableLiveData<LoginUser> loginBody, ILoginResponse iLoginResponse) {
        LoginWebService loginService = RetrofitClient.createService(LoginWebService.class, MainUtils.BASE_URL);
        loginUser.setUserLoginId(Objects.requireNonNull(loginBody.getValue()).getUserLoginId());
        loginUser.setUserPassword(loginBody.getValue().getUserPassword());
        Call<LoginResult> initiateLogin = loginService.saveLoginDetails(MainUtils.CONTENT_TYPE, MainUtils.EMP_TYPE_ADMIN, loginUser);


        initiateLogin.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(@NonNull Call<LoginResult> call, @NonNull Response<LoginResult> response) {
                assert response.body() != null;
                iLoginResponse.onResponse(response.body());
                Log.e(TAG, "onResponse: " + response.body().getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<LoginResult> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iLoginResponse.onFailure(t);
            }


        });

    }

    public interface ILoginResponse {
        void onResponse(LoginResult loginResponse);

        void onFailure(Throwable t);
    }
}
