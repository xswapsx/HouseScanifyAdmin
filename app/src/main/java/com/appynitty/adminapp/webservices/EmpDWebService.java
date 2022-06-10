package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.models.EmployeeDetailsDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface EmpDWebService {
    @GET("api/Supervisor/QREmployeeDetailsList")
    Call<List<EmpDModelDTO>> getEmpDList(@Header("Content-Type") String contentType,
                                               @Header("EmpType") String emp_type,
                                               @Header("userId") String userId,
                                               @Header("qrEmpId") String qrEmpId,
                                               @Header("appId") String appId);


}
