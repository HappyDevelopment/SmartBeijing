package com.example.user.smartbeijing.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Describe : SharedPreferences 的工具类， 来判断有没有是否点击过按钮
 * Created by 王兆琦 on 2016/10/2 01:33.
 * Email    : wzq1551159@gmail.com
 */
public class SpTools {

    /**
     * 设定值
     * @param Context
     * @param key
     * @param value     设定值
     */
    public static void setBoolean(Context Context, String key , boolean value){

        //此处如果把 SharedPreferences 申明在外面， 那么里面调用就得是静态的SharedPreferences，
        // 不推荐这么做， 会有内存泄漏
        SharedPreferences sharedPreferences = Context.getSharedPreferences(MyConstans.CONFIGFILE, android.content.Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key,value).commit();    //提交保存的键值对
    }

    /**
     * 得到值
     * @param context
     * @param key       键
     * @param defValue  默认值
     * @return
     */
    public static boolean getBoolean(Context context,String key,boolean defValue){
        SharedPreferences sp = context.getSharedPreferences(MyConstans.CONFIGFILE, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * @param key
     *        关键字
     * @param value
     *       对应的值
     */
    public static void setString(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences(MyConstans.CONFIGFILE, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();//提交保存键值对

    }

    /**
     * @param context
     * @param key
     *        关键字
     * @param defValue
     *        设置的默认值
     * @return
     */
    public static String getString(Context context,String key,String defValue){
        SharedPreferences sp = context.getSharedPreferences(MyConstans.CONFIGFILE, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }
}
