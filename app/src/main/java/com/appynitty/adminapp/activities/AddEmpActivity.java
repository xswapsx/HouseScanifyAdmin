package com.appynitty.adminapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.appynitty.adminapp.R;


public class AddEmpActivity extends AppCompatActivity {
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emp);
        init();

    }

    private void init(){
        context = this;

    }
}