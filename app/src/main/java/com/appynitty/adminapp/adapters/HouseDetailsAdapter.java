package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.BuildConfig;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.ZoomViewActivity;
import com.appynitty.adminapp.models.HouseDetailsImageDTO;
import com.bumptech.glide.Glide;

import java.util.List;

public class HouseDetailsAdapter extends RecyclerView.Adapter<HouseDetailsAdapter.MyViewHolder> {
    private static final String TAG = "HouseDetailsAdapter";
    private List<HouseDetailsImageDTO> imageDataList;
    String imageBytes = "";
    private Context context;
    boolean isChecked = true;
    public static int imgAccept = 1;
    public static int imgReject = 2;

    public HouseDetailsAdapter(Context context, List<HouseDetailsImageDTO> imageDataList1) {
        this.context = context;
        imageDataList = imageDataList1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card_photos_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        imageBytes = imageDataList.get(position).getqRCodeImage();

        Glide.with(context)
                .load(imageBytes)
                .fitCenter()

                .into(holder.imgPhoto);

        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e(TAG, "onClick: image:====" + imageDataList.get(position).getqRCodeImage());
                Intent intent = new Intent(context, ZoomViewActivity.class);
                intent.putExtra("qrImage", String.valueOf(imageDataList.get(holder.getAdapterPosition()).getqRCodeImage()));
                context.startActivity(intent);
            }
        });

        holder.txtImgAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txtImgAccept.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.solid_rounded_green_btn));
                holder.txtImgAccept.setTextColor(context.getResources().getColor(R.color.white));
                holder.txtImgReject.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.solid_rounded_white));
                holder.txtImgReject.setTextColor(context.getResources().getColor(R.color.black));
                Toast.makeText(context, "Image approved successfully", Toast.LENGTH_SHORT).show();
            }
        });

        holder.txtImgReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txtImgReject.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.solid_rounded_red_btn));
                holder.txtImgReject.setTextColor(context.getResources().getColor(R.color.white));
                holder.txtImgAccept.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.solid_rounded_white));
                holder.txtImgAccept.setTextColor(context.getResources().getColor(R.color.black));
                Toast.makeText(context, "Image reject successfully", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "HouseScanify Admin App");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.LIBRARY_PACKAGE_NAME + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    context.startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });

        holder.txtDateTime.setText(imageDataList.get(position).getModifiedDate());
        holder.txtEmpName.setText(imageDataList.get(position).getName());
        holder.txtRefId.setText(imageDataList.get(position).getReferanceId());
    }

    @Override
    public int getItemCount() {
        return imageDataList.size();
    }

    public void dumpYardList(List<HouseDetailsImageDTO> dumpYardList) {
        imageDataList.clear();
        imageDataList = dumpYardList;
        notifyDataSetChanged();
    }

    public void getLiquidList(List<HouseDetailsImageDTO> liquidList) {
        imageDataList.clear();
        imageDataList = liquidList;
        notifyDataSetChanged();
    }

    public void getStreetList(List<HouseDetailsImageDTO> streetList) {
        imageDataList.clear();
        imageDataList = streetList;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDateTime, txtRefId, txtEmpName, txtLatitude, txtLongitude;
        private ImageView imgPhoto, imgShare;
        private TextView txtImgAccept, txtImgReject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDateTime = itemView.findViewById(R.id.txt_date_time);
            txtRefId = itemView.findViewById(R.id.txt_houseId);
            txtEmpName = itemView.findViewById(R.id.txt_empName);
            txtLatitude = itemView.findViewById(R.id.txt_latitude);
            txtLongitude = itemView.findViewById(R.id.txt_longitude);
            imgPhoto = itemView.findViewById(R.id.img_photos);
            imgShare = itemView.findViewById(R.id.img_share);
            txtImgAccept = itemView.findViewById(R.id.txt_img_accept);
            txtImgReject = itemView.findViewById(R.id.txt_img_reject);
        }
    }
}
