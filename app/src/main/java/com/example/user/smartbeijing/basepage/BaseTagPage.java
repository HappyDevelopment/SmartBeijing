package com.example.user.smartbeijing.basepage;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.user.smartbeijing.R;
import com.example.user.smartbeijing.activity.MainActivity;


/**
 *  BaseTagPage定义了ViewPager界面的基本动作， 是抽取出来的基类
 *  	使用时，需要传入Context来完成View实例化
 */
public class BaseTagPage
{
	protected MainActivity mainActivity;
	protected View	root;
	protected ImageButton	ib_menu;//按钮ib
	protected TextView	tv_title;
	protected FrameLayout	fl_content;
	public BaseTagPage(MainActivity context){
		this.mainActivity = context;
		initView();//初始化布局
		initEvent();
	}

	public  void initView() {
		//界面的根布局
		root = View.inflate(mainActivity, R.layout.fragment_content_base_content, null);
		
		ib_menu = (ImageButton) root.findViewById(R.id.ib_base_content_menu);
		
		tv_title = (TextView) root.findViewById(R.id.tv_base_content_title);
		
		fl_content = (FrameLayout) root.findViewById(R.id.fl_base_content_tag);
	}

	/**
	 * 基本动作之， 点击左侧菜单之后菜单关闭
	 */
	public void initEvent(){
		ib_menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//打开或关闭左侧菜单
				mainActivity.getSlidingMenu().toggle();
			}
		});
	}

	/**
	 * 此方法在页面数据显示的时候在调用
	 *
	 * 昨天的错误， 因为我在BaseTagPage的构造函数中使用了 InitData()方法，
	 * 				所以类一加载就初始化数据，    naive
	 * 			 网络数据是很耗时的啊， 不能这么做啊 ，  兄弟啊 ， 要知道自己在干什么啊
	 */
	public void initData(){
		
	}
	
	
	public View getRoot(){
		return root;
	}

	public void switchPage(int subselectPosition) {


	}
}
