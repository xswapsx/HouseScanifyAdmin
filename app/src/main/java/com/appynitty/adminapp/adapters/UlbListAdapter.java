package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.ItemUlbCheckboxBinding;
import com.appynitty.adminapp.models.UlbDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.function.Function;


public class UlbListAdapter extends RecyclerView.Adapter<UlbListAdapter.MyViewHolder> {

    private static final String TAG = "UlbListAdapter";
    private Context context;
    List<UlbDTO> ulbList;
    public boolean isAllChecked = false;
    public String arg = "";
    String [] strings = new String [] {"1", "2" };
    List<String> stringList = new ArrayList<String>(Arrays.asList(strings));



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
            Log.e(TAG, " All ulb unselected : ");
        }
        else {
            holder.ulbCheckboxBinding.chkBoxUlbName.setChecked(true);

            /*ArrayList<String> appId = new ArrayList<String>();
            appId.add(String.valueOf(ulb.getAppId() + "  " + ulb.getUlbName()));
            *//*appId.add(String.valueOf(ulb.getAppId()));*//*
            Log.e(TAG, "array of app id: "+ appId);*/

            /*String[] ans = Arrays.copyOf(appId.toArray(), ulbList.size(), String[].class);
            Log.e(TAG, "get data: "+ ans);*/

            /*JSONArray jsonArray = new JSONArray();
            jsonArray.put(ulb.getAppId());
            Log.e(TAG,"array of appId: " +jsonArray);*/

            int count = ulbList.size();

            JSONObject object = new JSONObject();
            try {
                object.put("activeUlb",ulb.getAppId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e(TAG, "active ulb: " +object);


            JsonArray jsonArray = new JsonArray();
            jsonArray.add(String.valueOf(object)+",");
            Log.e(TAG, "active: " +jsonArray);

        }

        holder.ulbCheckboxBinding.chkBoxUlbName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.ulbCheckboxBinding.chkBoxUlbName.isChecked()) {
                    getItemId(holder.getAdapterPosition());
                    String ulbId = String.valueOf(ulb.getAppId());
                    String ulbName = String.valueOf(ulb.getUlbName());
                    Log.e(TAG, "ulb is : " + ulbName + ", " + "ulb id : " + ulbId);

                    JSONObject object = new JSONObject();
                    try {
                        object.put("activeUlb", ulbId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "active ulb: " + object);

                    JsonArray jsonArray = new JsonArray();
                    jsonArray.add(String.valueOf(object));
                    String jsonString = jsonArray.toString();
                    Log.e(TAG, "active: " +jsonString.toString());


                } else {
                    getItemId(holder.getAdapterPosition());
                    String ulbName = String.valueOf(ulb.getUlbName());
                    Log.e(TAG, "ulb unselected : " +ulbName);


                }
            }
        });
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
