package com.example.user.smartbeijing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import com.example.user.smartbeijing.R;
import com.example.user.smartbeijing.utils.MyConstans;
import com.example.user.smartbeijing.utils.SpTools;

/**
 * Describe : 智慧北京的Splash界面， 实现了动画效果
 * Created by 王兆琦 on 2016/10/2 00:41.
 * Email    : wzq1551159@gmail.com
 */
public class SplashActivity extends Activity {

    private View iv_mainview;
    private AnimationSet animationSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Android MVC 的三大操作

        initView();         //初始化页面
        initData();         //初始化数据 ， 本次Activity没有初始化的数据
        srartAnimation();   //开始播放动画
        initEvent();        //初始化操作
    }

    /**
     * @describe 开始播放动画  : 旋转， 缩放 ，  渐变
     * @author 王兆琦
     * @time 2016/10/2 1:15
     */
    private void srartAnimation() {
        //false 代表动画中每种动画都采用各自的实现方式， 数学函数
        animationSet = new AnimationSet(false);

        //旋转动画， 锚点
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        //设置动画播放的时间
        rotateAnimation.setDuration(2000);
        //动画播放完毕后， 停留在当前状态
        rotateAnimation.setFillAfter(true);

        //添加到动画集
        animationSet.addAnimation(rotateAnimation);

        // 渐变动画
        AlphaAnimation aa = new AlphaAnimation(0, 1);// 由完全透明到不透明
        // 设置动画播放时间
        aa.setDuration(2000);
        aa.setFillAfter(true);// 动画播放完之后，停留在当前状态

        // 添加到动画集
        animationSet.addAnimation(aa);

        // 缩放动画

        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        // 设置动画播放时间
        sa.setDuration(2000);
        sa.setFillAfter(true);// 动画播放完之后，停留在当前状态

        // 添加到动画集
        animationSet.addAnimation(sa);


        //播放动画
        iv_mainview.startAnimation(animationSet);

        //动画播放完毕后，进入下一个界面， 向导界面
    }

    private void initData() {

    }

    /**
     * @describe 监听动画播完的时间
     * @author 王兆琦
     * @time 2016/10/2 1:29
     */
    private void initEvent() {

        //监听动画播完的事件，
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //之间听结束
                //通过判断有没有点击按钮来判断进入那个界面
                // false为默认值， 当时true的时候进入mainActivity,fouze GuideActivity
                if (SpTools.getBoolean(getApplicationContext(), MyConstans.ISSETUP, false)) {
                    //进入主界面
                    Intent main = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(main);//启动主界面
                } else{
                    Intent intent = new Intent(SplashActivity.this,GuideActivity.class);
                    startActivity(intent);//启动设置向导界面
                }

                // 注意 ！！！   要关闭自己了 ，  释放资源啊！！
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initView() {
        // 设置主界面
        setContentView(R.layout.actiivity_splash);
        // 获取背景图片
        iv_mainview = findViewById(R.id.iv_splash_mainview);
    }
}
