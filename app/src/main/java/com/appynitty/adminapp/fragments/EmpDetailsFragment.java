package com.appynitty.adminapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.AddEmpActivity;
import com.appynitty.adminapp.activities.HomeActivity;
import com.appynitty.adminapp.adapters.EmpDetailsAdapter;
import com.appynitty.adminapp.databinding.FragmentEmpDetailsBinding;
import com.appynitty.adminapp.models.AttendanceDTO;
import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.utils.MyApplication;
import com.appynitty.adminapp.utils.MyViewModelFactory;
import com.appynitty.adminapp.viewmodels.EmpDViewModel;
import com.appynitty.adminapp.viewmodels.UlbDataViewModel;

import java.util.ArrayList;
import java.util.List;


public class EmpDetailsFragment extends Fragment {
    private static final String TAG = "EmpDetailsFragment";
    private Context context;
    private View view;
    private LinearLayoutManager layoutManager;
    private ProgressBar loader;
    private TextView txtEntries, txtNoData;
    private ImageView imgClear;
    private EditText edtSearchText;
    private RecyclerView recyclerEmpDetails;
    private EmpDetailsAdapter adapter;
    private CardView crdAddEmp;

    //binding part
    private FragmentEmpDetailsBinding empDetailsBinding;
    HomeActivity activity;
    MyApplication application;
    private String appId, ulbName;
    Bundle filterExtras;
    List<EmpDModelDTO> empDModelList;
    UlbDataViewModel ulbDataViewModel;
    EmpDViewModel empDViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view== null){
            empDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_emp_details, container, false);
            //view =inflater.inflate(R.layout.fragment_emp_details, container, false);
            view = empDetailsBinding.getRoot();
            empDetailsBinding.setLifecycleOwner(this);
            init();
        }
        return view;
    }

    private void init(){
        activity = (HomeActivity) getActivity();
        Bundle results = activity.getUlbData();
        appId = results.getString("val1");
        ulbName = results.getString("val2");
        Log.e(TAG, "AppID: " + appId + " ULB: " + ulbName);
        context = getActivity();

        empDModelList = new ArrayList<>();
        recyclerEmpDetails = view.findViewById(R.id.recycler_emp_details_frag);
        loader = view.findViewById(R.id.progress_circular);
        txtNoData = view.findViewById(R.id.txt_no_data);
        edtSearchText = view.findViewById(R.id.edt_search_empD);
        imgClear = view.findViewById(R.id.img_close);
        crdAddEmp = view.findViewById(R.id.card_add_empD);
        txtEntries = view.findViewById(R.id.txt_entries);
        layoutManager = new LinearLayoutManager(context);
        txtNoData.setVisibility(View.VISIBLE);
        imgClear.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);

        empDViewModel = ViewModelProviders.of(getActivity(), new MyViewModelFactory(appId)).get(EmpDViewModel.class);
        empDetailsBinding.setEmpDlist(empDViewModel);

        callApi();


        empDetailsBinding.edtSearchEmpD.addTextChangedListener(new TextWatcher() {
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

        setOnClick();
    }

    private void callApi() {
        empDViewModel.getEmpDResponseLiveData().observe(activity, new Observer<List<EmpDModelDTO>>() {
            @Override
            public void onChanged(List<EmpDModelDTO> empDModelDTOS) {
                Log.e(TAG, "onChanged: " + empDModelDTOS);
                empDModelList.clear();

                if (empDModelList != null && empDModelList.isEmpty()){
                    empDetailsBinding.recyclerEmpDetailsFrag.setVisibility(View.VISIBLE);
                    empDetailsBinding.progressCircular.setVisibility(View.GONE);
                    empDetailsBinding.txtNoData.setVisibility(View.GONE);

                    for (EmpDModelDTO empD : empDModelDTOS){
                        empDModelList.add(new EmpDModelDTO(empD.getQrEmpName(), empD.getQrEmpMobileNumber(),
                                empD.getQrEmpAddress(), empD.isActive()));
                    }
                    setRecycler(empDModelList);
                }
                else {
                    empDetailsBinding.recyclerEmpDetailsFrag.setVisibility(View.GONE);
                    empDetailsBinding.progressCircular.setVisibility(View.GONE);
                    empDetailsBinding.txtNoData.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void setOnClick() {
        empDetailsBinding.cardAddEmpD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(getContext(), AddEmpActivity.class));
            }
        });
    }

    private void setRecycler(List<EmpDModelDTO> empDModelList) {
        empDetailsBinding.progressCircular.setVisibility(View.GONE);
        empDetailsBinding.txtNoData.setVisibility(View.GONE);
        adapter = new EmpDetailsAdapter(context,empDModelList);
        empDetailsBinding.recyclerEmpDetailsFrag.setLayoutManager(layoutManager);
        empDetailsBinding.recyclerEmpDetailsFrag.setAdapter(adapter);
    }


    private void filter(String text) {
        List<EmpDModelDTO> searchedList = new ArrayList<>();

        for (EmpDModelDTO item : empDModelList) {
            if (item.getQrEmpName().toLowerCase().contains(text.toLowerCase()) || item.getQrEmpMobileNumber().toLowerCase().contains(text.toLowerCase())) {
                searchedList.add(item);
            }
        }
        adapter.searchListAdapter(searchedList);
    }
}