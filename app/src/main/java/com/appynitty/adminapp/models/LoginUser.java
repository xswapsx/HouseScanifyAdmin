package com.appynitty.adminapp.models;

public class LoginUser {
    private String UserLoginId;
    private String UserPassword;

    public LoginUser(String EmailAddress, String Password) {
        UserLoginId = EmailAddress;
        UserPassword = Password;
    }

    public LoginUser() {

    }

    public void setUserLoginId(String userLoginId) {
        UserLoginId = userLoginId;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserLoginId() {
        return UserLoginId;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public boolean isUserIdValid() {
        return getUserLoginId().length() > 3;
    }


    public boolean isPasswordLengthGreaterThan5() {
        return getUserPassword().length() > 3;
    }
}
