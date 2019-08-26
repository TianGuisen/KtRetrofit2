package com.lb.baselib.glide

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Created by Administrator on 2016/12/22.
 */

class GlideOrgImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        Glide.with(context).load(path).apply(commonOptions).into(imageView)
    }

    override fun createImageView(context: Context): ImageView {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.MATRIX
        return imageView
    }
}
