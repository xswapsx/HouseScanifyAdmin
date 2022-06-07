package com.appynitty.adminapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.HouseDetailsAdapter;
import com.appynitty.adminapp.dialog.FilterDialog;


public class HouseDetailsFragment extends Fragment implements FilterDialog.FilterDialogInterface {
    private static final String TAG = "HouseDetailsFragment";
    private Context context;
    private View view;
    private RadioGroup rdGroup;
    private RadioButton rdHouse, rdDumpYard, rdLiquid, rdStreet;

    private RecyclerView recyclerHouseImage;
    private LinearLayoutManager layoutManager;
    private HouseDetailsAdapter houseDetailsAdapter;
    private ProgressBar loader;
    private TextView txtNoData;

    private CardView crdFilter;
    private FilterDialog filterDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_house_details, container, false);
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


        recyclerHouseImage = view.findViewById(R.id.recycler_House_image);
        loader = view.findViewById(R.id.progress_circular);
        loader.setVisibility(View.GONE);
        txtNoData = view.findViewById(R.id.txt_no_data);
        txtNoData.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

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
    }

    private void openDialog() {
        /*filterDialog = new FilterDialog(context, new FilterDialog.FilterDialogInterface() {
            @Override
            public void onFilterDialogDismiss(String frmDate, String toDate, String userId) {
                Log.e(TAG, "onFilterDialogDismiss: frmDate: " + frmDate + " toDate: " + toDate);
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });*/
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(filterDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.DialogAnimation;
        filterDialog.getWindow().setAttributes(lp);
        filterDialog.setCancelable(false);
        filterDialog.show();
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