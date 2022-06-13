package com.appynitty.adminapp.repositories;


import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.AddEmpDTO;
import com.appynitty.adminapp.models.AddEmpResult;
import com.appynitty.adminapp.models.LoginResult;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.AddEmpWebService;

import java.util.Objects;

import retrofit2.Call;

public class AddEmpRepository {
    private static final String TAG = "AddEmpRepository";
    AddEmpDTO addEmpDTO = new AddEmpDTO();

    public void addEmpRemote(MutableLiveData<AddEmpDTO> addEmpBody, IAddEmpResponse iAddEmpResponse){
        AddEmpWebService empWebService = RetrofitClient.createService(AddEmpWebService.class, MainUtils.BASE_URL);
        addEmpDTO.setQrEmpName(Objects.requireNonNull(addEmpBody.getValue()).getQrEmpName().trim());
        addEmpDTO.setQrEmpMobileNumber(Objects.requireNonNull(addEmpBody.getValue()).getQrEmpMobileNumber().trim());
        addEmpDTO.setQrEmpAddress(Objects.requireNonNull(addEmpBody.getValue().getQrEmpAddress().trim()));
        addEmpDTO.setQrEmpLoginId(Objects.requireNonNull(addEmpBody.getValue().getQrEmpLoginId().trim()));
        addEmpDTO.setQrEmpPassword(Objects.requireNonNull(addEmpBody.getValue().getQrEmpPassword().trim()));
        addEmpDTO.setImoNo(Objects.requireNonNull(addEmpBody.getValue().getImoNo().trim()));
        String appId = MainUtils.PREFS.APP_ID;

        Call<AddEmpResult> initiateAddEmp = empWebService.addNewEmpHS(MainUtils.CONTENT_TYPE,appId,addEmpDTO);


    }



    public interface IAddEmpResponse {
        void onResponse(AddEmpResult addEmpResponse);

        void onFailure(Throwable t);
    }
}
