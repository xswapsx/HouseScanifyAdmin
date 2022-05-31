package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.activities.HomeActivity;
import com.appynitty.adminapp.databinding.DashboardItemListBinding;
import com.appynitty.adminapp.models.UlbDTO;

import java.util.List;


public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {
    private static final String TAG = "DashboardAdapter";
    private Context context;
    List<UlbDTO> ulbList;

    public DashboardAdapter(Context context, List<UlbDTO> ulbList) {
        this.context = context;
        this.ulbList = ulbList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        DashboardItemListBinding itemListBinding = DashboardItemListBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(itemListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final UlbDTO ulb = ulbList.get(position);
        holder.itemListBinding.setUlb(ulb);
        holder.itemListBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return ulbList.size();
    }

    public void filterList(List<UlbDTO> filteredList) {
        ulbList = filteredList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        DashboardItemListBinding itemListBinding;

        public MyViewHolder(@NonNull DashboardItemListBinding itemListBinding) {
            super(itemListBinding.getRoot());
            this.itemListBinding = itemListBinding;
            itemListBinding.cardDashItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e(TAG, "onClick: " + ulbList.get(getAdapterPosition()).getUlbName());
//                    DynamicToast.makeSuccess(context, ulbList.get(getAdapterPosition()).getUlbName()).show();
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.putExtra("appId", String.valueOf(ulbList.get(getAdapterPosition()).getAppId()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
