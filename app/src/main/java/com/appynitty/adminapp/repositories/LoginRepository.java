package com.appynitty.adminapp.repositories;

import com.appynitty.adminapp.models.LoginResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class LoginRepository {
    private static LoginRepository instance;



    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }


}
