package com.example.user.smartbeijing.basepage;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.user.smartbeijing.activity.MainActivity;


/**
 * Describe :
 * Created by 王兆琦 on 2016/10/3 01:44.
 * Email    : wzq1551159@gmail.com
 */
public class HomeBaseTagPager extends BaseTagPage {
    public HomeBaseTagPager(MainActivity context) {
        super(context);
    }
    @Override
    public void initData() {
        //屏蔽菜单按钮
        ib_menu.setVisibility(View.GONE);

        // 设置本page的标题
        tv_title.setText("主页");

        // 要展示的内容，
        TextView tv = new TextView(mainActivity);
        tv.setText("主页的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);

        // 替换掉白纸
        fl_content.addView(tv);// 添加自己的内容到白纸上
        super.initData();
    }
}
