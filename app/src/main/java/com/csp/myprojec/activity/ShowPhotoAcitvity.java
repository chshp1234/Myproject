package com.csp.myprojec.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.csp.myprojec.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by CSP on 2016/12/25.
 */

public class ShowPhotoAcitvity extends Activity {
    @BindView(R.id.photo)
    PhotoView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showphoto);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Glide.with(this).load(getIntent().getStringExtra("content"))
                .placeholder(R.mipmap.placeholder)
                .crossFade()
                .into(photo);
        photo.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                onBackPressed();
            }

            @Override
            public void onOutsidePhotoTap() {
                Log.i("OutsidePhotoTap","OutsidePhotoTap");

            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
