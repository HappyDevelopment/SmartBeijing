package com.example.user.smartbeijing.utils;

import android.content.Context;

/**
 * Describe :  px 与 dp  之间的转换工具类
 * Created by 王兆琦 on 2016/10/2 12:11.
 * Email    : wzq1551159@gmail.com
 */
public class DensityUtil {

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
