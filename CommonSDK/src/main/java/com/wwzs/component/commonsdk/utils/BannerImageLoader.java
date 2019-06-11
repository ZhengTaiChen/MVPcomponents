package com.wwzs.component.commonsdk.utils;

import android.content.Context;
import android.widget.ImageView;

import com.wwzs.component.commonsdk.http.imageloader.glide.ImageConfigImpl;
import com.youth.banner.loader.ImageLoader;


public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        ArmsUtils.obtainAppComponentFromContext(context).imageLoader().loadImage(context, ImageConfigImpl
                .builder()
//                .fallback(R.drawable.gallery_default_image)
                .url(path.toString())
                .isCrossFade(true)
                .imageView(imageView)
                .build());
    }
}
