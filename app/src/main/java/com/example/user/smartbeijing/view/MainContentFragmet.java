package com.example.user.smartbeijing.view;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

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

    //把5个界面的BaseTagPage都添加捡来
    List<BaseTagPage> pages = new ArrayList<BaseTagPage>();

    @Override
    public View initView() {
        View root = View.inflate(mainActivity,R.layout.fragment_content_view,null);

        //利用xutils 来动态注入view
        // 换关键的点， 注入的时候(Object handler ，View view)
        ViewUtils.inject(this,root);

        return root;
    }

    @Override
    public void initData() {

        // 首页
        pages.add(new HomeBaseTagPager(mainActivity));
        // 首页
        pages.add(new NewCenterBaseTagPager(mainActivity));
        // 首页
        pages.add(new SmartServiceBaseTagPager(mainActivity));
        // 首页
        pages.add(new GovAffairsBaseTagPager(mainActivity));
        // 首页
        pages.add(new SettingCenterBaseTagPager(mainActivity));

        MyAdapter adapter = new MyAdapter();
        viewPagers.setAdapter(adapter);

    }

    private class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return pages.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            BaseTagPage baseTagPage = pages.get(position);
            View root = baseTagPage.getRoot();
            container.addView(root);
            return root;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            // TODO Auto-generated method stub
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView((View) object);
        }

    }

}
