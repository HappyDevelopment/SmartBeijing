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
    private ListView lv_leftData ;
    //listview 的 adapter
    private MyAdapter adapter;

    //选中的位置， 采用成员变量保存起来
    private int selectPosition ;

    @Override
    public void initEvent() {
        //设置listview的选择事件
        lv_leftData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //保存选中的位置
                selectPosition = position ;

                //更新界面
                adapter.notifyDataSetChanged();


            }
        });

    }

    /**
     * 设置更新数据
     * @param data
     */
    public void setLeftMenuData(List<NewsContentData.NewsData> data){
        this.datas = data ;
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
        return lv_leftData ;

    }

    @Override
    public void initData() {
        //组织数据
        adapter = new MyAdapter();
        lv_leftData.setAdapter(adapter);

    }

    private class MyAdapter extends BaseAdapter{

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
            if(convertView==null){
                tv_currView = (TextView) View.inflate(mainActivity, R.layout.leftmenu_item_list, null);
            }else {
                //convertView是缓存view
                tv_currView = (TextView) convertView;
            }

            //设置标题
            tv_currView.setText(datas.get(position).title);

            //判断是否被选中
            tv_currView.setEnabled(position == selectPosition);

            return tv_currView ;

        }
    }
}
