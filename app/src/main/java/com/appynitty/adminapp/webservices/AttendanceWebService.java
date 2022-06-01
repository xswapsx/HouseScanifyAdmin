package com.appynitty.adminapp.webservices;

import com.appynitty.adminapp.models.AttendanceDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AttendanceWebService {
    @GET("api/Supervisor/AttendanceGridRow")
    Call<List<AttendanceDTO>> getFragAttendanceList (@Header("Content-Type") String content_type,
                                                     @Header("EmpType") String emp_type,
                                                     @Header("userId") String user_id,
                                                     @Header("FromDate") String from_date,
                                                     @Header("Todate") String to_date,
                                                     @Header("appId") int appId);

}
