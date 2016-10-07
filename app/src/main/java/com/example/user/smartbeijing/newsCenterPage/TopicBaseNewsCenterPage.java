package com.example.user.smartbeijing.newscenterpage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.user.smartbeijing.activity.MainActivity;

/**
 * Describe :
 * Created by 王兆琦 on 2016/10/4 14:30.
 * Email    : wzq1551159@gmail.com
 */

public class TopicBaseNewsCenterPage extends BaseNewsCenterPage {
    public TopicBaseNewsCenterPage(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public View initView() {
        TextView tv = new TextView(mainActivity);
        tv.setText("专题的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}
