package com.appynitty.adminapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.BuildConfig;

import com.appynitty.adminapp.R;
public class ZoomViewActivity extends AppCompatActivity {

    private Context context;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ImageView imgDownload, imgShareZ, imagePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_view);
        init();
    }

    private void init(){
        context = this;
        imgDownload = findViewById(R.id.img_download);
        imgShareZ = findViewById(R.id.img_share_zoom);
        imagePhoto = findViewById(R.id.img_photo);
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        setOnClick();
    }

    private void setOnClick(){
        imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imgShareZ.setOnClickListener(new View.OnClickListener() {
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
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            imagePhoto.setScaleX(mScaleFactor);
            imagePhoto.setScaleY(mScaleFactor);
            return true;
        }
    }
}