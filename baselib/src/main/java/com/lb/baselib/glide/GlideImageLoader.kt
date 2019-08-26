package com.lb.baselib.glide

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lb.baselib.R
import com.youth.banner.loader.ImageLoader

/**
 * Created by Administrator on 2016/12/22.
 */

class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        Glide.with(context).load(path).apply(commonOptions).into(imageView)
    }
}

class LifeHallImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        Glide.with(context).load(path).apply(roundedOptions(20).centerCrop()
                .error(R.drawable.iv_image_load_error)
                .placeholder(R.drawable.iv_image_loading)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL)).into(imageView)
    }
}
