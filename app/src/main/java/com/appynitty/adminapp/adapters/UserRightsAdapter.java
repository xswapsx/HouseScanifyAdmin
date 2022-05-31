package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;


public class UserRightsAdapter extends RecyclerView.Adapter<UserRightsAdapter.MyViewHolder> {

    private Context context;

    public UserRightsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user_rights_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imgEditUserRights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*** add new Activity ***/
                /*context.startActivity(new Intent(context, ));*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtEmpName, txtStatus, txtUserRole, txtMobile;
        private ImageView imgEditUserRights;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgEditUserRights = itemView.findViewById(R.id.img_edit_user_right);
            txtEmpName = itemView.findViewById(R.id.txt_emp_name);
            txtMobile = itemView.findViewById(R.id.txt_emp_mobile);
            txtUserRole = itemView.findViewById(R.id.txt_user_role);
            txtStatus = itemView.findViewById(R.id.txt_emp_status);
        }
    }
}
