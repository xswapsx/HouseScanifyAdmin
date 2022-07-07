package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.ItemUlbCheckboxBinding;
import com.appynitty.adminapp.models.UlbDTO;
import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.List;


public class UlbListAdapter extends RecyclerView.Adapter<UlbListAdapter.MyViewHolder> {

    private static final String TAG = "UlbListAdapter";
    private Context context;
    List<UlbDTO> ulbList;
    public boolean isAllChecked = false;
    public String arg = "";

    public UlbListAdapter(Context context, List<UlbDTO> ulbList) {
        this.context = context;
        this.ulbList = ulbList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemUlbCheckboxBinding ulbCheckboxBinding = ItemUlbCheckboxBinding.inflate(layoutInflater,parent, false);
        return new UlbListAdapter.MyViewHolder(ulbCheckboxBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final UlbDTO ulb = ulbList.get(position);
        holder.ulbCheckboxBinding.setUlbList(ulb);

        if(!isAllChecked){
            holder.ulbCheckboxBinding.chkBoxUlbName.setChecked(false);
        }
        else {
            holder.ulbCheckboxBinding.chkBoxUlbName.setChecked(true);
            String all_app_id = String.valueOf(ulb.getAppId());
            System.out.println("All app id :" + all_app_id);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("data",holder.getAdapterPosition());
            Log.e(TAG,"ulb app id: " +jsonObject);
        }


    }
    public void filterList(List<UlbDTO> searchList) {
        ulbList = searchList;
        notifyDataSetChanged();
        Log.e(TAG, "search list : "+ ulbList.size());
    }
    public void setAllChecked(boolean isAllChecked) {
        this.isAllChecked = isAllChecked;
        notifyDataSetChanged();
    }

    public void selectAll(){
        Log.e("onClickSelectAll","yes");
        isAllChecked =true;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return ulbList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemUlbCheckboxBinding ulbCheckboxBinding;
       // private CheckBox checkboxUlbName;
        public MyViewHolder(@NonNull ItemUlbCheckboxBinding ulbCheckboxBinding) {
            super(ulbCheckboxBinding.getRoot());
            this.ulbCheckboxBinding = ulbCheckboxBinding;
           // checkboxUlbName = itemView.findViewById(R.id.chk_box_ulb_name);
        }
    }
}
