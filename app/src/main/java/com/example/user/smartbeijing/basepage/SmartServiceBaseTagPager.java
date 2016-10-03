package com.example.user.smartbeijing.basepage;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.example.user.smartbeijing.activity.MainActivity;

/**
 * Describe :
 * Created by 王兆琦 on 2016/10/3 01:45.
 * Email    : wzq1551159@gmail.com
 */
public class SmartServiceBaseTagPager extends BaseTagPage {

    public SmartServiceBaseTagPager(MainActivity context) {
        super(context);
    }
    @Override
    public void initData() {
        //设置当前page的标题头
        tv_title.setText("智慧服务");

        //当前page要展示的内容
        TextView tv = new TextView(mainActivity);
        tv.setText("智慧服务的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);

        //添加到白纸中
        fl_content.addView(tv);
        super.initData();
    }

}