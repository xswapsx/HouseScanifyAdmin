package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.AttendanceDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.AttendanceWebService;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceRepository {
    private static final String TAG = "AttendanceRepository";
    private static final AttendanceRepository instance = new AttendanceRepository();
    private MutableLiveData<List<AttendanceDTO>> attendanceListLiveData = new MutableLiveData<>();

    public static AttendanceRepository getInstance(){
        return instance;
    }

    public void getListOfAttendance (IAttendanceResponse iAttendanceResponse){
        Log.e(TAG, "getListOfAttendance: ");
        String empType = Prefs.getString(MainUtils.EMP_TYPE);
        String userId = Prefs.getString(MainUtils.USER_ID);
        String fromDate = Prefs.getString(MainUtils.FROM_DATE);
        String toDate = Prefs.getString(MainUtils.TO_DATE);
        int appId = Prefs.getInt(String.valueOf(MainUtils.APP_ID));

        AttendanceWebService attendanceWebService = RetrofitClient.createService(AttendanceWebService.class, MainUtils.BASE_LIVE_URL);
        Call<List<AttendanceDTO>> attendanceDTOCall = attendanceWebService.getFragAttendanceList(MainUtils.CONTENT_TYPE, empType,
               userId, fromDate, toDate,appId);
        attendanceDTOCall.enqueue(new Callback<List<AttendanceDTO>>() {
            @Override
            public void onResponse(Call<List<AttendanceDTO>> call, Response<List<AttendanceDTO>> response) {

                if (response.code() == 200){
                    attendanceListLiveData.setValue(response.body());
                    iAttendanceResponse.onResponse(attendanceListLiveData);
                    Log.e(TAG, "onResponse: " + response.body());

                } else if (response.code() == 500){
                    Log.e(TAG, "onResponse: " + response.body());
                }

            }

            @Override
            public void onFailure(Call<List<AttendanceDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iAttendanceResponse.onFailure(t);
            }
        });
    }

    public interface IAttendanceResponse{
        void onResponse(MutableLiveData<List<AttendanceDTO>> attendanceResponse);

        void onFailure(Throwable t);
    }
}
