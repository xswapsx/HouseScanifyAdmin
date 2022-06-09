package com.appynitty.adminapp.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.DialogFilterBinding;
import com.appynitty.adminapp.models.FilterDTO;
import com.appynitty.adminapp.viewmodels.UlbDataViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FilterDialogFragment extends DialogFragment {
    private static final String TAG = "FilterDialogFragment";
    private FilterDialogFragment.FilterDialogInterface filterDialogListener;
    DialogFilterBinding filterBinding;

    UlbDataViewModel ulbDataViewModel;
    private Context context;
    private EditText edtSelectToDate, edtSelectFromDate, edtSelectEmployee;
    private TextView txtBtnCancel, txtBtnApplyFilter;
    private CardView crdSelectFromD, crdSelectToD, crdSelectEmp;
    private String frmDate, toDate, userId = "0";
    final Calendar myCalendar = Calendar.getInstance();

    public FilterDialogFragment(Context context) {
        this.context = context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
        filterBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_filter, container, false);
        ulbDataViewModel = new ViewModelProvider(requireActivity()).get(UlbDataViewModel.class);
        filterBinding.setEmpList(ulbDataViewModel);

        init();
        return filterBinding.getRoot();
//        return inflater.inflate(R.layout.dialog_filter, container, false);
    }

    private void setObservers() {

        ulbDataViewModel.getFilterLiveData().observe(this, new Observer<FilterDTO>() {
            @Override
            public void onChanged(FilterDTO filterDTO) {
                Log.e(TAG, "onChanged: " + filterDTO.toString());
                filterDialogListener.onFilterDialogDismiss(filterDTO.getFromDate(), filterDTO.getToDate(), "0");
                FilterDialogFragment.this.dismiss();
            }
        });
    }


    public void setFilterDialogListener(FilterDialogFragment.FilterDialogInterface filterDialogListener) {
        this.filterDialogListener = filterDialogListener;
    }


    private void init() {


        setObservers();

        filterBinding.edtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "From", Toast.LENGTH_SHORT).show();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        filterBinding.edtFromDate.setText(getCurrentDate());
                        frmDate = getCurrentDate();
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        filterBinding.edtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "To", Toast.LENGTH_SHORT).show();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        filterBinding.edtToDate.setText(getCurrentDate());
                        frmDate = getCurrentDate();
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        filterBinding.txtBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

        filterBinding.txtBtnAppFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialogListener.onFilterDialogDismiss(frmDate, toDate, userId);
                dismiss();
            }
        });

    }

    public String getCurrentDate() {

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = format.format(myCalendar.getTime());
        return strDate;
    }

    public interface FilterDialogInterface {
        void onFilterDialogDismiss(String frmDate, String toDate, String userId);
    }
}
