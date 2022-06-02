package com.appynitty.adminapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
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
import com.appynitty.adminapp.activities.HomeActivity;
import com.appynitty.adminapp.adapters.AttendanceAdapter;
import com.appynitty.adminapp.databinding.FragmentAttendanceBinding;
import com.appynitty.adminapp.utils.MyApplication;
import com.appynitty.adminapp.viewmodels.AttendanceViewModel;


public class AttendanceFragment extends Fragment {
    private static final String TAG = "AttendanceFragment";
    private String empType = "";
    private Context context;
    private View view;
    private LinearLayoutManager layoutManager;
    private ProgressBar loader;
    private TextView txtEntries, txtNoData;
    private ImageView imgClear;
    private EditText edtSearchText;
    private RecyclerView recyclerAttendance;
    private AttendanceAdapter adapter;

    private AttendanceViewModel attendanceViewModel;
    private FragmentAttendanceBinding attendanceBinding;
    HomeActivity activity;
    MyApplication application;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_attendance, container, false);
            init();
        }
        return view;
    }

    private void init(){
        context = getActivity();
        recyclerAttendance = view.findViewById(R.id.recycler_attendance);
        loader = view.findViewById(R.id.progress_circular);
        txtNoData = view.findViewById(R.id.txt_no_data);
        edtSearchText = view.findViewById(R.id.edt_search_text);
        imgClear = view.findViewById(R.id.img_close);
        txtEntries = view.findViewById(R.id.txt_entries);
        layoutManager = new LinearLayoutManager(context);
        txtNoData.setVisibility(View.VISIBLE);
        imgClear.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);

        setRecycler();
        setOnClick();
    }

    private void setOnClick() {
        edtSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

       /* edtSearchText.addOnAttachStateChangeListener((View.OnAttachStateChangeListener) searchViewTextWatcher);*/

    }

    private void setRecycler() {
        txtNoData.setVisibility(View.GONE);
        loader.setVisibility(View.GONE);
        adapter = new AttendanceAdapter(context);
        recyclerAttendance.setLayoutManager(layoutManager);
        recyclerAttendance.setAdapter(adapter);

    }

    private void performSearch(){

    }

    TextWatcher searchViewTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(charSequence.toString().trim().length()==0){
                imgClear.setVisibility(View.GONE);
            } else {
                imgClear.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}