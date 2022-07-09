package com.appynitty.adminapp.activities;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.UlbListAdapter;
import com.appynitty.adminapp.databinding.ActivityAddUserDetailsBinding;
import com.appynitty.adminapp.databinding.ActivityUpdateUserDetailsBinding;
import com.appynitty.adminapp.models.AddUserRoleRightDTO;
import com.appynitty.adminapp.models.AddUserRoleRightResult;
import com.appynitty.adminapp.models.DashboardDTO;
import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.models.UlbDTO;
import com.appynitty.adminapp.models.UserRoleModelDTO;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.viewmodels.AddEmpViewModel;
import com.appynitty.adminapp.viewmodels.AddUserRoleViewModel;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddUserDetailsActivity extends AppCompatActivity {
    String TAG = "AddUserDetailsActivity";
    String reqStatus = "", message = "";
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
    private List<UlbDTO> ulbList;
    private CheckBox cbSelectAll;

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
                    userRoleRightDetails.setActive(updateUserDetailsBinding.cbIsActive.isChecked());
                    addUserRoleViewModel.updateUserRoleDetails(userRoleRightDetails);
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

        addUserRoleViewModel.getAddUserResultMutableData().observe(this, new Observer<List<AddUserRoleRightResult>>() {
            @Override
            public void onChanged(List<AddUserRoleRightResult> addUserRoleRightResults) {
                Log.e(TAG, "onChanged: " + addUserRoleRightResults.get(0).getMessage());
                message = addUserRoleRightResults.get(0).getMessage();
                reqStatus = addUserRoleRightResults.get(0).getStatus();

                if (reqStatus.matches(MainUtils.STATUS_SUCCESS)) {
                    DynamicToast.makeSuccess(context, message).show();
                    finish();
                } else {
                    DynamicToast.makeWarning(context, message).show();
                }
            }
        });

    }

    private void init() {
        context = this;
        addUserRoleRightDTO = new AddUserRoleRightDTO();
        ulbList = new ArrayList<>();
        recyclerUlbList = findViewById(R.id.recycler_ulb_chkbox);
        loader = findViewById(R.id.progress_circular);
        txtNoData = findViewById(R.id.txt_no_data);
        edtSearchText = findViewById(R.id.edt_search_text);
        spinner = findViewById(R.id.spinner);
        cbSelectAll = findViewById(R.id.check_select_all);
        imgClear = findViewById(R.id.img_close);
        txtEntries = findViewById(R.id.txt_entries);
        layoutManager = new GridLayoutManager(context, 2);
        txtNoData.setVisibility(View.VISIBLE);
        imgClear.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
       // binding.spinner.setAdapter((SpinnerAdapter) userRoleList);

        binding.edtSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        addUserRoleViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                binding.progressCircular.setVisibility(visibility);
            }
        });

        addUserRoleViewModel.getDashboardResponse().observe(this, new Observer<List<DashboardDTO>>() {

            @Override
            public void onChanged(List<DashboardDTO> dashboardResults) {
                ulbList.clear();
                for (int i = 0; i < dashboardResults.size(); i++) {
                    ulbList.add(new UlbDTO(dashboardResults.get(i).getUlb(),
                            dashboardResults.get(i).getAppid()));
                }
                setOnRecycler(ulbList);
            }
        });


        addUserRoleViewModel.addUserRoleMutableLiveData().observe(this, new Observer<AddUserRoleRightDTO>() {
            @Override
            public void onChanged(AddUserRoleRightDTO addUserRoleRightDTO) {
                addUserRoleRightDTO.setEmpId("");
               // addUserRoleRightDTO.setIsActiveULB(adapter.toString());
                UlbDTO ulb = ulbList.get(ad)
                addUserRoleRightDTO.setIsActiveULB(ulbList.toString());

                if (binding.edtEmpName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter employee name", Toast.LENGTH_SHORT).show();

                } else if (binding.edtEmpMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter employee mobile number", Toast.LENGTH_SHORT).show();
                } else if (binding.edtEmpMobile.getText().toString().length() < 10) {
                    Toast.makeText(context, "Please enter valid employee mobile number", Toast.LENGTH_SHORT).show();
                } else if (binding.edtEmpAddress.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter employee address", Toast.LENGTH_SHORT).show();
                } else if (binding.edtEmpUsername.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter employee username", Toast.LENGTH_SHORT).show();
                } else if (binding.edtEmpUsername.getText().toString().length() < 4) {
                    Toast.makeText(context, "Username must contain at least 4 digits", Toast.LENGTH_SHORT).show();
                } else if (binding.edtEmpPassword.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter employee password", Toast.LENGTH_SHORT).show();
                } else if (binding.edtEmpPassword.getText().toString().length() < 4) {
                    Toast.makeText(context, "Password must contain at least 4 digits", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "onChanged value provide: EmpId: " + addUserRoleRightDTO.getEmpId() + " EmpName: " + addUserRoleRightDTO.getEmpName()
                            + " EmpMobile: " + addUserRoleRightDTO.getEmpMobileNumber() + " EmpAddress: " + addUserRoleRightDTO.getEmpAddress()
                            + " EmpUsername: " + addUserRoleRightDTO.getLoginId() + " password: " + addUserRoleRightDTO.getPassword()
                            + " EmpIsActiveStatus: " + addUserRoleRightDTO.getIsActive()
                            + "ActiveUlb: " +addUserRoleRightDTO.getIsActiveULB()
                    );
                }
                /*finish();
                Toast.makeText(context, "User role right data saved", Toast.LENGTH_SHORT).show();*/
            }
        });

        addUserRoleViewModel.postAddEmpResponse().observe(this, new Observer<List<AddUserRoleRightResult>>() {
            @Override
            public void onChanged(List<AddUserRoleRightResult> addUserResults) {
                if (addUserResults != null && addUserResults.get(0).getStatus() != null) {
                    Log.e(TAG, "onChanged: status: " + addUserResults.get(0).getStatus());
                    reqStatus = addUserResults.get(0).getStatus();
                    if (reqStatus.equals("success")) {
                        DynamicToast.makeSuccess(context, addUserResults.get(0).getMessage()).show();

                        finish();
                    } else if (reqStatus.equals("error")) {
                        DynamicToast.makeError(context, addUserResults.get(0).getMessage()).show();
                    }
                } else {
                    DynamicToast.makeError(context, "an error has occurred").show();
                }
            }
        });

        setOnClick();
    }

    private void setOnClick() {
        binding.txtBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.checkSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setAllChecked(binding.checkSelectAll.isChecked());
                adapter.notifyDataSetChanged();
                if (binding.checkSelectAll.isChecked()){
                    Log.e(TAG,"all ulb selected :" + binding.checkSelectAll.isChecked());
                }else {
                    Log.e(TAG,"all ulb not selected :" + binding.checkSelectAll.isChecked());
                }
            }
        });
    }

    private void setOnRecycler(List<UlbDTO> ulbList) {
        loader.setVisibility(View.GONE);
        txtNoData.setVisibility(View.GONE);
        adapter = new UlbListAdapter(context,ulbList);
        adapter.notifyDataSetChanged();
        binding.recyclerUlbChkbox.setLayoutManager(layoutManager);
        binding.recyclerUlbChkbox.setAdapter(adapter);

    }

    private void filter(String text) {
        List<UlbDTO> filteredList = new ArrayList<>();

        for (UlbDTO item : ulbList) {
            if (item.getUlbName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }
}