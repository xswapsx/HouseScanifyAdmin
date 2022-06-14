package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.models.AddEmpDTO;
import com.appynitty.adminapp.models.AddEmpResult;
import com.appynitty.adminapp.models.LoginResult;
import com.appynitty.adminapp.models.LoginUserDTO;
import com.appynitty.adminapp.repositories.AddEmpRepository;
import com.appynitty.adminapp.repositories.LoginRepository;

public class AddEmpViewModel extends ViewModel {
    private static final String TAG = "AddEmpViewModel";

    public MutableLiveData<String> qrEmpName = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> qrEmpMobileNumber = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> qrEmpAddress = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> qrEmpLoginId = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> qrEmpPassword = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> imoNo = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> addAndUpdateEmp = new MutableLiveData<>(); //liveData with dataBinding

    public MutableLiveData<AddEmpDTO> addEmpMutableLiveData;
    public MutableLiveData<AddEmpResult> addEmpResultMutableData = new MutableLiveData<>();
    public AddEmpRepository addEmpRepository = new AddEmpRepository();
    private Boolean cbClearLogin = false;
    private Boolean cbIsActive = false;


    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_btn_save:
                addAndUpdateEmp.setValue("Data saved successfully!");
                break;
            case R.id.cb_clear_login:
                cbClearLogin = ((CheckBox) view).isChecked();
                Log.e(TAG, "checked: " + cbClearLogin);
                break;
            case R.id.cb_isActive:
                cbIsActive = ((CheckBox) view).isChecked();
                Log.e(TAG, "checked: " + cbIsActive);
                break;
            default:
                // code block
        }


    }

}
