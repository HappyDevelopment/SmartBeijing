package com.example.user.smartbeijing.view;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.user.smartbeijing.R;
import com.example.user.smartbeijing.basepage.BaseTagPage;
import com.example.user.smartbeijing.basepage.GovAffairsBaseTagPager;
import com.example.user.smartbeijing.basepage.HomeBaseTagPager;
import com.example.user.smartbeijing.basepage.NewCenterBaseTagPager;
import com.example.user.smartbeijing.basepage.SettingCenterBaseTagPager;
import com.example.user.smartbeijing.basepage.SmartServiceBaseTagPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Describe :  主页面的Fragment
 * Created by 王兆琦 on 2016/10/3 01:14.
 * Email    : wzq1551159@gmail.com
 */

public class MainContentFragmet extends BaseFragmet {

    //利用xutils 的 viewutils 利用注解来实例化视图
    @ViewInject(R.id.vp_main_content_pages)
    private ViewPager viewPagers;

    @ViewInject(R.id.rg_content_radios)
    private RadioGroup rg_radios;

    //把5个界面的BaseTagPage都添加进来
    List<BaseTagPage> pages = new ArrayList<BaseTagPage>();

    //设置当前界面的编号
    private int selectIndex = 0;

    /**
     * 设置点击事件， 最终是在 OnActivityCreate()中被调用完成实例化
     */
    @Override
    public void initEvent() {
        rg_radios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main_content_home:// 主界面
                        selectIndex = 0;
                        break;
                    case R.id.rb_main_content_newscenter:// 新闻中心界面的切换
                        selectIndex = 1;
                        break;
                    case R.id.rb_main_content_smartservice:// 智慧服务面
                        selectIndex = 2;
                        break;
                    case R.id.rb_main_content_govaffairs:// 政务界面
                        selectIndex = 3;
                        break;
                    case R.id.rb_main_content_settingcenter:// 设置中心界面
                        selectIndex = 4;
                        break;

                    default:
                        break;
                }
                //在做处理，设置123能被点出来
                switchPage();
            }
        });
        //还可以调用父类的initView()来看父类做了什么 ， 继承 框架
        super.initEvent();
    }

    /**
     * 左侧菜单点击， 让主页面切换不同的界面
     *
     */
    public void leftMenuClickSwitchPage(int subselectPosition) {
        BaseTagPage baseTagPage = pages.get(selectIndex);
        baseTagPage.switchPage(subselectPosition);
    }

    /**
     * 设置选中的界面
     */
    protected void switchPage() {
        //设置viewPager的显示面
        viewPagers.setCurrentItem(selectIndex);

        //判断界面是不是123
        if (selectIndex == 0 | selectIndex == pages.size() - 1) {
            //不让左侧菜单话出来

            mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        } else {
            mainActivity.getSlidingMenu().setTouchModeAbove((SlidingMenu.TOUCHMODE_FULLSCREEN));
        }
    }

    @Override
    public View initView() {
        View root = View.inflate(mainActivity, R.layout.fragment_content_view, null);

        //利用xutils 来动态注入view
        // 换关键的点， 注入的时候(Object handler ，View view) handler == context
        ViewUtils.inject(this, root);

        return root;
    }

    /**
     * 向主界面的Fragment添加进去各个viewPager的TagPager ,
     * 传入Context  ， Context是MainActivity
     *
     * 在onActivityCreate()中被调用完成初始化/实例化
     */
    @Override
    public void initData() {

        // 首页
        pages.add(new HomeBaseTagPager(mainActivity));
        // 新闻中心
        pages.add(new NewCenterBaseTagPager(mainActivity));
        // 智慧服务
        pages.add(new SmartServiceBaseTagPager(mainActivity));
        // 政务
        pages.add(new GovAffairsBaseTagPager(mainActivity));
        // 设置中心
        pages.add(new SettingCenterBaseTagPager(mainActivity));

        MyAdapter adapter = new MyAdapter();
        viewPagers.setAdapter(adapter);

        //设置默认选择首页
        switchPage();
        //设置第一个按钮被选中(首页)
        rg_radios.check(R.id.rb_main_content_home);


    }



    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //要展示的内容
            BaseTagPage baseTagPage = pages.get(position);
            //得到  5 个 页面的view
            View root = baseTagPage.getRoot();
            // 添加到 ViewGroup (ViewPager)中
            container.addView(root);

            // 发现有错误， 一直加载不了数据， 原因是这里的adapter没有加载数据，
            //  以为是BaseTagPage中没有加载数据
            // 之前这是demo， 所以没有注意到这个事情，  现在要改， 改变数据适配啊
            //加载数据库 ，
            baseTagPage.initData();

            //把 view返回
            return root;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

}
