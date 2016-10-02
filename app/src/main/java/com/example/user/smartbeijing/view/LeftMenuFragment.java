package com.example.user.smartbeijing.view;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Describe :  左滑菜单的基础fragment
 * Created by 王兆琦 on 2016/10/3 01:11.
 * Email    : wzq1551159@gmail.com
 */

public class LeftMenuFragment extends BaseFragmet {
    @Override
    public View initView() {
        TextView tv = new TextView(mainActivity);
        tv.setText("左侧菜单");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}
