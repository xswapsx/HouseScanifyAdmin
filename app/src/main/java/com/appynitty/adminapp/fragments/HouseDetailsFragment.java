package com.appynitty.adminapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.DashboardActivity;
import com.appynitty.adminapp.adapters.HouseDetailsAdapter;
import com.appynitty.adminapp.databinding.FragmentHouseDetailsBinding;
import com.appynitty.adminapp.dialog.FilterDialog;
import com.appynitty.adminapp.dialog.FilterDialogFragment;
import com.appynitty.adminapp.models.HouseDetailsImageDTO;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.viewmodels.HouseDetailsImageVM;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;


public class HouseDetailsFragment extends Fragment implements FilterDialog.FilterDialogInterface {
    private static final String TAG = "HouseDetailsFragment";
    private Context context;
    private View view, homeButton;
    private RadioGroup rdGroup;
    private RadioButton rdHouse, rdDumpYard, rdLiquid, rdStreet;

    private RecyclerView recyclerHouseImage;
    private LinearLayoutManager layoutManager;
    private HouseDetailsAdapter houseDetailsAdapter;
    private ProgressBar loader;
    private TextView txtNoData;
    private Bundle filterExtras;
    private CardView crdFilter;
    private FilterDialogFragment filterDialog;
    HouseDetailsImageVM houseDetailsImageVM;
    FragmentHouseDetailsBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_house_details, container, false);
            view = binding.getRoot();
            binding.setLifecycleOwner(this);
            init();
        }
        return view;
    }

    private void init() {
        context = getActivity();
        rdHouse = view.findViewById(R.id.rdHouse);
        rdDumpYard = view.findViewById(R.id.rdDumpyard);
        rdLiquid = view.findViewById(R.id.rdLiquid);
        rdStreet = view.findViewById(R.id.rdStreet);

        crdFilter = view.findViewById(R.id.card_filter);

        homeButton = getActivity().findViewById(R.id.ib_home);
        recyclerHouseImage = view.findViewById(R.id.recycler_House_image);
        loader = view.findViewById(R.id.progress_circular);
        loader.setVisibility(View.GONE);
        txtNoData = view.findViewById(R.id.txt_no_data);
        txtNoData.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        houseDetailsImageVM = ViewModelProviders.of(this).get(HouseDetailsImageVM.class);
        binding.setImagesVM(houseDetailsImageVM);

        houseDetailsImageVM.getHouseQrImagesLiveData().observe(getViewLifecycleOwner(), new Observer<List<HouseDetailsImageDTO>>() {

            int imgCount = 0;

            @Override
            public void onChanged(List<HouseDetailsImageDTO> houseDetailsImageDTOS) {
                Log.e(TAG, "HosueImageDetails Size: " + houseDetailsImageDTOS.size());
                for (HouseDetailsImageDTO house : houseDetailsImageDTOS
                ) {
                    if (!house.getqRCodeImage().matches("/Images/default_not_upload.png"))
                        imgCount += 1;

                }
                Log.e(TAG, "ImgCount: " + imgCount);
            }
        });
        setOnClick();
        setOnRecycler();


    }

    private void setOnClick() {
        crdFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        homeButton.setOnClickListener(View -> startActivity(new Intent(getActivity(), DashboardActivity.class)));
    }

    private void openDialog() {

        filterDialog = new FilterDialogFragment();

        filterDialog.setFilterDialogListener(new FilterDialog.FilterDialogInterface() {
            @Override
            public void onFilterDialogDismiss(String frmDate, String toDate, String userId) {
                Log.e(TAG, "onFilterDialogDismiss: frmDate: " + frmDate + " toDate: " + toDate + " userId: " + userId);
                filterExtras = new Bundle();
                filterExtras.putString("frmDate", frmDate);
                filterExtras.putString("toDate", toDate);
                filterExtras.putString("userId", userId);
                filterExtras.putString("appId", Prefs.getString(MainUtils.APP_ID));
//                ulbDataViewModel.setFilteredData(filterExtras);
            }
        });

        filterDialog.show(getChildFragmentManager(), TAG);
    }

    private void setOnRecycler() {
        txtNoData.setVisibility(View.GONE);
        loader.setVisibility(View.GONE);
        houseDetailsAdapter = new HouseDetailsAdapter(context);
        recyclerHouseImage.setLayoutManager(layoutManager);
        recyclerHouseImage.setAdapter(houseDetailsAdapter);
    }

    @Override
    public void onFilterDialogDismiss(String frmDate, String toDate, String userId) {
        Log.e(TAG, "onFilterDialogDismiss: frmDate: " + frmDate + " toDate: " + toDate);
    }
}