package com.example.user.smartbeijing.basepage;


import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.user.smartbeijing.activity.MainActivity;
import com.example.user.smartbeijing.domain.NewsContentData;
import com.example.user.smartbeijing.newscenterpage.BaseNewsCenterPage;
import com.example.user.smartbeijing.newscenterpage.InteractBaseNewsCenterPage;
import com.example.user.smartbeijing.newscenterpage.NewsBaseNewsCenterPage;
import com.example.user.smartbeijing.newscenterpage.PhotosBaseNewsCenterPage;
import com.example.user.smartbeijing.newscenterpage.TopicBaseNewsCenterPage;
import com.example.user.smartbeijing.utils.MyConstans;
import com.example.user.smartbeijing.utils.SpTools;
import com.example.user.smartbeijing.view.LeftMenuFragment;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


/**
 * Describe :  新闻中心的页面的开发
 * Created by 王兆琦 on 2016/10/3 01:45.
 * Email    : wzq1551159@gmail.com
 */
public class NewCenterBaseTagPager extends BaseTagPage {

    //新闻中心有四个界面,用list容器存起来
    private List<BaseNewsCenterPage> newsCenterPages = new ArrayList<BaseNewsCenterPage>();
    // 要展示的数据bean
    private NewsContentData newsContentData;
    private Gson gson;

    public NewCenterBaseTagPager(MainActivity context) {
        super(context);
    }

    /**
     * 从网路上获取数据  哦吼
     * 使用开源框架 xutils
     */
    @Override
    public void initData() {

        // 0 添加本地数据缓冲的方法
        //最后一个值为空也行， null 也行 ， 只是得到value
        String jsonCache = SpTools.getString(mainActivity, MyConstans.NEWSCENTERURL, "");

        if (! TextUtils.isEmpty(jsonCache)) {
            //从本地获取数据
            parseData(jsonCache);
        }


        //1 , 从网络上获取数据
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, MyConstans.NEWSCENTERURL,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // 数据访问成功
                        String jsonData = responseInfo.result;
                        // 解析json数据
                        parseData(jsonData);

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println(error);
                        Toast.makeText(mainActivity, "访问服务器失败，请检查网络后重试", Toast.LENGTH_LONG).show();
                    }
                });
        // 看看父类干了什么
        super.initData();
    }

    /**
     * 解析json 数据  ， 传递到 LeftMenuFragment数据 ， 根据为不同显示不同的界面
     *
     * 本地的或者网络上的数据都调用这个方法，  没事 ， 会刷新到最新
     *      他这个逻辑不好
     *
     * @param jsonData 从网络上后去的json数据  或者是 本地的数据
     */
    private void parseData(String jsonData) {

        // 把gson 变为成员变量，防止过多的实例化
        if( gson == null){
            gson = new Gson();
        }

        //开始解析 , 得到解析后的对象
        newsContentData = gson.fromJson(jsonData, NewsContentData.class);

        // 3  数据的处理
        //在这里个左侧菜单设置数据, 把数据对象传递过去， 再去做处理
        mainActivity.getLeftMenuFragment().setLeftMenuData(newsContentData.data);

        //接口回调的处理方案
        mainActivity.getLeftMenuFragment().setOnSwitchPageListener(new LeftMenuFragment.OnSwitchPageListener() {
            @Override
            public void switchPage(int selectionIndex) {
                Log.i("OnSwitchPageListener","接口实现SwitchPage");
                NewCenterBaseTagPager.this.switchPage(selectionIndex);
            }
        });

        // 根据读取到的数据中type不同， 来显示不同的界面
        for (NewsContentData.NewsData data : newsContentData.data) {

            /*
              在这里声明四个界面的基类 ，企图遍历添加他们， 这样对吗？
System.out: com.example.user.smartbeijing.newsCenterPage.NewsBaseNewsCenterPage@233d4ff0------0
System.out: com.example.user.smartbeijing.newsCenterPage.TopicBaseNewsCenterPage@33165969--------1
System.out: com.example.user.smartbeijing.newsCenterPage.PhotosBaseNewsCenterPage@1ff83fee-----------2
System.out: com.example.user.smartbeijing.newsCenterPage.InteractBaseNewsCenterPage@2041ec8f------------3

            从打印结果上来看，每个 case 生成了不同的 baseNewsCenterPage，
                    此处是我迷惑了， baseNewsCenterPage是他们的基类， 多态啊，
                    父类 = 子类对象，  我迷糊了

               */

            //定义基类框架的好处
            BaseNewsCenterPage baseNewsCenterPage = null;

            switch (data.type) {
                case 1:// 新闻页面 ，把数据传给它的构造函数
                    baseNewsCenterPage = new NewsBaseNewsCenterPage(mainActivity, newsContentData.data.get(0).children);
                    //最好还是用Log来打印信息
                    break;
                case 10:// 专题页面
                    baseNewsCenterPage = new TopicBaseNewsCenterPage(mainActivity);
                    break;
                case 2:// 组图页面
                    baseNewsCenterPage = new PhotosBaseNewsCenterPage(mainActivity);
                    System.out.println();
                    break;
                case 3:// 互动页面
                    baseNewsCenterPage = new InteractBaseNewsCenterPage(mainActivity);
                    System.out.println();
                    break;

                default:
                    break;
            }

            // 把四个界面添加到容器中 , 会添加4 个吗？！  恩恩
            newsCenterPages.add(baseNewsCenterPage);
        }

        //在这里控制四个界面的显示，  默认选择第一个界面
        switchPage(0);

    }

    /**
     * 根据位置， 动态显示新闻中心的界面
     *
     * @param position
     */
    public void switchPage(int position) {

        //得到界面
        BaseNewsCenterPage baseNewsCenterPage = newsCenterPages.get(position);

        //设置本界面内容
        tv_title.setText(newsContentData.data.get(position).title);

        //移动掉原来画的内容
        fl_content.removeAllViews();

        //初始化数据
        // 这一步很关键， 数据的初始化都是在这里被调用的

// ！！！要明白这一步是做什么的， 时刻有个清醒的头脑 ， 为什么要有这一步

        baseNewsCenterPage.initData();

        //显示到 FragmentLayout中
        fl_content.addView(baseNewsCenterPage.getRoot());
    }

}