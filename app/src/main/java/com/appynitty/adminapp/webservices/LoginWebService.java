package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.LoginResult;
import com.appynitty.adminapp.models.LoginUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginWebService {
    @POST("api/Account/Login")
    Call<LoginResult> saveLoginDetails(@Header("appId") String appId,
                                       @Header("Content-Type") String content_type,
                                       @Header("EmpType") String emp_type,
                                       @Body LoginUser loginUser);
}
