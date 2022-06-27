package com.appynitty.adminapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.appynitty.adminapp.adapters.EmpListRecyclerAdapter;
import com.appynitty.adminapp.databinding.FragmentEmpListBinding;
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
    private List<EmployeeDetailsDTO> employeeDetailsList;
    public EmployeeDetailsRepository employeeDetailsRepository;
    View view;

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
        String appId = Prefs.getString(MainUtils.APP_ID);
        binding.progressBar.setVisibility(View.VISIBLE);
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

    private void setRecyclerView(List<EmployeeDetailsDTO> employeeDetailsList) {
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new EmpListRecyclerAdapter(getActivity(), employeeDetailsList);
        binding.empRecyclerView.setLayoutManager(layoutManager);
        binding.empRecyclerView.setAdapter(adapter);
    }
}