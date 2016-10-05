package com.example.user.smartbeijing.newsCenterPage;

import android.view.View;

import com.example.user.smartbeijing.activity.MainActivity;

/**
 * Describe :  新闻中心四个页面的基类
 * Created by 王兆琦 on 2016/10/4 13:11.
 * Email    : wzq1551159@gmail.com
 */
public abstract class BaseNewsCenterPage {
    //子类可以快速的访问maninactivity
    protected MainActivity mainActivity;
    //根布局
    protected View root ;

    //构造函数传入一个Context， 就相当于是view
    public BaseNewsCenterPage(MainActivity mainActivity){
        this.mainActivity = mainActivity ;

        root = initView();

        initEvent();

    }

    /**
     * 子类覆盖此方法完成事件的处理
     */
    public void initEvent() {

    }
    /**
     * 子类覆盖此方法完成数据的处理
     */
    public void initData() {

    }

    /**
     *  子类必须覆盖这个方法来完成view的初始化
     * @return
     */
    public abstract View initView() ;

    /**
     * 快捷工具之：  得到view
     * @return
     */
    public View getRoot(){
        return  root ;
    }



}
