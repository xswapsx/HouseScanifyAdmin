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
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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
    MyClickListener myClickListener;
    HouseDetailsImageDTO houseDetailsItem = new HouseDetailsImageDTO();
    String imageBytes = "";
    private Context context;
    private int listItemCount = 0;
    boolean isChecked = true;
    public static int imgAccept = 1;
    public static int imgReject = 2;

    public HouseDetailsAdapter(Context context, List<HouseDetailsImageDTO> imageDataList1, MyClickListener myClickListener) {
        this.context = context;
        imageDataList = imageDataList1;
        this.myClickListener = myClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_house_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        houseDetailsItem = imageDataList.get(position);
//        imageBytes = imageDataList.get(position).getqRCodeImage();

        Glide.with(context)
                .load(imageDataList.get(holder.getAdapterPosition()).getQRCodeImage())
                .fitCenter()
                .into(holder.imgPhoto);

        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e(TAG, "onClick: image:====" + imageDataList.get(position).getqRCodeImage());
                Intent intent = new Intent(context, ZoomViewActivity.class);
                intent.putExtra("qrImage", String.valueOf(imageDataList.get(holder.getAdapterPosition()).getQRCodeImage()));
                context.startActivity(intent);
            }
        });

        if (imageDataList.get(holder.getAdapterPosition()).getQRStatus() != null) {
            if (imageDataList.get(holder.getAdapterPosition()).getQRStatus().equals(true)) {
                holder.cardImgAccept.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorONDutyGreen));
                holder.txtImgAccept.setTextColor(ContextCompat.getColor(context, R.color.white));
                holder.cardImgReject.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
                holder.txtImgReject.setTextColor(ContextCompat.getColor(context, R.color.black));

            } else if (imageDataList.get(holder.getAdapterPosition()).getQRStatus().equals(false)) {
                holder.cardImgReject.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorOFFDutyRed));
                holder.txtImgReject.setTextColor(ContextCompat.getColor(context, R.color.white));
                holder.cardImgAccept.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
                holder.txtImgAccept.setTextColor(ContextCompat.getColor(context, R.color.black));
            }
        } /*else {
            Log.e(TAG, "onBindViewHolder: Null HPSBA-Id: " + imageDataList.get(holder.getAdapterPosition()).getReferanceId());
        }*/

        holder.cardImgAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cardImgAccept.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorONDutyGreen));
                holder.txtImgAccept.setTextColor(ContextCompat.getColor(context, R.color.white));
                holder.cardImgReject.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
                holder.txtImgReject.setTextColor(ContextCompat.getColor(context, R.color.black));
                myClickListener.onItemClicked(imageDataList.get(holder.getAdapterPosition()).getReferanceId(), true);
            }
        });

        holder.cardImgReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cardImgReject.setCardBackgroundColor(context.getResources().getColor(R.color.colorOFFDutyRed));
                holder.txtImgReject.setTextColor(context.getResources().getColor(R.color.white));
                holder.cardImgAccept.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                holder.txtImgAccept.setTextColor(context.getResources().getColor(R.color.black));
                myClickListener.onItemClicked(imageDataList.get(holder.getAdapterPosition()).getReferanceId(), false);
            }
        });

        holder.txtLatitude.setText(imageDataList.get(holder.getAdapterPosition()).getLat());
        holder.txtLongitude.setText(imageDataList.get(holder.getAdapterPosition()).getLong());
        /*holder.txtImgAccept.setOnClickListener(new View.OnClickListener() {
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
        });*/

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

        Log.e(TAG, "onBindViewHolder: normal posi: " + position);
        Log.e(TAG, "onBindViewHolder: adapter posi: " + position);
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
//        imageDataList.clear();
        imageDataList = streetList;
        notifyDataSetChanged();
    }

    public void filterList(List<HouseDetailsImageDTO> filteredList) {
        imageDataList = filteredList;
        notifyDataSetChanged();
        listItemCount = imageDataList.size();
    }

    interface OnTextClickListener {
//        void onTextClick(listItemCount);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtDateTime, txtRefId, txtEmpName, txtLatitude, txtLongitude;
        private final ImageView imgPhoto, imgShare;
        private final TextView txtImgAccept, txtImgReject;
        private final CardView cardImgAccept, cardImgReject;

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
            cardImgAccept = itemView.findViewById(R.id.card_img_accept);
            cardImgReject = itemView.findViewById(R.id.card_img_reject);
        }
    }

    public interface MyClickListener {
        void onItemClicked(String houseId, Boolean status);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
