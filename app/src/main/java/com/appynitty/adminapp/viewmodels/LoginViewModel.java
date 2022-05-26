package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.LoginResult;
import com.appynitty.adminapp.models.LoginUser;
import com.appynitty.adminapp.repositories.LoginRepository;

public class LoginViewModel extends ViewModel {
    private static final String TAG = "LoginViewModel";

    public MutableLiveData<String> UserLoginId = new MutableLiveData<>();
    public MutableLiveData<String> UserPassword = new MutableLiveData<>();
    public MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    public MutableLiveData<LoginUser> userMutableLiveData;
    public MutableLiveData<LoginResult> loginResultMutableData = new MutableLiveData<>();
    public LoginRepository loginRepository = new LoginRepository();

    public MutableLiveData<LoginUser> getUserMutableLiveData() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public void onClick(View view) {


        LoginUser loginUser = new LoginUser(UserLoginId.getValue(), UserPassword.getValue());
        userMutableLiveData.setValue(loginUser);

        if (UserLoginId.getValue() != null && UserPassword.getValue() != null) {
            mProgressMutableData.postValue(View.VISIBLE);
            loginRepository.loginRemote(userMutableLiveData, new LoginRepository.ILoginResponse() {
                @Override
                public void onResponse(LoginResult loginResponse) {
                    mProgressMutableData.postValue(View.INVISIBLE);

                    loginResultMutableData.setValue(loginResponse);
                    Log.e(TAG, "onResponse: " + loginResponse.getMessage());
                }

                @Override
                public void onFailure(Throwable t) {
                    mProgressMutableData.postValue(View.INVISIBLE);
//                    loginResultMutableData.setValue(t.getMessage());
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });
        }

    }

    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }

    public LiveData<LoginResult> getLoginResponse() {
        return loginResultMutableData;
    }
}
