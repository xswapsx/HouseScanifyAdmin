package com.appynitty.adminapp.activities;

import static android.os.Environment.DIRECTORY_PICTURES;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.utils.TouchImageView;
import com.bumptech.glide.Glide;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ZoomViewActivity extends AppCompatActivity {
    private static final String TAG = "ZoomViewActivity";
    private Context context;
    private float mScaleFactor = 1.0f;
    private ImageView imgDownload, imgShareZ;
    private TouchImageView imagePhoto;
    private String qrImage;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_view);
        init();
    }

    private void init() {
        context = this;
        Intent intent = getIntent();
        qrImage = intent.getStringExtra("qrImage");


        toolbar = findViewById(R.id.toolbarNext);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitle("Save / Share Image");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgDownload = findViewById(R.id.img_download);
        imgShareZ = findViewById(R.id.img_share_zoom);
        imagePhoto = findViewById(R.id.img_photo);

        Glide.with(context)
                .load(qrImage)
                .into(imagePhoto);

        setOnClick();

    }

    private void setOnClick() {
        imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qrImage != null || !qrImage.isEmpty()) {
                    if (isStoragePermissionGranted())
                        SaveImageToSDCard(qrImage);
                } else {
                    DynamicToast.makeError(context, "Invalid image!").show();
                }
            }
        });
        imgShareZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage();
                /*try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "HouseScanify Admin App");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.LIBRARY_PACKAGE_NAME + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    context.startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }*/
            }
        });
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    void SaveImageToSDCard(String Image) {
        try {
            byte[] imageBytes = Base64.decode(Image, Base64.DEFAULT);
            Log.e(TAG, "SaveImageToSDCard: imageBytes: " + imageBytes);
            InputStream is = new ByteArrayInputStream(imageBytes);
//            Bitmap image = BitmapFactory.decodeStream(is);
            Bitmap image = ((BitmapDrawable) imagePhoto.getDrawable()).getBitmap();

            String mBaseFolderPath = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES) + "/HouseScanify/";

            if (!new File(mBaseFolderPath).exists()) {
                new File(mBaseFolderPath).mkdirs();
            }
            String mFilePath = mBaseFolderPath + "/" + System.currentTimeMillis() + ".jpg";

            File file = new File(mFilePath);

            FileOutputStream stream = new FileOutputStream(file);

            if (!file.exists()) {
                file.createNewFile();
            }

            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            is.close();
//            image.recycle();

            stream.flush();
            stream.close();
            DynamicToast.makeSuccess(context, "Saved successfully!").show();

        } catch (Exception e) {
            Log.v("SaveFile", "" + e);
        }
    }

    public void shareImage() {
//        View content = findViewById(R.id.full_image_view);
        /*imagePhoto.setDrawingCacheEnabled(true);

        Bitmap bitmap = imagePhoto.getDrawingCache();
        File root = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES) + "/HouseScanify/");
        File cachePath = new File(root.getAbsolutePath() + "/image.jpg");
        try {
            cachePath.createNewFile();
            FileOutputStream ostream = new FileOutputStream(cachePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
        startActivity(Intent.createChooser(share, "Share via"));
    }*/

    }
}