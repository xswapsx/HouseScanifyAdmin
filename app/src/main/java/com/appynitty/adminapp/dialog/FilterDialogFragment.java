package com.appynitty.adminapp.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.DialogFilterBinding;
import com.appynitty.adminapp.viewmodels.UlbDataViewModel;

public class FilterDialogFragment extends DialogFragment {
    private DialogFilterBinding binding;
    private UlbDataViewModel ulbDataViewModel;
    private FilterDialog.FilterDialogInterface filterDialogListener;

    public void setFilterDialogListener(FilterDialog.FilterDialogInterface filterDialogListener) {
        this.filterDialogListener = filterDialogListener;
    }

    public interface FilterDialogInterface {
        void onFilterDialogDismiss(String frmDate, String toDate, String userId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ulbDataViewModel = new ViewModelProvider(this).get(UlbDataViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogFilterBinding.inflate(inflater, container, false);
        binding.setEmpList(ulbDataViewModel);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if (getDialog() == null) return;
        Window window = getDialog().getWindow();
        if (window == null) return;
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.4);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.gravity = Gravity.BOTTOM;
        params.windowAnimations = R.style.DialogAnimation;
        window.setAttributes(params);*/

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().getAttributes().gravity = Gravity.BOTTOM;
        getDialog().setCancelable(false);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

    }
}
