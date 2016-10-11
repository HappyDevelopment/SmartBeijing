package com.example.user.smartbeijing.newtpipager;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.smartbeijing.R;
import com.example.user.smartbeijing.activity.MainActivity;
import com.example.user.smartbeijing.domain.NewsContentData;
import com.example.user.smartbeijing.domain.TpiNewsData;
import com.example.user.smartbeijing.utils.DensityUtil;
import com.example.user.smartbeijing.utils.MyConstans;
import com.example.user.smartbeijing.utils.SpTools;
import com.example.user.smartbeijing.view.RefreshListView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import static android.R.attr.data;
import static android.R.id.list;

/**
 * Describe :  新闻中心页签对应的界面 ， 其中有 轮播图和 ListView
 * Created by 王兆琦 on 2016/10/6 22:44.
 * Email    : wzq1551159@gmail.com
 */

public class TPINewsNewsCenterPager {
    private View         rootView;
    private MainActivity mainActivity;

    //找到所有组件并添加进来
    @ViewInject(R.id.vp_tpi_news_lunbo_pic)
    private ViewPager vp_lunbo; //轮播图的显示组件

    @ViewInject(R.id.tv_tpi_news_pic_desc)
    private TextView tv_pic_desc;//图片的描述信息

    @ViewInject(R.id.ll_tpi_news_pic_points)
    private LinearLayout ll_points;//轮播图的每张图片对应的点组合

    @ViewInject(R.id.lv_tpi_news_listnews)

    // 基本的listview 不能满足我们的要求， 我们要自定义可以刷新的listview
    private RefreshListView                      lv_listnews;// 显示列表新闻的组件
    private Gson                                 gson;
    private BitmapUtils                          bitmapUtils;
    private NewsContentData.NewsData.ViewTagData viewTagData;

    //新闻列表的数据, 这名字绕来绕去的 。。。。。
    private List<TpiNewsData.TPINewsData_Data.TPINewsData_Data_LunBoData> lunboDatas =
            new ArrayList<TpiNewsData.TPINewsData_Data.TPINewsData_Data_LunBoData>();
    private int          picSelectIndex;
    private LunboAdapter lunboAdapter;
    private LunboTask    lunboTask;

    //新闻列表的数据
    private List<TpiNewsData.TPINewsData_Data.TPINewsData_Data_ListNewsData> listNews = new ArrayList<TpiNewsData.TPINewsData_Data.TPINewsData_Data_ListNewsData>();
    private ListNewsAdapter listNewsAdapter;
    private View            rootView1;
    private View            lunBoPic;
    private boolean isFresh;


    public TPINewsNewsCenterPager(MainActivity mainActivity,
            NewsContentData.NewsData.ViewTagData viewTagData) {

        this.mainActivity = mainActivity;
        this.viewTagData = viewTagData;
        gson = new Gson();
        lunboTask = new LunboTask();
        bitmapUtils = new BitmapUtils(mainActivity);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.ARGB_4444);
        initView();
        initData();
        initEVent();
    }

    private void initEVent() {

        //给轮播图添加页面切换事件
        vp_lunbo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // 目的就是动态的获取当前的位置
                picSelectIndex = position;
                setPicDescAndPointSelect(picSelectIndex);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 给listView添加刷新数据事件
        lv_listnews.setOnRefreshDataListener(new RefreshListView.OnRefreshDataListener() {
            @Override
            public void refreshData() {
                //刷新状态变为正在刷新
                isFresh = true;
                //刷新数据
                getDataFromNet();

                //刷新完， 改变listview的状态
            }
        });
    }

    private void initData() {

        //  轮播图的适配器
        lunboAdapter = new LunboAdapter();

        vp_lunbo.setAdapter(lunboAdapter);

        //   listView的适配器
        listNewsAdapter = new ListNewsAdapter();

        lv_listnews.setAdapter(listNewsAdapter);

        // 1 缓存技术， 从本地获取数据
        String jsonCache = SpTools.getString(mainActivity, viewTagData.url, "");
        if (!TextUtils.isEmpty(jsonCache)) {
            //解析数据
            TpiNewsData newsData = parseJson(jsonCache);
            //处理数据
            ProcessData(newsData);
        }

        // 2 从网络获取数据
        getDataFromNet();
    }

    private void ProcessData(TpiNewsData newsData) {

        //完成数据的处理

        // 1 ，设置轮播的数据
        setLunboData(newsData);

        // 2 ， 轮播图对应点的处理
        initPoints();

        // 3 , 设置图片的描述和点的选中效果
        setPicDescAndPointSelect(picSelectIndex);

        // 4 , 开始轮播图

        lunboTask.startLunbo();
        // 5 ， 新闻列表的数据
        setListViewNews(newsData);

    }

    private void setListViewNews(TpiNewsData newsData) {
        listNews = newsData.data.news;

        //跟新界面
        listNewsAdapter.notifyDataSetChanged();
    }

    // 轮播图任务类 , 使用到了Handler的机制
    private class LunboTask extends android.os.Handler implements Runnable {

        public void stopLunbo() {
            //移除当前所有任务   null 为移除所有
            removeCallbacksAndMessages(null);
        }

        public void startLunbo() {
            //先有移除操作
            stopLunbo();
            postDelayed(this, 2000);
        }

        @Override
        public void run() {
            // 控制了轮播图的显示
            // 循环显示
            vp_lunbo.setCurrentItem((vp_lunbo.getCurrentItem() + 1) % vp_lunbo.getAdapter().getCount());
            postDelayed(this, 2000);
        }
    }

    //初始化点
    private void initPoints() {

        // 要有清空之前点的操作
        ll_points.removeAllViews();

        // 添加点
        for (int i = 0; i < lunboDatas.size(); i++) {
            View v_points = new View(mainActivity);
            //设置点的背景选择器
            v_points.setBackgroundResource(R.drawable.point_selector);
            //默认是灰色
            v_points.setEnabled(false);

            //设置点的大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(mainActivity, 5), DensityUtil.dip2px(mainActivity, 5));
            //设置点的间距
            params.leftMargin = DensityUtil.dip2px(mainActivity, 10);

            //把参数设置为v_points
            v_points.setLayoutParams(params);

            ll_points.addView(v_points);

        }

    }

    private void setLunboData(TpiNewsData newsData) {

        // 把数据装到集合里面
        lunboDatas = newsData.data.topnews;

        // 更新操作很关键
        lunboAdapter.notifyDataSetChanged();

    }

    private class ListNewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listNews.size();
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
        public View getView(int position, android.view.View convertView, ViewGroup parent) {

            //此处利用ViewHolder 来加载数据
            ViewHolder holder = null;
            //缓存技术
            if (convertView == null) {
                convertView = View.inflate(mainActivity, R.layout.tpi_news_listview_item, null);
                holder = new ViewHolder();
                holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_tpi_news_listview_item_icon);
                holder.iv_newspic = (ImageView) convertView.findViewById(R.id.iv_tpi_news_listview_item_pic);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_tpi_news_listview_item_title);
                holder.tv_time = (TextView) convertView.findViewById(R.id.tv_tpi_news_listview_item_time);

                // 给 convertView 添加一个标签 ， 内容是 holder
                convertView.setTag(holder);
            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            //设置数据
            TpiNewsData.TPINewsData_Data.TPINewsData_Data_ListNewsData tpiNewsData_Data_ListNewsData = listNews.get(position);

            //设置标题
            holder.tv_title.setText(tpiNewsData_Data_ListNewsData.title);

            //设置时间
            holder.tv_time.setText(tpiNewsData_Data_ListNewsData.pubdate);

            //设置图片
            bitmapUtils.display(holder.iv_newspic, tpiNewsData_Data_ListNewsData.listimage);

            return convertView;
        }
    }

    //装载组件的holder
    private class ViewHolder {
        ImageView iv_newspic;
        TextView  tv_title;
        TextView  tv_time;
        ImageView iv_icon;
    }

    private TpiNewsData parseJson(String jsonCache) {

        // 利用gson解析数据
        TpiNewsData tpiNewsData = gson.fromJson(jsonCache, TpiNewsData.class);

        return tpiNewsData;
    }


    //页签对应的页面的根布局
    private void initView() {

        lunBoPic = View.inflate(mainActivity, R.layout.tpi_news_lunbopic, null);
        ViewUtils.inject(this, lunBoPic);

        rootView = View.inflate(mainActivity, R.layout.tpi_news_center, null);
        ViewUtils.inject(this, rootView);

        // 把 Viewpager 当做listview中的第一个添加进来， 这样就能一起滑动了

        //把轮播图加到listView中
        lv_listnews.addLunboView(lunBoPic);
    }

    public View getRootView() {
        return rootView;
    }

    private void getDataFromNet() {
        //从网上后去数据
        HttpUtils httpUtils = new HttpUtils();

        httpUtils.send(HttpRequest.HttpMethod.GET, MyConstans.SERVERURL + viewTagData.url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        //请求数据成功
                        String jsonData = responseInfo.result;

                        //保存到本地
                        SpTools.setString(mainActivity, viewTagData.url, jsonData);

                        //解析数据
                        TpiNewsData newsData = parseJson(jsonData);
                        //处理数据
                        ProcessData(newsData);

                        // 在一个判断， 是否是刷新获得的数据
                        if(isFresh){
                            lv_listnews.refreshStateFinish();
                            Toast.makeText(mainActivity, "刷新数据成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                        Toast.makeText(mainActivity, "服务器或网络不可用", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void setPicDescAndPointSelect(int picDescAndPointSelect) {

        //设置描述信息
        tv_pic_desc.setText(lunboDatas.get(picDescAndPointSelect).title);

        //设置点是否选中
        for (int i = 0; i < lunboDatas.size(); i++) {
            ll_points.getChildAt(i).setEnabled(i == picDescAndPointSelect);
        }
    }

    private class LunboAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return lunboDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            // 设置图片
            ImageView iv_lunbo_pic = new ImageView(mainActivity);

            // 如果网路缓慢  设置默认图片
            iv_lunbo_pic.setImageResource(R.drawable.home_scroll_default);

            //给图片添加数据
            TpiNewsData.TPINewsData_Data.TPINewsData_Data_LunBoData lunBoData = lunboDatas.get(position);

            //图片的url
            String topimageurl = lunBoData.topimage;

            //把URL的图片给iv_lunbo_pic
            bitmapUtils.display(iv_lunbo_pic, topimageurl);

            // 给图片添加触摸事件，让用户点击的时候可以查看图片或者查看这个新闻
            iv_lunbo_pic.setOnTouchListener(new View.OnTouchListener() {

                private long downTime;
                private float downY;
                private float downX;

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        //按下停止轮播
                        case MotionEvent.ACTION_DOWN:
                            //记录按下的点坐标
                            downX = event.getX();
                            downY = event.getY();
                            //记录按下的时间
                            downTime = System.currentTimeMillis();
                            //停止轮播
                            lunboTask.stopLunbo();
                            break;

                        // 按下事件取消
                        case MotionEvent.ACTION_CANCEL:
                            lunboTask.startLunbo();
                            break;

                        //点击松开 , 松开与按下的时间间隔来判断用户点击了
                        case MotionEvent.ACTION_UP:
                            //记录位置
                            float upX = event.getX();
                            float upY = event.getY();

                            // 只有松开与按下的位置相同才是用户点击了
                            if (downX == upX && downY == upY) {

                                long uptime = System.currentTimeMillis();
                                //时间间隔小于半秒
                                if (uptime - downTime < 500) {
                                    lunboPicClick();
                                }
                            }
                            //从方法体出来后也要轮播的
                            lunboTask.startLunbo();//开始轮播
                            break;

                        default:
                            break;
                    }
                    return true;
                }

                private void lunboPicClick() {
                    //处理图片的点击事件
                    System.out.println("被点击了，  下一阶段开发处理");

                }
            });


            //添加到视图
            container.addView(iv_lunbo_pic);

            return iv_lunbo_pic;

        }
    }
}
