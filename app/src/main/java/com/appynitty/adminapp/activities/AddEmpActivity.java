package com.appynitty.adminapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.ActivityAddEmpBinding;
import com.appynitty.adminapp.models.EmpDModelDTO;

import java.util.ArrayList;
import java.util.List;


public class AddEmpActivity extends AppCompatActivity {
    private Context context;
    private ActivityAddEmpBinding binding;
    private View view;
    private Toolbar toolbar;
    private List<EmpDModelDTO> empDModelDTOList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmpBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        //custom toolbar added
        binding.rlCustomToolbar.txtTitle.setText("Employee Details");
        binding.rlCustomToolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();

    }

    private void init(){
        context = this;
        empDModelDTOList = new ArrayList<>();
        setData();
        setOnClick();
    }

    private void setOnClick() {
        if (empDModelDTOList != null){
            binding.txtBtnSave.setText("Updated");
            binding.txtBtnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    Toast.makeText(context, "Employee data updated successfully", Toast.LENGTH_SHORT).show();
                }
            });
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
        /*Intent i = getIntent();
         empDModelDTOList = (List<EmpDModelDTO>) i.getSerializableExtra("empDModel");
        if (empDModelDTOList != null && empDModelDTOList.isEmpty()){
            for (EmpDModelDTO item : empDModelDTOList) {
                binding.edtEmpAddress.setText(item.getQrEmpAddress());
                binding.edtEmpName.setText(item.getQrEmpName());
                binding.edtEmpMobile.setText(item.getQrEmpMobileNumber());
                binding.edtEmpUsername.setText(item.getQrEmpLoginId());
                binding.edtEmpPassword.setText(item.getQrEmpPassword());
                binding.edtEmpLoginNum.setText(item.getImoNo());

            }
        }else {
            Toast.makeText(context, "employee detail model null", Toast.LENGTH_SHORT).show();
        }*/
    }
}