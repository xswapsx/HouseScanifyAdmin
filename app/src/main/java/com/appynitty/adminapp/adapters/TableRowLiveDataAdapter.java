package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;


public class TableRowLiveDataAdapter extends RecyclerView.Adapter<TableRowLiveDataAdapter.MyViewHolder> {

    private Context context;

    public TableRowLiveDataAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.table_row_item_live_data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TableLayout tableLayout;
        private TableRow tableRow;
        private TextView txtRowName, txtRowHouse, txtRowLiquid, txtRowStreet, txtRowDump;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tableLayout = itemView.findViewById(R.id.table_view);
            tableRow = itemView.findViewById(R.id.table_row);
            txtRowName = itemView.findViewById(R.id.row_txt_name);
            txtRowHouse = itemView.findViewById(R.id.row_txt_house);
            txtRowLiquid = itemView.findViewById(R.id.row_txt_liquid);
            txtRowStreet = itemView.findViewById(R.id.row_txt_street);
            txtRowDump = itemView.findViewById(R.id.row_txt_dump);
        }
    }
}
