package com.jiangpingwei.cowpea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by jiangpingwei on 2017/3/10.
 */

public class PhotoActivity extends AppCompatActivity {
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

    private String imageUrl;
    private PhotoViewAttacher mPhotoViewAttacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        imageUrl = getIntent().getStringExtra("IMAGEURL");
        Glide.with(this).load(imageUrl).into(ivPhoto);
        setupPhotoAttacher();
    }

    private void setupPhotoAttacher() {
        mPhotoViewAttacher = new PhotoViewAttacher(ivPhoto);
    }

}
