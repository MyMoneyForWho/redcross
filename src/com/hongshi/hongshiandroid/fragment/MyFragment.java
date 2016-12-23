package com.hongshi.hongshiandroid.fragment;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.hongshi.hongshiandroid.view.refresh.PullToRefreshBase;
import com.hongshi.hongshiandroid.view.refresh.PullToRefreshBase.OnRefreshListener;
import com.hongshi.hongshiandroid.model.MessageRemind;
import com.hongshi.hongshiandroid.MainActivity;
import com.hongshi.hongshiandroid.R;
import com.hongshi.hongshiandroid.base.BaseFragment;
import com.hongshi.hongshiandroid.view.TitleBarView;
import com.hongshi.hongshiandroid.view.refresh.PullToRefreshScrollView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class MyFragment extends BaseFragment {

	// fragment保存数据的key
	private static final String KEY_CONTENT = "MyFragment:Content";

	private MainActivity activity;

	private ScrollView eScroll;

	private PullToRefreshScrollView mPullScrollView;

	// 公共title
	@ViewInject(R.id.my_titleBar)
	private TitleBarView titleBarView;

	public static MyFragment newInstance() {
		MyFragment fragment = new MyFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		activity = (MainActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_my, container, false);

		mPullScrollView = (PullToRefreshScrollView) rootView.findViewById(R.id.my_scroll);

		eScroll = mPullScrollView.getRefreshableView();

		eScroll.addView(inflater.inflate(R.layout.fragment_my_child, null));

		eScroll.setVerticalScrollBarEnabled(false);

		// 注入view和事件
		ViewUtils.inject(this, rootView);

		initView();
		
		initEvent();

		mPullScrollView.doPullRefreshing(true, 500);

		return rootView;
	}

	/**
	 * 初始化视图
	 * 
	 * @title initViews
	 * @author liuchengbao
	 */
	private void initView() {
		titleBarView.setCommonTitle(View.VISIBLE, View.GONE, View.GONE, View.VISIBLE, View.GONE);
		application.setMessageImg(titleBarView);
		titleBarView.setBtnRight(R.drawable.set_button);

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
				mPullScrollView.onPullDownRefreshComplete();
			}
		});

	}

	// 接收信息
	public void onEventMainThread(MessageRemind messageRemind) {
		application.setMessageImg(titleBarView);
	}
}
