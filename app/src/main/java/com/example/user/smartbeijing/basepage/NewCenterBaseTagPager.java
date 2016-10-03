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
public class NewCenterBaseTagPager extends BaseTagPage {
    public NewCenterBaseTagPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        tv_title.setText("新闻中心");

        TextView tv = new TextView(context);
        tv.setText("新闻中心的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        super.initData();
    }

}