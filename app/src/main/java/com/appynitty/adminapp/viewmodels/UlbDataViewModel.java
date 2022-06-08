package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.EmployeeDetailsDTO;
import com.appynitty.adminapp.models.SpecificUlbDTO;
import com.appynitty.adminapp.repositories.EmployeeDetailsRepository;
import com.appynitty.adminapp.repositories.UlbDataRepository;

import java.util.List;

public class UlbDataViewModel extends ViewModel {
    private static final String TAG = "UlbDataViewModel";
    public UlbDataRepository ulbDataRepository = UlbDataRepository.getInstance();
    public EmployeeDetailsRepository employeeDetailsRepository = EmployeeDetailsRepository.getInstance();
    public MutableLiveData<SpecificUlbDTO> specificUlbMutableLiveData;
    public MutableLiveData<List<EmployeeDetailsDTO>> empDetailsListLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    public MutableLiveData<Integer> mProgressMutableData1 = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalHouseLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalHouseUpdatedLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalHouseUpdated_CurrentDayLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalPointLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalPointUpdatedLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalPointUpdated_CurrentDayLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalDumpLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalDumpUpdatedLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalDumpUpdated_CurrentDayLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalLiquidLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalLiquidUpdatedLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalLiquidUpdated_CurrentDayLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalStreetLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalStreetUpdatedLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> TotalStreetUpdated_CurrentDayLiveData = new MutableLiveData<>();


    public UlbDataViewModel() {

    }

    public UlbDataViewModel(Object[] mParams) {
        String appId = (String) mParams[0];
        mProgressMutableData.setValue(View.VISIBLE);
        mProgressMutableData1.setValue(View.VISIBLE);
        ulbDataRepository.getUlbDataInfo(appId, new UlbDataRepository.IUlbDataResponse() {
            @Override
            public void onResponse(MutableLiveData<SpecificUlbDTO> specificUlbDataResponse) {
                mProgressMutableData.setValue(View.INVISIBLE);
                specificUlbMutableLiveData.setValue(specificUlbDataResponse.getValue());

                TotalHouseLiveData.setValue(specificUlbDataResponse.getValue().getTotalHouse());
                TotalHouseUpdatedLiveData.setValue(specificUlbDataResponse.getValue().getTotalHouseUpdated());
                TotalHouseUpdated_CurrentDayLiveData.setValue(specificUlbDataResponse.getValue().getTotalHouseUpdated_CurrentDay());

                TotalLiquidLiveData.setValue(specificUlbDataResponse.getValue().getTotalLiquid());
                TotalLiquidUpdatedLiveData.setValue(specificUlbDataResponse.getValue().getTotalLiquidUpdated());
                TotalLiquidUpdated_CurrentDayLiveData.setValue(specificUlbDataResponse.getValue().getTotalLiquidUpdated_CurrentDay());

                TotalStreetLiveData.setValue(specificUlbDataResponse.getValue().getTotalStreet());
                TotalStreetUpdatedLiveData.setValue(specificUlbDataResponse.getValue().getTotalStreetUpdated());
                TotalStreetUpdated_CurrentDayLiveData.setValue(specificUlbDataResponse.getValue().getTotalStreetUpdated_CurrentDay());

                TotalDumpLiveData.setValue(specificUlbDataResponse.getValue().getTotalDump());
                TotalDumpUpdatedLiveData.setValue(specificUlbDataResponse.getValue().getTotalDumpUpdated());
                TotalDumpUpdated_CurrentDayLiveData.setValue(specificUlbDataResponse.getValue().getTotalDumpUpdated_CurrentDay());
                Log.e(TAG, "onResponse: " + specificUlbDataResponse.getValue().getTotalHouse());
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.setValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        employeeDetailsRepository.getEmpDetailsList(appId, new EmployeeDetailsRepository.IEmpDetailsListener() {
            @Override
            public void onResponse(MutableLiveData<List<EmployeeDetailsDTO>> empDetailsResponse) {
                mProgressMutableData1.setValue(View.INVISIBLE);
                Log.e(TAG, "onResponse: EmpDetails= " + empDetailsResponse.getValue().toString());
                empDetailsListLiveData.setValue(empDetailsResponse.getValue());
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData1.setValue(View.INVISIBLE);
                Log.e(TAG, "onResponse: EmpDetails= " + t.getMessage());
            }
        });

        if (mParams.length > 1) {
            Log.e(TAG, "Check lenght : " + mParams.length);
        }

    }


    public MutableLiveData<SpecificUlbDTO> getSpecificUlbMutableLiveData() {
        if (specificUlbMutableLiveData == null) {
            specificUlbMutableLiveData = new MutableLiveData<>();
        }
        return specificUlbMutableLiveData;
    }

    public MutableLiveData<List<EmployeeDetailsDTO>> getEmpDetailsListLiveData() {
        if (empDetailsListLiveData == null) {
            empDetailsListLiveData = new MutableLiveData<>();
        }
        return empDetailsListLiveData;
    }
}
