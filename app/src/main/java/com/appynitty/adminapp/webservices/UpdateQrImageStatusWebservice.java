package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.QrImageStatusDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UpdateQrImageStatusWebservice {
    @POST("api/Supervisor/UpdateQRstatus")
    Call<List<QrImageStatusDTO>> setQrImageStatus(@Header("Content-Type") String content_type,
                                                  @Header("appId") String appId,
                                                  @Body List<QrImageStatusDTO> qrImageRequestBody);
}
