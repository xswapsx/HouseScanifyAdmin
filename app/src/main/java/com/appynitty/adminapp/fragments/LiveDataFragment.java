package com.appynitty.adminapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.HomeActivity;
import com.appynitty.adminapp.adapters.TableRowLiveDataAdapter;
import com.appynitty.adminapp.databinding.FragmentLiveDataBinding;
import com.appynitty.adminapp.models.SpecificUlbDTO;
import com.appynitty.adminapp.utils.MyApplication;
import com.appynitty.adminapp.utils.MyViewModelFactory;
import com.appynitty.adminapp.viewmodels.UlbDataViewModel;


public class LiveDataFragment extends Fragment {
    private static final String TAG = "LiveDataFragment";
    private Context context;
    private View view;
    private FragmentLiveDataBinding binding;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerTableRow;
    private ProgressBar loader;
    private TableRowLiveDataAdapter adapter;
    private TextView txtNoData;
    private String appId, ulbName;
    HomeActivity activity;
    UlbDataViewModel ulbDataViewModel;
    MyApplication application;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_live_data, container, false);
            view = binding.getRoot();
            //here data must be an instance of the class MarsDataProvider
            binding.setLifecycleOwner(this);
            init();
        }
        return view;
    }

    private void init() {
        activity = (HomeActivity) getActivity();

        Bundle results = activity.getUlbData();
        appId = results.getString("val1");
        ulbName = results.getString("val2");
        Log.e(TAG, "AppID: " + appId + " ULB: " + ulbName);

        context = getActivity();
        ulbDataViewModel = ViewModelProviders.of(getActivity(),
                new MyViewModelFactory(appId)).get(UlbDataViewModel.class);
        binding.setUlbData(ulbDataViewModel);

        ulbDataViewModel.getSpecificUlbMutableLiveData().observe(getActivity(), new Observer<SpecificUlbDTO>() {
            @Override
            public void onChanged(SpecificUlbDTO specificUlbDTO) {
                Log.e(TAG, "onChanged: " + specificUlbDTO.getTotalHouse());
            }
        });


        recyclerTableRow = view.findViewById(R.id.recycler_table_row_live_data);
        txtNoData = view.findViewById(R.id.txt_no_data);
        loader = view.findViewById(R.id.progress_circular);
        layoutManager = new LinearLayoutManager(context);
        loader.setVisibility(View.GONE);
        txtNoData.setVisibility(View.VISIBLE);
        setRecycler();
    }

    private void setRecycler() {
        loader.setVisibility(View.GONE);
        txtNoData.setVisibility(View.GONE);
        adapter = new TableRowLiveDataAdapter(context);
        recyclerTableRow.setLayoutManager(layoutManager);
        recyclerTableRow.setAdapter(adapter);
    }
}