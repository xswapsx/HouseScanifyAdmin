package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;


public class EmpDetailsAdapter extends RecyclerView.Adapter<EmpDetailsAdapter.MyViewHolder> {

    private Context context;

    public EmpDetailsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_emp_details_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.imgAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAddEmp;
        private TextView txtEmpName, txtEmpMobile, txtAddress, txtStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAddEmp = itemView.findViewById(R.id.img_edit_emp_details);
            txtEmpName = itemView.findViewById(R.id.txt_emp_name);
            txtEmpMobile = itemView.findViewById(R.id.txt_emp_mobile);
            txtAddress = itemView.findViewById(R.id.txt_emp_address);
            txtStatus = itemView.findViewById(R.id.txt_emp_status);
        }
    }
}
