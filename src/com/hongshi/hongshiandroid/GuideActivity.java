package com.hongshi.hongshiandroid;


import com.hongshi.hongshiandroid.common.Contants;
import com.hongshi.hongshiandroid.util.NetUtils;
import com.hongshi.hongshiandroid.util.SPUtils;
import com.hongshi.hongshiandroid.util.StringUtils;
import com.hongshi.hongshiandroid.weibo.WBOpenAPI;
import com.hongshi.hongshiandroid.wxapi.WXAsyncHttpClient;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

/**
 * 引导页
 * 
 * @name GuideActivity
 * @author zhaoqingyang
 * @date 2015年9月14日
 * @modify
 * @modifyDate 2015年9月14日
 * @modifyContent
 */
public class GuideActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_guide);

		// 判断是否连接网络
		if (NetUtils.isConnected(getApplicationContext())) {
			setLoginState();
		} else {
			isLogin();
		}

	}

	/**
	 * 在未连网的状态下,判断是否登陆过
	 * 
	 * @title isLogin
	 * @author liuchengbao
	 * @Description TODO
	 */
	private void isLogin() {
		int type = (Integer) SPUtils.get(this, Contants.LOGIN_TYPE, 0);
		if (type == 1 || type == 2 || type == 3 || type == 4) {
			SPUtils.put(this, Contants.LOGIN_STATE, true);
		} else {
			SPUtils.put(this, Contants.LOGIN_STATE, false);
		}
		handler.sendEmptyMessageDelayed(0, 2000);
	}

	private void setLoginState() {

		int type = (Integer) SPUtils.get(this, Contants.LOGIN_TYPE, 0);

		switch (type) {
		case 0:
			handler.sendEmptyMessageDelayed(0, 2000);
			break;
		case 1:

			String qq_token = (String) SPUtils.get(this, Contants.QQ_TOKEN, "");
			String qq_expires = (String) SPUtils.get(this, Contants.QQ_EXPIRES, "");
			if (StringUtils.isEmpty(qq_token)) {
				return;
			} else {
				long many_time = (Long.parseLong(qq_expires) - (long) System.currentTimeMillis());
				if (many_time > 0) {
					SPUtils.put(this, Contants.LOGIN_STATE, true);
				} else {
					SPUtils.put(this, Contants.LOGIN_STATE, false);
				}
			}
			handler.sendEmptyMessageDelayed(0, 2000);
			break;
		case 2:

			String weibo_token = (String) SPUtils.get(this, Contants.WEIBO_TOKEN, "");
			String weibo_expires = (String) SPUtils.get(this, Contants.WEIBO_EXPIRES, "");
			String weibo_refresh_token = (String) SPUtils.get(this, Contants.WEIBO_REFRESH_TOKEN, "");
			if (StringUtils.isEmpty(weibo_token)) {
				return;
			} else {
				long many_time = (Long.parseLong(weibo_expires) - (long) System.currentTimeMillis());
				if (many_time > 0) {
					SPUtils.put(this, Contants.LOGIN_STATE, true);
					handler.sendEmptyMessageDelayed(0, 2000);
				} else {
					WBOpenAPI.refreshTokenRequest(getApplicationContext(), handler, weibo_refresh_token);
				}
			}
			break;
		case 3:

			String openId = (String) SPUtils.get(this, Contants.WEIXIN_PENID, "");
			String accessToken = (String) SPUtils.get(this, Contants.WEIXIN_ACCESS_TOKEN, "");

			WXAsyncHttpClient.getWeiXinExpires(getApplicationContext(), openId, accessToken, handler);
			break;
		case 4:
			String userId = (String) SPUtils.get(this, Contants.USER_ID, "");
			if (StringUtils.isEmpty(userId)) {
				return;
			} else {
				SPUtils.put(this, Contants.LOGIN_STATE, true);
				handler.sendEmptyMessageDelayed(0, 2000);
			}
			break;

		default:
			break;
		}
	}

	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			// 获取登录状态
			// boolean loginState = (Boolean) SPUtils.get(GuideActivity.this,
			// Contants.LOGIN_STATE, false);

			// 如果已经登录 跳转到主页面 否则跳转到登录页面
			// if (loginState) {
			// startActivity(MainActivity.class);
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			// } else {
			// startActivity(LoginActivity.class);
			// }

			finish();
		};
	};

	@Override
	protected void onResume() {
		super.onResume();
		//JPushInterface.onResume(getApplicationContext());
	}

	@Override
	protected void onPause() {
		super.onPause();
		//JPushInterface.onPause(getApplicationContext());
	}

}
