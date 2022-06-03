package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.ItemAttendanceFragListBinding;
import com.appynitty.adminapp.models.AttendanceDTO;

import java.util.List;


public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {

    private static final String TAG = "AttendanceAdapter";
    private Context context;
    private List<AttendanceDTO> attendanceDTOList;

    public AttendanceAdapter(Context context, List<AttendanceDTO> attendanceDTOList) {
        this.context = context;
        this.attendanceDTOList = attendanceDTOList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAttendanceFragListBinding attendanceFragListBinding = ItemAttendanceFragListBinding.inflate(layoutInflater,parent,false);

        return new MyViewHolder(attendanceFragListBinding);
        /*return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_attendance_frag_list, parent, false));*/
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      final  AttendanceDTO attendanceDTO = attendanceDTOList.get(position);
        holder.attendanceFragListBinding.txtEmpNameAt.setText(attendanceDTO.getUserName());

    }

    @Override
    public int getItemCount() {
        return attendanceDTOList.size();
        /*return 10;*/
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemAttendanceFragListBinding attendanceFragListBinding;

        public MyViewHolder(@NonNull ItemAttendanceFragListBinding attendanceFragListBinding) {
            super(attendanceFragListBinding.getRoot());
            this.attendanceFragListBinding = attendanceFragListBinding;
        }
    }
}
