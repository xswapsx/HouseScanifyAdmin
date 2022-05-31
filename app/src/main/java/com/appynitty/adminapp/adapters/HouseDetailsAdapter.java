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
import androidx.viewbinding.BuildConfig;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.ZoomViewActivity;

public class HouseDetailsAdapter extends RecyclerView.Adapter<HouseDetailsAdapter.MyViewHolder> {

    private Context context;

    public HouseDetailsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card_photos_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ZoomViewActivity.class));
            }
        });

        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "HouseScanify Admin App");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.LIBRARY_PACKAGE_NAME +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                   context.startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDateTime, txtHouseId, txtEmpName, txtLatitude, txtLongitude;
        private ImageView imgPhoto, imgShare;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDateTime = itemView.findViewById(R.id.txt_date_time);
            txtHouseId = itemView.findViewById(R.id.txt_houseId);
            txtEmpName = itemView.findViewById(R.id.txt_empName);
            txtLatitude = itemView.findViewById(R.id.txt_latitude);
            txtLongitude = itemView.findViewById(R.id.txt_longitude);
            imgPhoto = itemView.findViewById(R.id.img_photos);
            imgShare = itemView.findViewById(R.id.img_share);
        }
    }
}
