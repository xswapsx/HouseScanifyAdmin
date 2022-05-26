package com.appynitty.adminapp.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.ActivityLoginBinding;
import com.appynitty.adminapp.models.LoginUser;
import com.appynitty.adminapp.viewmodels.LoginViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    String TAG = "LoginActivity";
    private LoginViewModel loginViewModel;

    private ActivityLoginBinding binding;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(this);

        binding.setLoginViewModel(loginViewModel);
        loginViewModel.getUserMutableLiveData().observe(this, new Observer<LoginUser>() {
            @Override
            public void onChanged(LoginUser loginUser) {
                if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getUserLoginId())) {
                    binding.etUserName.setError("Enter a username!");
                    binding.etUserName.requestFocus();
                } else if (!loginUser.isUserIdValid()) {
                    binding.etUserName.setError("password must contain atleast 4 digits");
                    binding.etUserName.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser.getUserPassword()))) {
                    binding.etPassword.setError("Enter a username!");
                    binding.etPassword.requestFocus();
                } else if (!loginUser.isPasswordLengthGreaterThan5()) {
                    binding.etPassword.setError("password must contain atleast 4 digits");
                    binding.etPassword.requestFocus();
                } else {
                    Log.e(TAG, "onChanged: userName: " + loginUser.getUserLoginId() + " password: " + loginUser.getUserPassword());
                }
            }
        });
    }
}