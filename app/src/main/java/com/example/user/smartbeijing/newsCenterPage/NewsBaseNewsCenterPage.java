package com.example.user.smartbeijing.newscenterpage;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.smartbeijing.R;
import com.example.user.smartbeijing.activity.MainActivity;
import com.example.user.smartbeijing.domain.NewsContentData;
import com.example.user.smartbeijing.newtpipager.TPINewsNewsCenterPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe :  新闻中心的第一个左侧菜单的数据展示
 *             使用到了 页签 和 下面的展示数据ViewPager
 *             可以参照ViewPagerIndicatorSample来写
 *
 * Created by 王兆琦 on 2016/10/4 14:29.
 * Email    : wzq1551159@gmail.com
 */

public class NewsBaseNewsCenterPage extends BaseNewsCenterPage {

    //利用注解来findviewbyid
    @ViewInject(R.id.newcenter_vp)
    private ViewPager vp_newcenter ;

    @ViewInject(R.id.newcenter_tpi)
    private TabPageIndicator tpi_newcenter ;

    // 利用注解来完成OnClick 事件
    @OnClick(R.id.newscenter_ib_nextpage)
    public void next(View view){
        //setCurrentItem 方法里面不会让他越界的
        vp_newcenter.setCurrentItem(vp_newcenter.getCurrentItem() + 1);
    }

    //页签的数据
    private List<NewsContentData.NewsData.ViewTagData>  viewTagDatas = new
            ArrayList<NewsContentData.NewsData.ViewTagData>();

    public NewsBaseNewsCenterPage(MainActivity mainActivity,
                                  List<NewsContentData.NewsData.ViewTagData> children) {
        super(mainActivity);

        //得到从新闻中心页面传递过来的数据
        this.viewTagDatas = children;

    }

    // 滑动冲突的问题 ，  采用事件拦截
    @Override
    public void initEvent() {

        //给ViewPager添加页面切换监听事件，当页面位于第一个界面时，可以滑出左侧菜单，否则不可以
        tpi_newcenter.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //监听页面停留的位置
            @Override
            public void onPageSelected(int position) {

                //当界面位于第一个界面可以滑动出左侧菜单
                if(position == 0){
                    mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                }else
                    mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public View initView() {

        //找到view
        View newsCenterRoot = View.inflate(mainActivity, R.layout.newcenterpager_content,null);

        //xutils工具注入主键
        ViewUtils.inject(this,newsCenterRoot);;

        return newsCenterRoot;
    }

    //得到数据的处理啊
    @Override
    public void initData() {
        super.initData();

        //设置数据
        MyAdapter myAdapter = new MyAdapter();

        //数据展示在VIewPager上面
        vp_newcenter.setAdapter(myAdapter);

        // 把 viewPager 和 VIewPagerIndicator 关联起来
        // 这样就变成了我想要的滑板鞋
        tpi_newcenter.setViewPager(vp_newcenter);

    }

    private class   MyAdapter extends PagerAdapter{

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            //移除 view
            container.removeView((View) object);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return viewTagDatas.get(position).title;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //要展示的内容， 先展示个文本，  具体的留给下一个阶段开发
            /*TextView textView = new TextView(mainActivity);
            textView.setText(viewTagDatas.get(position).title);
            textView.setTextSize(25);
            textView.setGravity(Gravity.CENTER);*/

            //现在要根据传入的数据展示具体所需要的内容了
            TPINewsNewsCenterPager tpipager = new TPINewsNewsCenterPager(mainActivity,viewTagDatas.get(position));
            View rootView = tpipager.getRootView();

            //添加界面
            container.addView(rootView);

            //返回view
            return rootView ;
        }

        @Override
        public int getCount() {
            return viewTagDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
