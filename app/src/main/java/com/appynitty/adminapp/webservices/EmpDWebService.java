package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.EmployeeDetailsDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface EmpDWebService {
    @GET("api/Supervisor/QREmployeeDetailsList")
    Call<List<EmployeeDetailsDTO>> getEmpDetailsList(@Header("Content-Type") String contentType,
                                                          @Header("EmpType") String emp_type,
                                                          @Header("userId") Integer userId,
                                                          @Header("appId") String appId,
                                                          @Header("qrEmpId") Integer qrEmpId);
}
