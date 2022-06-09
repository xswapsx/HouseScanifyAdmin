package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.models.QREmployeeDTO;

import java.util.ArrayList;

public class QREmpListAdapter extends ArrayAdapter<QREmployeeDTO> {

    public QREmpListAdapter(@NonNull Context context, ArrayList<QREmployeeDTO> qrEmpList) {
        super(context, 0, qrEmpList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.filter_emp_list_row_item, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.text_view_name);

        QREmployeeDTO currentItem = getItem(position);

        if (currentItem != null) {
            textViewName.setText(currentItem.getEmployeeName());
        }

        return convertView;
    }
}
