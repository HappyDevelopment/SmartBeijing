package com.example.user.smartbeijing.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.smartbeijing.activity.MainActivity;

/**
 * Describe :  把左滑菜单和viewPager主界面抽取出来的BaseFragmet
 * Created by 王兆琦 on 2016/10/3 00:55.
 * Email    : wzq1551159@gmail.com
 */

public abstract class BaseFragmet extends Fragment {

    //把上下文写在BaseFragment中，是protected属性， 只用子类可以访问，方便
    protected MainActivity mainActivity;
    //明白getActiivty()的使用误区， 只有类创建实例化好，才可以调用，
    // 要不发生NullPointerException, java的实例化基础，要有一个清醒的逻辑意识，不要只看结果猜数据

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 只用 Activity onCreate()后， 才可以获取Fragment所在的Activity
        mainActivity = (MainActivity) getActivity();
    }

    /**
     *  initView() 在 Fragment 的onCreateVIew中完成初始化
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Fragment 生命周期的第三步，下一步是onActivityCreated
        //次步实例化view
        View root = initView();
        return  root;
    }

    /**
     * 必须覆盖此案方法来完成界面的显示
     * @return
     */
    public abstract View initView() ;

    /**
     * 可选覆盖方法
     */
    public void initData(){}

    /**
     * 可选覆盖方法
     */
    public void initEvent(){}


    /**
     * 秒！！！！！！
     * 如果有子类实现 initData（）或 initEvent()的话，会在onActivityCreate中调用初始化数据和事件,
     * 解决了让子类继承Fragment 在 onActivityCreated（）重写方法中写 initData（）或 initEvent()
     *
     * 框架的好处啊！！！！！
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initEvent();
    }


}
