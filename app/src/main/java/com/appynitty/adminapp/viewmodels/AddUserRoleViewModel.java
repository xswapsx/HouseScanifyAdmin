package com.appynitty.adminapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.AddEmpDTO;
import com.appynitty.adminapp.models.AddEmpResult;
import com.appynitty.adminapp.models.AddUserRoleRightDTO;
import com.appynitty.adminapp.models.AddUserRoleRightResult;
import com.appynitty.adminapp.models.DashboardDTO;
import com.appynitty.adminapp.models.UserRoleModelDTO;
import com.appynitty.adminapp.repositories.AddEmpRepository;
import com.appynitty.adminapp.repositories.AddUserRoleRepository;
import com.appynitty.adminapp.repositories.DashboardRepository;
import com.appynitty.adminapp.utils.MainUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

public class AddUserRoleViewModel extends ViewModel {
    private static final String TAG = "AddUserRoleViewModel";
    String appId = Prefs.getString(MainUtils.APP_ID, null);
    public MutableLiveData<String> EmpId = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> EmpName = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> LoginId = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> Password = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> EmpMobileNumber = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> EmpAddress = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> type = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> isActive = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> isActiveULB = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<List<DashboardDTO>> dashboardResponseLiveData;
    public DashboardRepository dashboardRepository = DashboardRepository.getInstance();
    private Boolean status = false;

    public MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();

    public MutableLiveData<AddUserRoleRightDTO> addUserRoleRightsMutableLiveData;
    public MutableLiveData<AddUserRoleRightResult> addUserRoleRightsResultMutableData = new MutableLiveData<>();
    public AddUserRoleRepository addUserRoleRepository = new AddUserRoleRepository();
    private Boolean cbSelectAll = false;
    private Boolean cbIsActive = false;
    private boolean isValid;

    public MutableLiveData<AddUserRoleRightDTO> getAddUserRoleMutableLiveData() {
        if (addUserRoleRightsMutableLiveData == null) {
            addUserRoleRightsMutableLiveData = new MutableLiveData<>();
        }
        return addUserRoleRightsMutableLiveData;
    }






    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }

    public LiveData<AddUserRoleRightResult> postAddUserRoleResponse() {
        return addUserRoleRightsResultMutableData;
    }


    public void updateUserRoleDetails(UserRoleModelDTO userRoleDetails){
        Log.d(TAG, "updateUserRoleDetails: " + userRoleDetails.toString());
        addUserRoleRepository.userRoleRightUpdate(userRoleDetails, new AddUserRoleRepository.IAddUserRoleRightsResponse() {
            @Override
            public void onResponse(AddUserRoleRightResult addUserRoleRightResponse) {
                Log.e(TAG, "onResponse: " + addUserRoleRightResponse.getMessage());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
