package com.appynitty.adminapp.models;

import android.util.Patterns;

public class LoginUser {
    private String UserLoginId;
    private String UserPassword;

    public LoginUser(String EmailAddress, String Password) {
        UserLoginId = EmailAddress;
        UserPassword = Password;
    }

    public String getUserLoginId() {
        return UserLoginId;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getUserLoginId()).matches();
    }


    public boolean isPasswordLengthGreaterThan5() {
        return getUserPassword().length() > 5;
    }
}
