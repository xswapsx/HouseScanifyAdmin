package com.appynitty.adminapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.UlbListAdapter;
import com.appynitty.adminapp.databinding.ActivityAddUserDetailsBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddUserDetailsActivity extends AppCompatActivity {

    private Context context;
    private LinearLayoutManager layoutManager;
    private ProgressBar loader;
    private TextView txtEntries, txtNoData;
    private ImageView imgClear;
    private EditText edtSearchText;
    private RecyclerView recyclerUlbList;
    private UlbListAdapter adapter;
    private ActivityAddUserDetailsBinding binding;
    private Toolbar toolbar;
    private Spinner spinner;

    // Initializing a String Array
    String[] userRole = new String[]{
            "Select User Role",
            "Admin",
            "Sub Admin"
    };

    final List<String> userRoleList = new ArrayList<>(Arrays.asList(userRole));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_user_details);
        init();
    }

    private void init() {

        binding = DataBindingUtil.setContentView(AddUserDetailsActivity.this, R.layout.activity_add_user_details);
        binding.setLifecycleOwner(this);

        binding.toolbar.setTitle("Add User");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddUserDetailsActivity.this, UserRightsActivity.class));
            }
        });

        recyclerUlbList = findViewById(R.id.recycler_ulb_chkbox);
        loader = findViewById(R.id.progress_circular);
        txtNoData = findViewById(R.id.txt_no_data);
        edtSearchText = findViewById(R.id.edt_search_text);
        spinner = findViewById(R.id.spinner);
        imgClear = findViewById(R.id.img_close);
        txtEntries = findViewById(R.id.txt_entries);
        layoutManager = new GridLayoutManager(context, 2);
        txtNoData.setVisibility(View.VISIBLE);
        imgClear.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);

        setOnClick();
        setOnRecycler();
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

        /*edtSearchText.addOnAttachStateChangeListener((View.OnAttachStateChangeListener) searchViewTextWatcher);*/


    }

    private void performSearch() {

    }

    private void setOnRecycler() {
        loader.setVisibility(View.GONE);
        txtNoData.setVisibility(View.GONE);
        adapter = new UlbListAdapter(getBaseContext());
        recyclerUlbList.setLayoutManager(layoutManager);
        recyclerUlbList.setAdapter(adapter);

    }

    TextWatcher searchViewTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().trim().length() == 0) {
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