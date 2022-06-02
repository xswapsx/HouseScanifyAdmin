package com.appynitty.adminapp.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.appynitty.adminapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FilterDialog extends Dialog {

    private Context context;
    private EditText edtSelectToDate, edtSelectFromDate, edtSelectEmployee;
    private TextView txtBtnCancel, txtBtnApplyFilter;

    private CardView crdSelectFromD, crdSelectToD, crdSelectEmp;

    final Calendar myCalendar= Calendar.getInstance();

    private FilterDialogInterface listener;
    public FilterDialog(@NonNull Context context, FilterDialogInterface listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    public interface FilterDialogInterface{

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_filter);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(context.getResources().getColor(R.color.colorWhite));
        }
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);


         init();
    }

    private void init() {

        edtSelectFromDate = findViewById(R.id.edt_from_date);
        edtSelectToDate = findViewById(R.id.edt_to_date);
        edtSelectEmployee = findViewById(R.id.edt_select_emp);
        txtBtnApplyFilter = findViewById(R.id.txt_btn_app_filter);
        txtBtnCancel = findViewById(R.id.txt_btn_cancel);
        crdSelectFromD = findViewById(R.id.card_select_from_date);
        crdSelectToD = findViewById(R.id.card_select_to_date);
        crdSelectEmp = findViewById(R.id.card_select_emp);

        setOnClick();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(context, R.color.colorWhite));
        }

    }

    private void setOnClick() {

        txtBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        txtBtnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        crdSelectEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        crdSelectFromD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                updateFromDate();
            }
        });

        crdSelectToD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                updateToDate();
            }
        });
    }

    DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateToDate();
            updateFromDate();
        }
    };

    private void updateToDate() {
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        edtSelectToDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void updateFromDate() {
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        edtSelectFromDate.setText(dateFormat.format(myCalendar.getTime()));
    }

}
