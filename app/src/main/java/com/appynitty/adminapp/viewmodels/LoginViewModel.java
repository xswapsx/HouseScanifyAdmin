package com.appynitty.adminapp.viewmodels;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.LoginUser;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    public MutableLiveData<LoginUser> userMutableLiveData;

    public MutableLiveData<LoginUser> getUserMutableLiveData() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public void onClick(View view) {
        LoginUser loginUser = new LoginUser(EmailAddress.getValue(), Password.getValue());
        userMutableLiveData.setValue(loginUser);
    }
}
