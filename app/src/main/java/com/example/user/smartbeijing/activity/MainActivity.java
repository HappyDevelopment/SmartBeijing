package com.example.user.smartbeijing.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.user.smartbeijing.R;
import com.example.user.smartbeijing.view.LeftMenuFragment;
import com.example.user.smartbeijing.view.MainContentFragmet;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


/**
 * Describe : 这是智慧北京的主界面Activity
 *            这个界面是 侧滑菜单  Fragment  RadioGroup
 *            把主界面的共性抽取出来建立
 *              一个基本的Fragment模板，
 *                      主界面 和 左滑菜单继承自他
 *                      展示界面的时候是建立一个Fragment帧布局，利用fragment.replace()来替换
 *              一个基本的标题菜单的BaseTagPage模板
 *                      5个viewPager继承自他
 *            要建立一个基本模板，viewPager 和  左滑菜单 ， 然后用放好模块fragment的替换
 * Created by 王兆琦 on 2016/10/2 00:17.
 * Email    : wzq1551159@gmail.com
 */
public class MainActivity extends SlidingFragmentActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        initData();
    }

    /**
     * 初始化数据， 完成fragment的替换
     */
    private void initData() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        //获取事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //开始替换
        transaction.replace(R.id.fl_left_menu,new LeftMenuFragment());
        transaction.replace(R.id.fl_main_menu,new MainContentFragmet());

        //提交事务
        transaction.commit();

    }

    private void initView() {

        //设置主界面
        setContentView(R.layout.fragment_content_tag);

        //设置左侧菜单界面
        setBehindContentView(R.layout.fragment_left);

        //设置滑动模式
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT);      //只有左侧可以滑

        //设置滑动位置为全屏
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        //设置滑动出去后剩余空间的大小
        slidingMenu.setBehindOffset(200);
    }
}