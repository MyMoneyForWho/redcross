package com.hongshi.hongshiandroid.fragment;

import com.hongshi.hongshiandroid.MainActivity;
import com.hongshi.hongshiandroid.R;
import com.hongshi.hongshiandroid.base.BaseFragment;

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
public class MerchandiseFragment extends BaseFragment {

	// fragment保存数据的key
	private static final String KEY_CONTENT = "MerchandiseFragment:Content";

	private MainActivity activity;

	private ScrollView eScroll;

	public static MerchandiseFragment newInstance() {
		MerchandiseFragment fragment = new MerchandiseFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		activity = (MainActivity) getActivity();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_merchandise, container, false);

		return rootView;
	}

	/**
	 * 初始化视图
	 * 
	 * @title initViews
	 * @author liuchengbao
	 */
	private void initViews() {


	}

	/**
	 * 初始化事件
	 * 
	 * @title initEvents
	 * @author liuchengbao
	 */
	private void initEvents() {


	}

}
