package com.hongshi.hongshiandroid.fragment;

import java.util.List;

import com.hongshi.hongshiandroid.VideoActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.hongshi.hongshiandroid.view.NoScrollListView;
import com.hongshi.hongshiandroid.view.refresh.PullToRefreshBase;
import com.hongshi.hongshiandroid.view.refresh.PullToRefreshBase.OnRefreshListener;
import com.hongshi.hongshiandroid.view.refresh.PullToRefreshScrollView;
import com.hongshi.hongshiandroid.MainActivity;
import com.hongshi.hongshiandroid.R;
import com.hongshi.hongshiandroid.adapter.CommodityAdapter;
import com.hongshi.hongshiandroid.base.BaseFragment;
import com.hongshi.hongshiandroid.model.Commodity;
import com.hongshi.hongshiandroid.view.TitleBarView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

/**
 * 速递页面
 * 
 * @name ExpressFragment
 * @author zhaoqingyang
 * @date 2015年9月16日
 * @modify
 * @modifyDate 2015年9月16日
 * @modifyContent
 */
public class HomeFragment extends BaseFragment {

	// fragment保存数据的key
	private static final String KEY_CONTENT = "HomeFragment:Content";

	private MainActivity activity;
	
	// 公共title
	@ViewInject(R.id.home_titleBar)
	private TitleBarView titleBarView;

	@ViewInject(R.id.home_list_demo)
	private NoScrollListView homeListDemo;
	
	@ViewInject(R.id.button_demo)
	private Button buttomDemo;

	private ScrollView scrollView;

	private PullToRefreshScrollView mPullScrollView;
	
	private List<Commodity> commodityList;
	
	private CommodityAdapter commodityAdapter;

	public static HomeFragment newInstance() {
		HomeFragment fragment = new HomeFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		activity = (MainActivity) getActivity();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		
		mPullScrollView = (PullToRefreshScrollView) rootView.findViewById(R.id.home_scroll);

		mPullScrollView.setPullLoadEnabled(false);
		mPullScrollView.setScrollLoadEnabled(true);

		scrollView = mPullScrollView.getRefreshableView();

		scrollView.addView(inflater.inflate(R.layout.fragment_home_child, null));




		scrollView.setVerticalScrollBarEnabled(false);

		// 注入view和事件
		ViewUtils.inject(this, rootView);

		initView();

		mPullScrollView.doPullRefreshing(true, 500);

		initEvent();
		return rootView;
		
	}

	/**
	 * 初始化视图
	 * 
	 * @title initViews
	 * @author liuchengbao
	 */
	private void initView() {
		titleBarView.setTitleText("顶石珠宝");
		commodityAdapter = new CommodityAdapter(activity, application, handler);
		homeListDemo.setAdapter(commodityAdapter);
	}

	/**
	 * 初始化事件
	 * 
	 * @title initEvents
	 * @author liuchengbao
	 */
	private void initEvent() {
		mPullScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

			}

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//				getCommodityList();
			}
		});
		
		buttomDemo.setOnClickListener(new OnClickListener()  
        {         
            public void onClick(View v)  
            {
				Log.e("qf","111111111");
				Intent intent =new Intent();
				intent.setClass(getActivity(), VideoActivity.class);
				startActivity(intent);
            }  
        });  
	}
	
	/**
	 * 获取速递数据
	 */
	protected void getCommodityList() {
		activity.putAsyncTask(new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				commodityList = application.getCommodityList();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				if (result) {
					commodityAdapter.setList(commodityList);
				}
				mPullScrollView.onPullDownRefreshComplete();
			}
		});
	}


	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				
			}
		};
	};
}
