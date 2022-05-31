package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;


public class UlbListAdapter extends RecyclerView.Adapter<UlbListAdapter.MyViewHolder> {

    private Context context;

    public UlbListAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ulb_checkbox, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkboxUlbName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkboxUlbName = itemView.findViewById(R.id.chk_box_ulb_name);

        }
    }
}
