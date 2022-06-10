package com.appynitty.adminapp.dialog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.QREmpListAdapter;
import com.appynitty.adminapp.databinding.DialogFilterBinding;
import com.appynitty.adminapp.models.QREmployeeDTO;
import com.appynitty.adminapp.viewmodels.QREmpListVM;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FilterDialogFragment extends DialogFragment {
    private static final String TAG = "FilterDialogFragment";
    private DialogFilterBinding binding;
    private FilterDialog.FilterDialogInterface filterDialogListener;
    private EditText edtSelectToDate, edtSelectFromDate;
    private Spinner spnrSelectEmployee;
    private TextView txtBtnCancel, txtBtnApplyFilter;
    private String frmDate, toDate, userId = "0";
    final Calendar myCalendar = Calendar.getInstance();
    QREmpListVM qrEmpListVM;
    private ArrayList<QREmployeeDTO> mQrEmpList;
    private QREmpListAdapter mAdapter;

    View view;

    public void setFilterDialogListener(FilterDialog.FilterDialogInterface filterDialogListener) {
        this.filterDialogListener = filterDialogListener;
    }

    public interface FilterDialogInterface {
        void onFilterDialogDismiss(String frmDate, String toDate, String userId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        qrEmpListVM = new ViewModelProvider(this).get(QREmpListVM.class);
    }

    private void init(View view) {
        initList();
        edtSelectFromDate = view.findViewById(R.id.edt_from_date);
        edtSelectToDate = view.findViewById(R.id.edt_to_date);
        spnrSelectEmployee = view.findViewById(R.id.spinner_EmpList);
        txtBtnApplyFilter = view.findViewById(R.id.txt_btn_app_filter);
        txtBtnCancel = view.findViewById(R.id.txt_btn_cancel);

        mAdapter = new QREmpListAdapter(getActivity(), mQrEmpList);
        spnrSelectEmployee.setAdapter(mAdapter);

        spnrSelectEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                QREmployeeDTO qrEmployee = (QREmployeeDTO) adapterView.getItemAtPosition(i);
                String selectedEmp = qrEmployee.getEmployeeName();
                userId = qrEmployee.getEmployeeID();
                Toast.makeText(getActivity(), selectedEmp + " selected", Toast.LENGTH_SHORT).show();

                Log.e(TAG, "onItemSelected: " + userId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        qrEmpListVM = new ViewModelProvider(this).get(QREmpListVM.class);
        qrEmpListVM.init();
        edtSelectFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        edtSelectFromDate.setText(getCurrentDate());
                        frmDate = getCurrentDate();
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        edtSelectToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        edtSelectToDate.setText(getCurrentDate());
                        toDate = getCurrentDate();
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        txtBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

        txtBtnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialogListener.onFilterDialogDismiss(frmDate, toDate, userId);
                dismiss();
            }
        });

        qrEmpListVM.getQREmpListLiveData().observe(getViewLifecycleOwner(), new Observer<List<QREmployeeDTO>>() {
            @Override
            public void onChanged(List<QREmployeeDTO> qrEmployeeDTOS) {
                Log.e(TAG, "onChanged: " + qrEmployeeDTOS.toString());
            }
        });
    }

    private void initList() {
        mQrEmpList = new ArrayList<>();
        mQrEmpList.add(new QREmployeeDTO("Swapnil", "1"));
        mQrEmpList.add(new QREmployeeDTO("Nikhil", "4"));
        mQrEmpList.add(new QREmployeeDTO("Ankit", "8"));
        mQrEmpList.add(new QREmployeeDTO("Abijit", "11"));
        mQrEmpList.add(new QREmployeeDTO("Hrithik", "13"));
        mQrEmpList.add(new QREmployeeDTO("Shahid", "15"));
        mQrEmpList.add(new QREmployeeDTO("Salman", "17"));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.dialog_filter, container);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().getAttributes().gravity = Gravity.BOTTOM;
        getDialog().setCancelable(false);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

    }

    public String getCurrentDate() {

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = format.format(myCalendar.getTime());
        return strDate;
    }
}
