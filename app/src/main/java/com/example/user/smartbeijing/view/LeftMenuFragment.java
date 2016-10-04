package com.example.user.smartbeijing.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.smartbeijing.R;
import com.example.user.smartbeijing.domain.NewsContentData;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.id.list;

/**
 * Describe :  左滑菜单的基础fragment
 * Created by 王兆琦 on 2016/10/3 01:11.
 * Email    : wzq1551159@gmail.com
 */

public class LeftMenuFragment extends BaseFragmet {

    //新闻中心左侧菜单数据
    private List<NewsContentData.NewsData> datas = new ArrayList<>();
    //左侧菜单是用listview展示的
    private ListView lv_leftData;
    //listview 的 adapter
    private MyAdapter adapter;

    //选中的位置， 采用成员变量保存起来
    private int selectPosition;

    /**
     * 采用接口技术来完成点击跳转事件
     */
    public interface OnSwitchPageListener {
        void switchPage(int selectionIndex);
    }

    //小王含有小李的引用  ，
    private OnSwitchPageListener switchPageListener;

    /**
     * 设置监听回调接口
     *
     * @param listener
     */
    public void setOnSwitchPageListener(OnSwitchPageListener listener) {
        this.switchPageListener = listener;
    }

    @Override
    public void initEvent() {
        //设置listview的选择事件
        lv_leftData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //保存选中的位置
                selectPosition = position;

                //更新界面
                adapter.notifyDataSetChanged();

/**
 * 现在的需求是： 点击 123 page 的界面的左侧菜单对应不同的显示数据
 *      涉及到了LeftMenuFragment跳转到MainContentFragment下面的123page中的子page中
 *
 * 解决方案 1 ：要新建方法可以让左侧菜单点击切换不同的页面
 *               现在是在左侧菜单点击事件里面，我要点击，然后跳转到对应界面
 *               BaseTagPage有个switchPage（index）方法， 子类覆盖了他
 *               我在这里找到MainFragment调用里面的方法，此方法里面调用BaseTagPage有个switchPage（subIndex）方法
 *               根据index来区别该显示那个界面
 *
 *               此方法当时我一直没弄明白此中的关系，原来是子类覆盖了这个方法啊
 *               其实用到了多态加继承
 *
 * 解决方案 2 ：利用接口， 接口是处理复杂问题有效的手段
 *              接口回调技术， 也是如此
 *              和方案一相比有优点 ，  好用啊
 *
 *
 */
// 方案一：              mainActivity.getMainMenuFragment().leftMenuClickSwitchPage(selectPosition);

                //方案二   接口回调    nice
                    switchPageListener.switchPage(selectPosition);


                    mainActivity.getSlidingMenu().toggle();
            }
        });

    }

    /**
     * NewCenterBaseTagePager传递过来数据 ， 这里设置更新数据
     *
     * @param data
     */
    public void setLeftMenuData(List<NewsContentData.NewsData> data) {
        this.datas = data;
        //通知adapter更新数据
        adapter.notifyDataSetChanged();
    }


    @Override
    public View initView() {
        //初始化左侧菜单的一些数据
        lv_leftData = new ListView(mainActivity);
        //背景是黑色的
        lv_leftData.setBackgroundColor(Color.BLACK);
        //选中拖动的背景色 设置成透明
        lv_leftData.setCacheColorHint(Color.TRANSPARENT);

        //设置选中时为透明背景
        lv_leftData.setSelector(new ColorDrawable(Color.TRANSPARENT));

        //没有分割线
        lv_leftData.setDividerHeight(0);

        //距顶部为45px
        lv_leftData.setPadding(0, 45, 0, 0);

        //Fragment的createView() 要把创建好的view返回
        return lv_leftData;

    }

    @Override
    public void initData() {
        //组织数据
        adapter = new MyAdapter();
        lv_leftData.setAdapter(adapter);

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv_currView;
            //显示数据, listview的缓存技术
            if (convertView == null) {
                tv_currView = (TextView) View.inflate(mainActivity, R.layout.leftmenu_item_list, null);
            } else {
                //convertView是缓存view
                tv_currView = (TextView) convertView;
            }

            //设置标题
            tv_currView.setText(datas.get(position).title);

            //判断是否被选中
            tv_currView.setEnabled(position == selectPosition);

            return tv_currView;

        }
    }
}
