package com.appynitty.adminapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.adapters.TableRowLiveDataAdapter;


public class LiveDataFragment extends Fragment {
    private Context context;
    private View view;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerTableRow;
    private ProgressBar loader;
    private TableRowLiveDataAdapter adapter;
    private TextView txtNoData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null ){
            view = inflater.inflate(R.layout.fragment_live_data, container, false);
            init();
        }
        return view;
    }

    private void init(){
        context = getActivity();
        recyclerTableRow = view.findViewById(R.id.recycler_table_row_live_data);
        txtNoData = view.findViewById(R.id.txt_no_data);
        loader = view.findViewById(R.id.progress_circular);
        layoutManager = new LinearLayoutManager(context);
        loader.setVisibility(View.GONE);
        txtNoData.setVisibility(View.VISIBLE);
        setRecycler();
    }

    private void setRecycler() {
        loader.setVisibility(View.GONE);
        txtNoData.setVisibility(View.GONE);
        adapter = new TableRowLiveDataAdapter(context);
        recyclerTableRow.setLayoutManager(layoutManager);
        recyclerTableRow.setAdapter(adapter);
    }
}