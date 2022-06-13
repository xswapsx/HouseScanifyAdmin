package com.appynitty.adminapp.webservices;


import com.appynitty.adminapp.models.AddEmpDTO;
import com.appynitty.adminapp.models.AddEmpResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AddEmpWebService {
    @POST("api/Supervisor/AddHouseScanifyEmployee")
    Call<AddEmpResult> addNewEmpHS(@Header("Content-Type") String content_type,
                                   @Header("appId") String appId,
                                   @Body AddEmpDTO addEmpDTO);

}
