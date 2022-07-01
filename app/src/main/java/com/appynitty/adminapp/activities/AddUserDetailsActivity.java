package com.appynitty.adminapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.UlbListAdapter;
import com.appynitty.adminapp.databinding.ActivityAddUserDetailsBinding;
import com.appynitty.adminapp.databinding.ActivityUpdateUserDetailsBinding;
import com.appynitty.adminapp.models.AddUserRoleRightDTO;
import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.models.UserRoleModelDTO;
import com.appynitty.adminapp.viewmodels.AddEmpViewModel;
import com.appynitty.adminapp.viewmodels.AddUserRoleViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddUserDetailsActivity extends AppCompatActivity {

    private Context context;
    private LinearLayoutManager layoutManager;
    private ProgressBar loader;
    private TextView txtEntries, txtNoData;
    private ImageView imgClear;
    private EditText edtSearchText;
    private RecyclerView recyclerUlbList;
    private UlbListAdapter adapter;
    private ActivityAddUserDetailsBinding binding;
    private ActivityUpdateUserDetailsBinding updateUserDetailsBinding;
    private List<UserRoleModelDTO> userRoleModelDTOS;
    private UserRoleModelDTO userRoleRightDetails;
    private AddUserRoleRightDTO  addUserRoleRightDTO;
    private AddUserRoleViewModel addUserRoleViewModel;
    private View view;
    private Toolbar toolbar;
    private Spinner spinner;

    // Initializing a String Array
    String[] userRole = new String[]{
            "Select User Role",
            "Admin",
            "Sub Admin"
    };

    final List<String> userRoleList = new ArrayList<>(Arrays.asList(userRole));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserDetailsBinding.inflate(getLayoutInflater());
        updateUserDetailsBinding = ActivityUpdateUserDetailsBinding.inflate(getLayoutInflater());
        addUserRoleViewModel = ViewModelProviders.of(this).get(AddUserRoleViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra("userRoleRightsDetails")) {
            view = updateUserDetailsBinding.getRoot();
            setContentView(view);
            userRoleRightDetails = (UserRoleModelDTO) getIntent().getSerializableExtra("userRoleRightsDetails");
            updateUserDetailsBinding.setUpdateUserRoleDetails(userRoleRightDetails);
            binding.setLifecycleOwner(this);

            //custom toolbar added
            updateUserDetailsBinding.rlCustomToolbar.txtTitle.setText("Update User Right");
            updateUserDetailsBinding.rlCustomToolbar.imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            updateUserDetailsBinding.txtBtnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!updateUserDetailsBinding.cbIsActive.isChecked()) {
                        userRoleRightDetails.setActive(false);
                    }
                    addUserRoleViewModel.updateUserRoleDetails(userRoleRightDetails);
                }
            });

            updateUserDetailsBinding.cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                }
            });
        } else {
            view = binding.getRoot();
            setContentView(view);
            binding.setLifecycleOwner(this);
//            addEmpViewModel = ViewModelProviders.of(this).get(AddEmpViewModel.class);
            binding.setAddUserDetailsData(addUserRoleViewModel);

            //custom toolbar added
            binding.rlCustomToolbar.txtTitle.setText("Add User Role Right");
            binding.rlCustomToolbar.imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            init();
        }
    }

    private void init() {
        context = this;
        recyclerUlbList = findViewById(R.id.recycler_ulb_chkbox);
        loader = findViewById(R.id.progress_circular);
        txtNoData = findViewById(R.id.txt_no_data);
        edtSearchText = findViewById(R.id.edt_search_text);
        spinner = findViewById(R.id.spinner);
        imgClear = findViewById(R.id.img_close);
        txtEntries = findViewById(R.id.txt_entries);
        layoutManager = new GridLayoutManager(context, 2);
        txtNoData.setVisibility(View.VISIBLE);
        imgClear.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);

        setOnClick();
        setOnRecycler();
    }

    private void setOnClick() {
        binding.txtBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setOnRecycler() {
        loader.setVisibility(View.GONE);
        txtNoData.setVisibility(View.GONE);
        adapter = new UlbListAdapter(getBaseContext());
        recyclerUlbList.setLayoutManager(layoutManager);
        recyclerUlbList.setAdapter(adapter);

    }

}