package com.appynitty.adminapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.HomeActivity;
import com.appynitty.adminapp.adapters.HouseDetailsAdapter;
import com.appynitty.adminapp.databinding.FragmentHouseDetailsBinding;
import com.appynitty.adminapp.dialog.FilterDialog;
import com.appynitty.adminapp.dialog.FilterDialogFragment;
import com.appynitty.adminapp.models.HouseDetailsImageDTO;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.viewmodels.HouseDetailsImageVM;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;


public class HouseDetailsFragment extends Fragment {
    private static final String TAG = "HouseDetailsFragment";
    private Context context;
    AppCompatActivity activity;
    private View view, homeButton;
    private RadioGroup rdGroup;
    private String filterType = "HW";
    private RadioButton rdHouse, rdDumpYard, rdLiquid, rdStreet;
    private List<HouseDetailsImageDTO> imageDataList;
    private RecyclerView recyclerHouseImage;
    private LinearLayoutManager layoutManager;
    private HouseDetailsAdapter houseDetailsAdapter;
    private ProgressBar loader;
    ImageButton ib;
    private TextView txtNoData, tvCount, tvBottomEntryCount;
    private EditText etHouseFilter;
    private Bundle filterExtras;
    private CardView crdFilter;
    private FilterDialogFragment filterDialog;
    HouseDetailsImageVM houseDetailsImageVM;
    FragmentHouseDetailsBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            /*binding = DataBindingUtil.inflate(inflater, R.layout.fragment_house_details, container, false);
            view = binding.getRoot();
            binding.setLifecycleOwner(this);*/
            view = inflater.inflate(R.layout.fragment_house_details, container, false);
            init();
        }
        return view;
    }


    private void init() {
        String empId = getArguments().getString(MainUtils.EMP_ID);
        Log.e(TAG, "init: empId- " + empId);
        context = getActivity();
        activity = (AppCompatActivity) view.getContext();
        rdGroup = view.findViewById(R.id.rd_group);
        rdHouse = view.findViewById(R.id.rdHouse);
        rdDumpYard = view.findViewById(R.id.rdDumpyard);
        rdLiquid = view.findViewById(R.id.rdLiquid);
        rdStreet = view.findViewById(R.id.rdStreet);
        imageDataList = new ArrayList<>();
        crdFilter = view.findViewById(R.id.card_filter);
//        homeButton = getActivity().findViewById(R.id.ib_home);
        recyclerHouseImage = view.findViewById(R.id.recycler_House_image);
        loader = view.findViewById(R.id.progress_circular);
        loader.setVisibility(View.GONE);
        txtNoData = view.findViewById(R.id.txt_no_data);
        tvCount = view.findViewById(R.id.tvItementries);
        tvBottomEntryCount = view.findViewById(R.id.txt_entries_bottom1);
        txtNoData.setVisibility(View.GONE);
        etHouseFilter = view.findViewById(R.id.etHouseFilter);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        ib = activity.findViewById(R.id.ib_home);
        ib.setImageResource(R.drawable.ic_back);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment empListFrag = new EmpListFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_frame_layout, empListFrag).addToBackStack(null).commit();
            }
        });

        houseDetailsImageVM = ViewModelProviders.of(this).get(HouseDetailsImageVM.class);
        houseDetailsImageVM.callHouseApi(empId);
//        binding.setImagesVM(houseDetailsImageVM);

        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rdHouse:
                        if (rdHouse.isChecked())
                            Log.e(TAG, "onRadioBtnClicked: checked rdHouse");
                        filterType = "HW";
                        houseDetailsImageVM.callHouseApi(empId);
                        /*houseDetailsImageVM.getHouseQrImagesLiveData().observe(getViewLifecycleOwner(), new Observer<List<HouseDetailsImageDTO>>() {

                            int imgCount = 0;

                            @Override
                            public void onChanged(List<HouseDetailsImageDTO> houseDetailsImageDTOS) {
                                Log.e(TAG, "HosueImageDetails Size: " + houseDetailsImageDTOS.size());
                                for (HouseDetailsImageDTO house : houseDetailsImageDTOS
                                ) {
                                    if (!house.getqRCodeImage().matches("/Images/default_not_upload.png")) {
                                        imgCount += 1;
                                        imageDataList.add(house);
                                    }
                                }
                                itemListCount = imageDataList.size();
                                Log.e(TAG, "ImgCount: " + imageDataList.size());
                                setOnRecycler(imageDataList);

                            }
                        });*/
                        break;
                    case R.id.rdDumpyard:
                        if (rdDumpYard.isChecked())
                            Log.e(TAG, "onRadioBtnClicked: checked rdDumpyard");
                        filterType = "DY";
                        houseDetailsImageVM.callDumpYardApi();
                        break;
                    case R.id.rdLiquid:
                        if (rdLiquid.isChecked())
                            Log.e(TAG, "onRadioBtnClicked: checked rdLiquid");
                        filterType = "LW";
                        houseDetailsImageVM.callLiquidWasteApi();
                        break;
                    case R.id.rdStreet:
                        if (rdStreet.isChecked())
                            Log.e(TAG, "onRadioBtnClicked: checked rdStreet");
                        filterType = "SW";
                        houseDetailsImageVM.callStreetWasteApi();
                        break;
                }
            }
        });

        etHouseFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e(TAG, "beforeTextChanged: before textchanged " + charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e(TAG, "onTextChanged: " + charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e(TAG, "afterTextChanged: " + editable.toString());
                filter(editable.toString());
            }
        });

        houseDetailsImageVM.getHouseQrImagesLiveData().observe(getViewLifecycleOwner(), new Observer<List<HouseDetailsImageDTO>>() {

            @Override
            public void onChanged(List<HouseDetailsImageDTO> houseDetailsImageDTOS) {
                imageDataList.clear();
                Log.e(TAG, "HosueImageDetails Size: " + houseDetailsImageDTOS.size());
                for (HouseDetailsImageDTO house : houseDetailsImageDTOS
                ) {
                    imageDataList.add(house);
                }
                setOnRecycler(imageDataList);
            }
        });

        houseDetailsImageVM.getDumpyQrImagesLiveData().observe(getViewLifecycleOwner(), new Observer<List<HouseDetailsImageDTO>>() {
            int size = 0;

            @Override
            public void onChanged(List<HouseDetailsImageDTO> dumpYardWasteList) {
                for (HouseDetailsImageDTO dumpYard :
                        dumpYardWasteList) {
                    imageDataList.add(dumpYard);
                    Log.e(TAG, "onChanged: DumpYardId: " + dumpYard.getReferanceId());
                }
                if (!imageDataList.isEmpty() || imageDataList != null) {
                    houseDetailsAdapter.dumpYardList(imageDataList);
                }
                Log.e(TAG, "onChanged: DumpYardId: Size---" + dumpYardWasteList.size());
            }
        });

        houseDetailsImageVM.getLiquidQrImagesLiveData().observe(getViewLifecycleOwner(), new Observer<List<HouseDetailsImageDTO>>() {
            @Override
            public void onChanged(List<HouseDetailsImageDTO> liquidWasteList) {
                for (HouseDetailsImageDTO liquidWaste :
                        liquidWasteList) {
                    imageDataList.add(liquidWaste);
                    Log.e(TAG, "onChanged: liquidId: " + liquidWaste.getReferanceId());
                }
                if (houseDetailsAdapter!= null) {
                    houseDetailsAdapter.getLiquidList(imageDataList);
                }

                Log.e(TAG, "onChanged: LiquidWasteList: Size---" + liquidWasteList.size());
            }
        });

        houseDetailsImageVM.getStreetQrImagesLiveData().observe(getViewLifecycleOwner(), new Observer<List<HouseDetailsImageDTO>>() {
            @Override
            public void onChanged(List<HouseDetailsImageDTO> streetWasteList) {
                for (HouseDetailsImageDTO streetWaste :
                        streetWasteList) {
                    imageDataList.add(streetWaste);
                    Log.e(TAG, "onChanged: streetId: " + streetWaste.getReferanceId());
                }
                if (houseDetailsAdapter!= null) {
                    houseDetailsAdapter.getLiquidList(imageDataList);
                }else{
                    setOnRecycler(imageDataList);
                }

                Log.e(TAG, "onChanged: streetWasteList: Size---" + streetWasteList.size());
            }
        });

        houseDetailsImageVM.getProgress().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                loader.setVisibility(integer);
            }
        });

        houseDetailsImageVM.getImageCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer imgCount) {
                tvCount.setText(getResources().getString(R.string.imageCount, imgCount));
                tvBottomEntryCount.setText(getResources().getString(R.string.imageCount, imgCount));
            }
        });

        setOnClick();

    }

    private void setOnClick() {
        crdFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        /* homeButton.setOnClickListener(View -> startActivity(new Intent(getActivity(), DashboardActivity.class)));*/
    }

    private void openDialog() {

        filterDialog = new FilterDialogFragment("houseDetails");

        filterDialog.setFilterDialogListener(new FilterDialog.FilterDialogInterface() {
            @Override
            public void onFilterDialogDismiss(String frmDate, String toDate, String userId) {
                Log.e(TAG, "onFilterDialogDismiss: frmDate: " + frmDate + " userId: " + userId);
                filterExtras = new Bundle();
                filterExtras.putString("frmDate", frmDate);
                filterExtras.putString("toDate", frmDate);
                filterExtras.putString("userId", userId);
                filterExtras.putString("filterType", filterType);
                filterExtras.putString("appId", Prefs.getString(MainUtils.APP_ID));
                houseDetailsImageVM.setFilterData(filterExtras);
            }
        });

        filterDialog.show(getChildFragmentManager(), TAG);
    }

    private void filter(String text) {
        List<HouseDetailsImageDTO> filteredList = new ArrayList<>();

        for (HouseDetailsImageDTO item : imageDataList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        houseDetailsAdapter.filterList(filteredList);
    }


    private void setOnRecycler(List<HouseDetailsImageDTO> imageDataList) {
        txtNoData.setVisibility(View.GONE);
//        tvCount.setText(itemListCount);
        loader.setVisibility(View.GONE);
        houseDetailsAdapter = new HouseDetailsAdapter(context, imageDataList);
        houseDetailsAdapter.notifyDataSetChanged();
        recyclerHouseImage.setLayoutManager(layoutManager);
        recyclerHouseImage.setAdapter(houseDetailsAdapter);
    }

    private void moveToNewActivity() {

        Intent i = new Intent(getActivity(), HomeActivity.class);
        startActivity(i);
        activity.overridePendingTransition(0, 0);

    }

}