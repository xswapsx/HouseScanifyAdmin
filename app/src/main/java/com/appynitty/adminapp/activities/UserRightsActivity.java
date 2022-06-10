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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.UserRightsAdapter;
import com.appynitty.adminapp.databinding.ActivityUserRightsBinding;
import com.appynitty.adminapp.viewmodels.UserRightViewModel;


public class UserRightsActivity extends AppCompatActivity {
    private Context context;
    private LinearLayoutManager layoutManager;
    private ProgressBar loader;
    private TextView txtEntries, txtNoData;
    private ImageView imgClear;
    private EditText edtSearchText;
    private RecyclerView recyclerUserRights;
    private CardView crdAddEmp;
    private UserRightsAdapter adapter;
    private UserRightViewModel userRightViewModel;
    private ActivityUserRightsBinding binding;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        context = this;
        binding = DataBindingUtil.setContentView(UserRightsActivity.this, R.layout.activity_user_rights);
        binding.setLifecycleOwner(this);
        userRightViewModel = ViewModelProviders.of(this).get(UserRightViewModel.class);

        binding.toolbar.setTitle("User Rights");
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserRightsActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserRightsActivity.this, DashboardActivity.class));
            }
        });

        recyclerUserRights = findViewById(R.id.recycler_user_rights);
        loader = findViewById(R.id.progress_circular);
        txtNoData = findViewById(R.id.txt_no_data);
        edtSearchText = findViewById(R.id.edt_search_user_right);
        imgClear = findViewById(R.id.img_close);
        crdAddEmp = findViewById(R.id.card_addEmp_user_right);
        txtEntries = findViewById(R.id.txt_entries);
        layoutManager = new LinearLayoutManager(context);
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

        crdAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, AddUserDetailsActivity.class));
            }
        });

    }

    private void performSearch() {

    }

    private void setOnRecycler() {
        loader.setVisibility(View.GONE);
        txtNoData.setVisibility(View.GONE);
        adapter = new UserRightsAdapter(context);
        recyclerUserRights.setLayoutManager(layoutManager);
        recyclerUserRights.setAdapter(adapter);

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