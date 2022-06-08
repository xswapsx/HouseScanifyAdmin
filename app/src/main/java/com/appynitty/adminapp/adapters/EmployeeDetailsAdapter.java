package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.databinding.EmpDetailsRowItemBinding;
import com.appynitty.adminapp.models.EmployeeDetailsDTO;
import com.appynitty.adminapp.models.UlbDTO;

import java.util.List;


public class EmployeeDetailsAdapter extends RecyclerView.Adapter<EmployeeDetailsAdapter.MyViewHolder> {
    private static final String TAG = "TableRowLiveDataAdapter";
    private Context context;
    List<EmployeeDetailsDTO> employeeDetailsList;

    public EmployeeDetailsAdapter(Context context, List<EmployeeDetailsDTO> employeeDetailsList) {
        this.context = context;
        this.employeeDetailsList = employeeDetailsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        EmpDetailsRowItemBinding binding = EmpDetailsRowItemBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final EmployeeDetailsDTO empDetails = employeeDetailsList.get(position);
        holder.binding.setEmpList(empDetails);
        holder.binding.executePendingBindings();
    }

    public void filterList(List<EmployeeDetailsDTO> filteredList) {
        employeeDetailsList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return employeeDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EmpDetailsRowItemBinding binding;

        public MyViewHolder(@NonNull EmpDetailsRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
