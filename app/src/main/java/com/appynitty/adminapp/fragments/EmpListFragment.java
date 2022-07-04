package com.appynitty.adminapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.DashboardActivity;
import com.appynitty.adminapp.adapters.EmpListRecyclerAdapter;
import com.appynitty.adminapp.databinding.FragmentEmpListBinding;
import com.appynitty.adminapp.dialog.FilterDialog;
import com.appynitty.adminapp.dialog.FilterDialogFragment;
import com.appynitty.adminapp.models.EmployeeDetailsDTO;
import com.appynitty.adminapp.repositories.EmployeeDetailsRepository;
import com.appynitty.adminapp.utils.MainUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmpListFragment extends Fragment {
    private static final String TAG = "EmpListFragment";
    private LinearLayoutManager layoutManager;
    private EmpListRecyclerAdapter adapter;
    FragmentEmpListBinding binding;
    String fromDate = "";
    private List<EmployeeDetailsDTO> employeeDetailsList;
    public EmployeeDetailsRepository employeeDetailsRepository;
    AppCompatActivity activity;
    String appId;
    ImageButton homeButton;
    View view;
    private FilterDialogFragment filterDialog;

    public EmpListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEmpListBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        init();
        return view;
    }

    private void init() {
        employeeDetailsRepository = EmployeeDetailsRepository.getInstance();
        employeeDetailsList = new ArrayList<>();
        appId = Prefs.getString(MainUtils.APP_ID);
        binding.progressBar.setVisibility(View.VISIBLE);
        filterDialog = new FilterDialogFragment("EmpListFragment");
        activity = (AppCompatActivity) view.getContext();
        homeButton = getActivity().findViewById(R.id.ib_home);
        homeButton.setImageResource(R.drawable.ic_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DashboardActivity.class);
                startActivity(i);
//                activity.finish();
            }
        });
        getEmpList();
        binding.filterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog.setFilterDialogListener(new FilterDialog.FilterDialogInterface() {
                    @Override
                    public void onFilterDialogDismiss(String frmDate, String toDate, String userId) {
                        Log.e(TAG, "onFilterDialogDismiss: fromDate: " + frmDate);

                        fromDate = frmDate;
                        binding.progressBar.setVisibility(View.VISIBLE);
                        employeeDetailsRepository.getFilteredEmpDetails(frmDate, frmDate, appId, String.valueOf(0), new EmployeeDetailsRepository.IEmpDetailsListener() {
                            @Override
                            public void onResponse(MutableLiveData<List<EmployeeDetailsDTO>> empDetailsResponse) {
                                employeeDetailsList.clear();
                                for (EmployeeDetailsDTO emp : Objects.requireNonNull(empDetailsResponse.getValue())) {
                                    Log.d(TAG, "onResponse: Name: " + emp.getEmpName());
                                    employeeDetailsList.add(emp);
                                }
                                binding.progressBar.setVisibility(View.GONE);
                                setRecyclerView(employeeDetailsList);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                binding.progressBar.setVisibility(View.GONE);
                                Log.e(TAG, "onFailure: " + t.getMessage());
                            }
                        });
                    }
                });
                filterDialog.show(getChildFragmentManager(), TAG);
            }
        });
    }

    private void setRecyclerView(List<EmployeeDetailsDTO> employeeDetailsList) {
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new EmpListRecyclerAdapter(getActivity(), employeeDetailsList, fromDate);
        binding.empRecyclerView.setLayoutManager(layoutManager);
        binding.empRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void getEmpList() {
        employeeDetailsRepository.getEmpDetailsList(appId, new EmployeeDetailsRepository.IEmpDetailsListener() {

            @Override
            public void onResponse(MutableLiveData<List<EmployeeDetailsDTO>> empDetailsResponse) {
                for (EmployeeDetailsDTO emp :
                        Objects.requireNonNull(empDetailsResponse.getValue())) {
                    Log.d(TAG, "onResponse: " + emp.getEmpName() + "\n");
                    employeeDetailsList.add(emp);
                }
                binding.progressBar.setVisibility(View.GONE);
                setRecyclerView(employeeDetailsList);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}