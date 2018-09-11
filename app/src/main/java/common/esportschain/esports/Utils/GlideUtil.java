package common.esportschain.esports.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import common.esportschain.esports.R;

/**
 * @author liangzhaoyou
 * @date 2018/6/5
 */

public class GlideUtil {
    /**
     * 快速加载图片
     *
     * @param mContext   上下文
     * @param path       图片路径
     * @param mImageView 控件
     */
    public static void loadImg(Context mContext, String path, ImageView mImageView) {
        if (!TextUtils.isEmpty(path)) {
            Glide.with(mContext).load(path).into(mImageView);
        } else {
            Glide.with(mContext).load(R.mipmap.icon_default).into(mImageView);
        }
    }

    /**
     * 加载本地图片
     *
     * @param mContext
     * @param path
     * @param mImageView
     */
    public static void loadImg(Context mContext, int path, ImageView mImageView) {
//        if (path != 0) {
//            Glide.with(mContext)
//                    .load(path)
//                    .placeholder(new ColorDrawable(Color.RED))
//                    .into(mImageView);
//        } else {
//            Glide.with(mContext).load(R.mipmap.icon_default).into(mImageView);
//        }
    }

    /**
     * 加载本地图片
     *
     * @param mContext
     * @param path
     * @param mImageView
     */
    public static void loadImg2(Context mContext, int path, ImageView mImageView) {
//        if (path != 0) {
//            Glide.with(mContext).load(path).fitCenter().error(R.mipmap.icon_default).into(mImageView);
//        } else {
//            Glide.with(mContext).load(R.mipmap.icon_default).fitCenter().into(mImageView);
//        }
    }

    /**
     * 指定大小加载图片
     *
     * @param mContext   上下文
     * @param path       图片路径
     * @param width      宽
     * @param height     高
     * @param mImageView 控件
     */
    public static void loadImg(Context mContext, String path, int width, int height, ImageView mImageView) {
//        if (!TextUtils.isEmpty(path)) {
//            Glide.with(mContext).load(path).override(
//                    UIUtil.dip2px(mContext, width),
//                    UIUtil.dip2px(mContext, height))
//                    .centerCrop().into(mImageView);
//        } else {
//            Glide.with(mContext).load(R.mipmap.icon_default).override(
//                    UIUtil.dip2px(mContext, width),
//                    UIUtil.dip2px(mContext, height))
//                    .centerCrop().into(mImageView);
//        }
    }

    /**
     * 指定大小加载图片
     *
     * @param mContext   上下文
     * @param path       图片路径
     * @param width      宽
     * @param height     高
     * @param mImageView 控件
     */
    public static void loadImg(Context mContext, int path, int width, int height, ImageView mImageView) {
//        if (path != 0) {
//            Glide.with(mContext).load(path).override(
//                    UIUtil.dip2px(mContext, width),
//                    UIUtil.dip2px(mContext, height))
//                    .centerCrop().into(mImageView);
//        } else {
//            Glide.with(mContext).load(R.mipmap.icon_default).override(
//                    UIUtil.dip2px(mContext, width),
//                    UIUtil.dip2px(mContext, height))
//                    .centerCrop().into(mImageView);
//        }
    }

    /**
     * 快速加载圆形图片
     *
     * @param mContext   上下文
     * @param path       图片路径
     * @param mImageView 控件
     */
//    public static void loadRandAvatar(Context mContext, String path, ImageView mImageView) {
//
//        if (!TextUtils.isEmpty(path)) {
//
//            RequestOptions requestOptions = new RequestOptions()
//                    .transform(new GlideCircleTransform(mContext))
//                    .error(R.mipmap.icon_default);
//
//
//            Glide.with(mContext).load(path).apply(requestOptions).into(mImageView);
//        } else {
//            RequestOptions requestOptions = new RequestOptions()
//                    .centerCrop()
//                    .transform(new GlideCircleTransform(mContext));
//
//            Glide.with(mContext).load(R.mipmap.icon_default).apply(requestOptions).into(mImageView);
//        }
//    }


    /**
     * 快速加载圆形图片
     *
     * @param mContext   上下文
     * @param path       图片路径
     * @param mImageView 控件
     */
    public static void loadRandImg(Context mContext, String path, ImageView mImageView) {
        if (!TextUtils.isEmpty(path)) {
            RequestOptions requestOptions = new RequestOptions()
                    .transform(new GlideCircleTransform(mContext))
                    .error(R.mipmap.icon_default);

            Glide.with(mContext).load(path).apply(requestOptions).into(mImageView);
        } else {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .transform(new GlideCircleTransform(mContext));

            Glide.with(mContext).load(R.mipmap.icon_default).apply(requestOptions).into(mImageView);
        }
    }

    public static void loadRandImg(Context mContext, int path, ImageView mImageView) {
        if (0 != path) {
            RequestOptions requestOptions = new RequestOptions()
                    .transform(new GlideCircleTransform(mContext))
                    .error(R.mipmap.icon_default);

            Glide.with(mContext).load(path).apply(requestOptions).into(mImageView);
        } else {
            RequestOptions requestOptions = new RequestOptions()
                    .transform(new GlideCircleTransform(mContext));

            Glide.with(mContext).load(R.mipmap.icon_default).apply(requestOptions).into(mImageView);
        }
    }

    /**
     * 快速加载图片
     *
     * @param mContext   上下文
     * @param bitmap     BITMAP
     * @param mImageView 控件
     */
    public static void loadBitmapImg(Context mContext, Bitmap bitmap, ImageView mImageView) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] bytes = baos.toByteArray();
//        Glide.with(mContext).load(bytes).asBitmap().into(mImageView);
    }
}
