package com.appynitty.adminapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
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
import com.appynitty.adminapp.activities.AddEmpActivity;
import com.appynitty.adminapp.adapters.EmpDetailsAdapter;


public class EmpDetailsFragment extends Fragment {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view== null){
            view =inflater.inflate(R.layout.fragment_emp_details, container, false);
            init();
        }
        return view;
    }

    private void init(){
        context = getActivity();
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

        crdAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(getContext(), AddEmpActivity.class));
            }
        });


    }

    private void setRecycler() {
        loader.setVisibility(View.GONE);
        txtNoData.setVisibility(View.GONE);
        adapter = new EmpDetailsAdapter(context);
        recyclerEmpDetails.setLayoutManager(layoutManager);
        recyclerEmpDetails.setAdapter(adapter);

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