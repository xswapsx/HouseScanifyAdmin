package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.UserLocationDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.UserLocationWebservice;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendLocationRepo {
    private static final String TAG = "SendLocationRepo";
    private static final SendLocationRepo instance = new SendLocationRepo();
    MutableLiveData<List<UserLocationDTO>> userLocationResponse = new MutableLiveData<>();


    public static SendLocationRepo getInstance() {
        return instance;
    }

    public void send10MinLocation(ILocationResponse iLocationResponse) {
        UserLocationDTO locationDTO = new UserLocationDTO(Prefs.getString(MainUtils.LONG), "0", MainUtils.getLocalDate(), "",
                true, Prefs.getString(MainUtils.LAT), Prefs.getString(MainUtils.EMP_ID));
        List<UserLocationDTO> locationDTOList = new ArrayList<>();
        locationDTOList.add(locationDTO);
        UserLocationWebservice service = RetrofitClient.createService(UserLocationWebservice.class, MainUtils.BASE_URL);
        Call<List<UserLocationDTO>> serviceCall = service.sendLocation(Prefs.getString(MainUtils.APP_ID),
                "0", String.valueOf(MainUtils.getBatteryStatus()), MainUtils.CONTENT_TYPE, Prefs.getString(MainUtils.EMP_TYPE), locationDTOList);
        serviceCall.enqueue(new Callback<List<UserLocationDTO>>() {
            @Override
            public void onResponse(Call<List<UserLocationDTO>> call, Response<List<UserLocationDTO>> response) {
                iLocationResponse.onResponse(response.body());
                Log.e(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<List<UserLocationDTO>> call, Throwable t) {
                iLocationResponse.onFailure(t);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public interface ILocationResponse {
        void onResponse(List<UserLocationDTO> locationResponse);

        void onFailure(Throwable t);
    }
}
