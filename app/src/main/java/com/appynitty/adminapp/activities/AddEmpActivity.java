package com.appynitty.adminapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.ActivityAddEmpBinding;
import com.appynitty.adminapp.models.AddEmpDTO;
import com.appynitty.adminapp.models.AddEmpResult;
import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.viewmodels.AddEmpViewModel;
import com.appynitty.adminapp.viewmodels.LoginViewModel;
import com.pixplicity.easyprefs.library.Prefs;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AddEmpActivity extends AppCompatActivity {
    String TAG = "AddEmpActivity";
    String reqStatus = "";
    private Context context;
    private ActivityAddEmpBinding binding;
    private View view;
    private Toolbar toolbar;
    private List<EmpDModelDTO> empDModelDTOList;
    private AddEmpDTO addEmpModelDto;
    private AddEmpViewModel addEmpViewModel;
    String empName, empMobile, empAdd, empUsername, empPass, empLoginImei, empId;
    String empIdAdapter, empNameAdapter;
    boolean isActiveChecked, isClearChecked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmpBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        addEmpViewModel = ViewModelProviders.of(this).get(AddEmpViewModel.class);
        binding.setAddEmpViewModel(addEmpViewModel);

        //custom toolbar added
        binding.rlCustomToolbar.txtTitle.setText("Employee Details");
        binding.rlCustomToolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        empIdAdapter = intent.getStringExtra("qrEmpId");
        empNameAdapter = intent.getStringExtra("qrEmpName");
        Log.e(TAG, "adapter send emp id : " +empIdAdapter +""+empNameAdapter);

        init();

    }

    private void init(){
        context = this;
        /*empDModelDTOList = new ArrayList<>();*/
        addEmpModelDto = new AddEmpDTO();
        TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e(TAG, "Device id: " + androidID);
        binding.edtEmpLoginNum.setText(androidID);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("imiNo",androidID);
        editor.apply();

        addEmpViewModel.getAddEmpMutableLiveData().observe(this, new Observer<AddEmpDTO>() {
            @Override
            public void onChanged(AddEmpDTO addEmpDTO) {
                addEmpDTO.setQrEmpId("");
                addEmpDTO.setImoNo(androidID);
                /*if (TextUtils.isEmpty(Objects.requireNonNull(addEmpDTO).getQrEmpName())) {
                    binding.edtEmpName.setError("Please enter employee name!");
                    binding.edtEmpName.requestFocus();
                }else if (TextUtils.isEmpty(Objects.requireNonNull(addEmpDTO).getQrEmpMobileNumber())) {
                    binding.edtEmpMobile.setError("Please enter employee mobile number!");
                    binding.edtEmpMobile.requestFocus();
                }*//*else if (!addEmpDTO.isEmpMobileValid()) {
                    binding.edtEmpMobile.setError("please enter valid mobile number");
                    binding.edtEmpMobile.requestFocus();
                }*//*else if (TextUtils.isEmpty(Objects.requireNonNull(addEmpDTO).getQrEmpAddress())) {
                    binding.edtEmpAddress.setError("Please enter employee address!");
                    binding.edtEmpAddress.requestFocus();
                }else if (TextUtils.isEmpty(Objects.requireNonNull(addEmpDTO).getQrEmpLoginId())) {
                    binding.edtEmpUsername.setError("Please enter employee username!");
                    binding.edtEmpUsername.requestFocus();
                }else if (!addEmpDTO.isEmpUsernameValid()) {
                    binding.edtEmpUsername.setError("Username must contain at least 4 digits");
                    binding.edtEmpUsername.requestFocus();
                }else if (TextUtils.isEmpty(Objects.requireNonNull(addEmpDTO).getQrEmpPassword())) {
                    binding.edtEmpPassword.setError("Please enter employee password!");
                    binding.edtEmpPassword.requestFocus();
                }else if (!addEmpDTO.isEmpPassValid()) {
                    binding.edtEmpPassword.setError("Password must contain at least 4 digits");
                    binding.edtEmpPassword.requestFocus();
                }else if (TextUtils.isEmpty(Objects.requireNonNull(addEmpDTO).getImoNo())) {
                    binding.edtEmpLoginNum.setError("Your device id not found!");
                    binding.edtEmpLoginNum.requestFocus();*/
                if (binding.edtEmpName.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Please enter employee name", Toast.LENGTH_SHORT).show();

                }else if (binding.edtEmpMobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Please enter employee mobile number", Toast.LENGTH_SHORT).show();
                }else if (binding.edtEmpMobile.getText().toString().length() <10){
                    Toast.makeText(context, "Please enter valid employee mobile number", Toast.LENGTH_SHORT).show();
                }else if (binding.edtEmpAddress.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Please enter employee address", Toast.LENGTH_SHORT).show();
                }else if (binding.edtEmpUsername.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Please enter employee username", Toast.LENGTH_SHORT).show();
                }else if (binding.edtEmpUsername.getText().toString().length() < 4){
                    Toast.makeText(context, "Username must contain at least 4 digits", Toast.LENGTH_SHORT).show();
                }else if (binding.edtEmpPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Please enter employee password", Toast.LENGTH_SHORT).show();
                }else if (binding.edtEmpPassword.getText().toString().length() < 4){
                    Toast.makeText(context, "Password must contain at least 4 digits", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e(TAG, "onChanged: qrEmpId: " + addEmpDTO.getQrEmpId() + " EmpName: " + addEmpDTO.getQrEmpName()
                            + " EmpMobile: " + addEmpDTO.getQrEmpMobileNumber() + " EmpAddress: " + addEmpDTO.getQrEmpAddress()
                            + " EmpUsername: " + addEmpDTO.getQrEmpLoginId() + " password: " + addEmpDTO.getQrEmpPassword()
                            + " empDeviceId: " + addEmpDTO.getImoNo() + " EmpIsActiveStatus: " + addEmpDTO.getIsActive()
                    );
                }
            }
        });

        addEmpViewModel.postAddEmpResponse().observe(this, new Observer<AddEmpResult>() {
            @Override
            public void onChanged(AddEmpResult addEmpResult) {
                if (addEmpResult != null && addEmpResult.getStatus() != null) {
                    Log.e(TAG, "onChanged: status: " + addEmpResult.getStatus());
                    reqStatus = addEmpResult.getStatus();
                    if (reqStatus.equals("success")) {
                        DynamicToast.makeSuccess(context, addEmpResult.getMessage()).show();
                        /*Prefs.putString(MainUtils.EMP_TYPE, loginResult.getEmpType());
                        Prefs.putString(MainUtils.USER_ID, loginResult.getUserId().toString());
                        Prefs.putBoolean(MainUtils.IS_LOGIN, true);*/
                        finish();
                    }else if (reqStatus.equals("error")){
                        DynamicToast.makeError(context, addEmpResult.getMessage()).show();
                    }
                }else {
                    DynamicToast.makeError(context, "an error has occurred").show();
                }
            }
        });


    }


    private void setOnClick() {
        if (empDModelDTOList != null){
            if (empId != null){
                binding.txtBtnSave.setText("Updated");
                binding.txtBtnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        Toast.makeText(context, "Employee data updated successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        if (empDModelDTOList.isEmpty()){
            binding.txtBtnSave.setText("Save");
            binding.txtBtnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    Toast.makeText(context, "Employee data saved successfully", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void setData() {

    }
}