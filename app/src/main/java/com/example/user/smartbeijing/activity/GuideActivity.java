package com.example.user.smartbeijing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.user.smartbeijing.R;
import com.example.user.smartbeijing.utils.DensityUtil;
import com.example.user.smartbeijing.utils.MyConstans;
import com.example.user.smartbeijing.utils.SpTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe : 用户引导界面， 可以滑动几个界面演示功能
 * Created by 王兆琦 on 2016/10/2 01:49.
 * Email    : wzq1551159@gmail.com
 */
public class GuideActivity extends Activity {

    private ViewPager vp_guides;
    private LinearLayout ll_points;
    private View v_redpoint;
    private Button bt_startexp;
    private List<ImageView> guides;
    private int dispoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        initView();// 初始化界面

        initData();// 初始化数据

        //要在初始化玩数据在进行动作操作
        initEvent();// 初始化组件事件
    }

    private void initEvent() {

        //监听布局完成，触发的结果
        v_redpoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //取消注册，界面改变而发生的回调结果
                v_redpoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                //计算点与点之间的距离， 只有页面布局完成，才可以计算
                dispoint = ll_points.getChildAt(1).getLeft() - ll_points.getChildAt(0).getLeft();

            }
        });

        //按钮的点击事件
        bt_startexp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //只有点击过开始体验按钮， 下一次才会直接进入主界面
                SpTools.setBoolean(getApplicationContext(), MyConstans.ISSETUP, true);
                Intent intent = new Intent();
                intent.setClass(getApplication(), MainActivity.class);
                startActivity(intent);          //启动主界面

                // 关闭自己
                finish();

            }
        });

        // 给 ViewPager添加页面改变的事件
        vp_guides.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //viewPager界面滚动
            // @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                 positionOffset偏移的比例值 ， positionOffsetPixels偏移的像素值

                //计算红点的左边距
                float leftMargin = dispoint * (position + positionOffset);
                // 设置红点的左边距
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v_redpoint.getLayoutParams();
                //因为滑动的是float型， 强转为int会损失精度， 用 Math.round()来四舍五入
                layoutParams.leftMargin = Math.round(leftMargin);

                //重新绘制布局
                v_redpoint.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                //当前viewPager的界面， 当滑动到最后一个的时候，显示button
                if (position == guides.size()-1) {
                    bt_startexp.setVisibility(View.VISIBLE);
                } else {
                    //否则  gone
                    bt_startexp.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * @describe 初始化数据和adapter适配
     * @author 王兆琦
     * @time 2016/10/2 11:00
     */
    private void initData() {

        //把图片的资源文件装起来， 如果多的话， 就用for 装，  不过， 要名字前缀一致
        int[] picters = new int[]{R.drawable.guide_1, R.drawable.guide_2,
                R.drawable.guide_3};

        //定义ViewPager的使用容器
        guides = new ArrayList<ImageView>();

        //把图片装到ViewPager中
        for (int i = 0; i < picters.length; i++) {
            /*
            动态定义ImageView ，并展示出来
            */
            ImageView iv_temp = new ImageView(getApplicationContext());
            iv_temp.setBackgroundResource(picters[i]);

            //把图片加到容器中
            guides.add(iv_temp);


            /*
            下面来实现为点的容器linearlayout动态加载小灰点
            */
            //  声明一个 View  , 设置背景， 添加元素
            View v_graypoint = new View(getApplicationContext());
            v_graypoint.setBackgroundResource(R.drawable.gray_point);

            // 因为要使用dp来满足不同手机的屏幕适配问题， 而动态添加view的时候
            // 是使用的   像素px   要转换单位
            int dip = 10;
            // LayoutParams  是许多组件的内部类，可以用来动态父视图传递配置信息
            // 和在xml文件里边设置是一样的
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    new DensityUtil().dip2px(getApplicationContext(), dip),
                    new DensityUtil().dip2px(getApplicationContext(), dip));

            // 过滤第一个点， 设置间隔大小
            if (i != 0)
                params.leftMargin = 15;// px


            // 把LayoutParams关联到父视图
            v_graypoint.setLayoutParams(params);
            // 把灰色的点添加到linearlayout 中
            ll_points.addView(v_graypoint);

        }

        //创建ViewPager的适配器
        MyAdapter myAdapter = new MyAdapter();
        vp_guides.setAdapter(myAdapter);

    }

    private void initView() {
        //设置主界面
        setContentView(R.layout.activity_guide);

        //滑动page
        vp_guides = (ViewPager) findViewById(R.id.vp_guide_pages);

        //动态添加小灰点的LinearLayout
        ll_points = (LinearLayout) findViewById(R.id.ll_guide_points);

        //小红点
        v_redpoint = findViewById(R.id.v_guide_redpoint);

        //底部button按钮
        bt_startexp = (Button) findViewById(R.id.bt_guide_startexp);
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return guides.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 实例化Item
            // container  ->  ViewPager
            //获取view
            View child = guides.get(position);
            //添加到viewpager
            container.addView(child);
            Log.i("TAG", "instantitem:" + position);  // 预加载下一个
            return child;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //过滤和缓存的作用， 如果当前view 和 object一样就复用
            return view == object;
        }
    }
}
