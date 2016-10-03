package com.example.user.smartbeijing.basepage;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;


/**
 * Describe :
 * Created by 王兆琦 on 2016/10/3 01:45.
 * Email    : wzq1551159@gmail.com
 */
public class GovAffairsBaseTagPager extends BaseTagPage {
    public GovAffairsBaseTagPager(Context context) {
        super(context);
    }
    @Override
    public void initData() {
        tv_title.setText("政务");

        TextView tv = new TextView(context);
        tv.setText("政务的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        super.initData();
    }
}
