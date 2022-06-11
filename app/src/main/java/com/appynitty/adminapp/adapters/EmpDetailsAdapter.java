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
import com.appynitty.adminapp.databinding.ItemEmpDetailsListBinding;
import com.appynitty.adminapp.models.AttendanceDTO;
import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.utils.MainUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;


public class EmpDetailsAdapter extends RecyclerView.Adapter<EmpDetailsAdapter.MyViewHolder> {
    private static final String TAG = "EmpDetailsAdapter";
    private Context context;
    private List<EmpDModelDTO> empDModelDTOList;

    public EmpDetailsAdapter(Context context, List<EmpDModelDTO> empDModelDTOList) {
        this.context = context;
        this.empDModelDTOList = empDModelDTOList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemEmpDetailsListBinding itemEmpDListBinding = ItemEmpDetailsListBinding.inflate(layoutInflater,parent,false);
        return new MyViewHolder(itemEmpDListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      final   EmpDModelDTO empDModelDTO = empDModelDTOList.get(position);

        holder.itemEmpDListBinding.setEmpDItem(empDModelDTO);
        holder.itemEmpDListBinding.executePendingBindings();

        holder.itemEmpDListBinding.imgEditEmpDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, AddEmpActivity.class).putExtra("empDModel", String.valueOf(empDModelDTO)));
            }
        });

    }

    public void searchListAdapter(List<EmpDModelDTO> searchedList) {
        empDModelDTOList = searchedList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return empDModelDTOList.size();
        /*return 20;*/
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /*private ImageView imgAddEmp;
        private TextView txtEmpName, txtEmpMobile, txtAddress, txtStatus;*/
        ItemEmpDetailsListBinding itemEmpDListBinding;

        public MyViewHolder(@NonNull ItemEmpDetailsListBinding itemEmpDListBinding) {
            super(itemEmpDListBinding.getRoot());
            this.itemEmpDListBinding = itemEmpDListBinding;
            /*Prefs.putString(MainUtils.EMP_ID, String.valueOf(empDModelDTOList.get(getAdapterPosition()).getQrEmpId()));
            Log.e(TAG, "QrEmpID: " + empDModelDTOList.get(getAdapterPosition()).getQrEmpId());*/

            /*imgAddEmp = itemView.findViewById(R.id.img_edit_emp_details);
            txtEmpName = itemView.findViewById(R.id.txt_emp_name);
            txtEmpMobile = itemView.findViewById(R.id.txt_emp_mobile);
            txtAddress = itemView.findViewById(R.id.txt_emp_address);
            txtStatus = itemView.findViewById(R.id.txt_emp_status);*/
        }
    }
}
