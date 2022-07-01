package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.AddEmpActivity;
import com.appynitty.adminapp.activities.AddUserDetailsActivity;
import com.appynitty.adminapp.databinding.ItemUserRightsListBinding;
import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.models.UserRoleModelDTO;

import java.util.List;


public class UserRightsAdapter extends RecyclerView.Adapter<UserRightsAdapter.MyViewHolder> {

    private Context context;
    private static final String TAG = "UserRightsAdapter";
    private List<UserRoleModelDTO> userRoleModelDTOList;

    public UserRightsAdapter(Context context, List<UserRoleModelDTO> userRoleModelDTOList) {
        this.context = context;
        this.userRoleModelDTOList = userRoleModelDTOList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user_rights_list, parent, false));
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemUserRightsListBinding userRightsListBinding = ItemUserRightsListBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(userRightsListBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserRoleModelDTO userRoleModelDTO = userRoleModelDTOList.get(position);
        holder.userRightsListBinding.setUserRoleItem(userRoleModelDTO);
        holder.userRightsListBinding.executePendingBindings();

        holder.userRightsListBinding.imgEditUserRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddUserDetailsActivity.class);
                intent.putExtra("userRoleRightsDetails", userRoleModelDTOList.get(holder.getAdapterPosition()));

                context.startActivity(intent);
                Log.e(TAG, "onClick: User Role Right Info:- " + userRoleModelDTOList.get(holder.getAdapterPosition()).toString());
                Log.e(TAG, "adapter clicked User Role Rights  Id:- " + userRoleModelDTO.getEmpId() + "  " + userRoleModelDTOList.get(holder.getAdapterPosition()).getEmpName()
                        + "  " + userRoleModelDTO.getType());

            }
        });

    }

    public void inActiveList(List<UserRoleModelDTO> inActiveUserListData) {
        userRoleModelDTOList = inActiveUserListData;
        notifyDataSetChanged();
    }

    public void activeList(List<UserRoleModelDTO> activeUserListData) {
        userRoleModelDTOList = activeUserListData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userRoleModelDTOList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /*private TextView txtEmpName, txtStatus, txtUserRole, txtMobile;
        private ImageView imgEditUserRights;*/

        ItemUserRightsListBinding userRightsListBinding;

        public MyViewHolder(@NonNull ItemUserRightsListBinding userRightsListBinding) {
            super(userRightsListBinding.getRoot());
            this.userRightsListBinding = userRightsListBinding;

            /*imgEditUserRights = itemView.findViewById(R.id.img_edit_user_right);
            txtEmpName = itemView.findViewById(R.id.txt_emp_name);
            txtMobile = itemView.findViewById(R.id.txt_emp_mobile);
            txtUserRole = itemView.findViewById(R.id.txt_user_role);
            txtStatus = itemView.findViewById(R.id.txt_emp_status);*/
        }
    }
}
