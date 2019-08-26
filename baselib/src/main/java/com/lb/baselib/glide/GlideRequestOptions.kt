package com.lb.baselib.glide

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.lb.baselib.R

/**
 * @author 田桂森 2019/5/20
 */
/**
 * 普通的
 */
val commonOptions = RequestOptions()
        .centerCrop()
        .error(R.drawable.iv_image_load_error)
        .placeholder(R.drawable.iv_image_loading)
        .priority(Priority.HIGH)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
/**
 * 圆形
 */
val circleOptions = RequestOptions()
        .centerCrop()
        .error(R.drawable.iv_image_load_error)
        .transform(GlideCircleTransform())
        .priority(Priority.HIGH)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

val bigoptions = RequestOptions()
        .centerCrop()
        .format(DecodeFormat.PREFER_ARGB_8888)
        .error(R.drawable.iv_empty)
        .placeholder(R.drawable.iv_image_loading)
        .priority(Priority.HIGH)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

/**
 * 圆角
 */
fun roundedOptions(radius: Int) = RequestOptions.bitmapTransform(RoundedCorners(radius))