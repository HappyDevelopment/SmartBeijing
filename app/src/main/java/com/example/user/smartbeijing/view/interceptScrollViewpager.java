package com.example.user.smartbeijing.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import static android.R.transition.move;

/**
 * Describe : 滑动冲突的解决办法， 事件拦截
 *            这就需要来重写VIewPager 自定义view了
 * Created by 王兆琦 on 2016/10/7 11:31.
 * Email    : wzq1551159@gmail.com
 */

public class interceptScrollViewpager extends ViewPager{

    private float downX;
    private float downY;
    private float moveX;
    private float moveY;

    public interceptScrollViewpager(Context context) {
        super(context);
    }

    public interceptScrollViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 这是事件分发的方法， 在这里进行拦截
    // true  申请父控件不拦截我的touch事件
    // false  默认父类先拦截事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        //需要 事件完全有自己来处理

        // 1 ， 如果在第一个界面并且是从左往右，父控件拦截， 就是会滑出左侧菜单
        // 2 ， 如果在本页签的最后一个界面并且是从右往左，父控件拦截，
        // 3 ,  否则就是自己处理事件， 不让他可能滑动出左侧菜单

        switch (ev.getAction()){

            //按下的时候，viewPager不会滑动
            case MotionEvent.ACTION_DOWN:    //按下操作
                //不让拦截
                getParent().requestDisallowInterceptTouchEvent(true);
                //记录下按下时点的位置坐标
                downX = ev.getX();
                downY = ev.getY();
                break;

            //在移动里面进行判断
            case MotionEvent.ACTION_MOVE:
                //记录移动时的位置坐标
                moveX = ev.getX();
                moveY = ev.getY();

                //计算两者的差距来判断用户是什么操作事件
                float  dx = moveX - downX ;
                float  dy = moveY - downY ;

                //x>y 横向移动
                if(Math.abs(dx) > Math.abs(dy)){
                    // 如果在第一个界面并且是从左往右，父控件拦截， 就是会滑出左侧菜单
                    if (getCurrentItem() == 0 && dx >0){
                        getParent().requestDisallowInterceptTouchEvent(false);

                        //如果在本页签的最后一个界面并且是从右往左，父控件拦截，
                    }else if (getCurrentItem() == getAdapter().getCount() - 1  && dx< 0){
                        getParent().requestDisallowInterceptTouchEvent(false);

                        //其他情况都不要拦截我
                    }else
                        getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    //让父控件拦截我，  让我滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;

            //其他情况不处理了
            default:
                break;
        }

        //返回父类处理结果
        return super.dispatchTouchEvent(ev);
    }
}
