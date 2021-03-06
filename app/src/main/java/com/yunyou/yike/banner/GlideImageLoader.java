package com.yunyou.yike.banner;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.loader.ImageLoader;
import com.yunyou.yike.R;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ((SimpleDraweeView) imageView).setImageURI(Uri.parse((String) path));
    }

    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
    @Override
    public ImageView createImageView(Context context) {
//        使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) LayoutInflater.from(context)
                .inflate(R.layout.simpledraweeview, null);

        return simpleDraweeView;
    }
}
