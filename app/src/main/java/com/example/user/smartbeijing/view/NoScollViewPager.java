package com.example.user.smartbeijing.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Describe :  为了不和左滑菜单滑动冲突，所以只能点击按钮来跳转界面
 * Created by 王兆琦 on 2016/10/4 00:08.
 * Email    : wzq1551159@gmail.com
 */

public class NoScollViewPager extends ViewPager {
    public NoScollViewPager(Context context) {
        super(context);
    }

    public NoScollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 屏蔽拦截与触摸事件  ， 不让滑动， 所以主界面不是viewPager布局，而是他
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
