package com.hongshi.hongshiandroid.wxapi;

import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.hongshi.hongshiandroid.R;
import com.hongshi.hongshiandroid.base.BaseActivity;
import com.hongshi.hongshiandroid.common.Contants;
import com.hongshi.hongshiandroid.util.NetUtils;
import com.hongshi.hongshiandroid.util.SPUtils;
import com.hongshi.hongshiandroid.util.ToastUtils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 微信登陆
 * 
 * @name WXEntryActivity
 * @author liuchengbao
 * @Description TODO
 * @date 2015年10月15日
 * @modify
 * @modifyDate 2015年10月15日
 * @modifyContent
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

	// 微信api
	private IWXAPI api;

	// 返回openid，accessToken消息码
	public static final int RETURN_OPENID_ACCESSTOKEN = 0;

	// 返回昵称，uid消息码
	public static final int RETURN_NICKNAME_UID = 1;

	// 提交用户个人信息返回消息
	private String commitUserMessage = "";

	private Context mContext;

	private boolean isAuth = false;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RETURN_OPENID_ACCESSTOKEN:
				String openId = (String) SPUtils.get(mContext, Contants.WEIXIN_PENID, "");

				String accessToken = (String) SPUtils.get(mContext, Contants.WEIXIN_ACCESS_TOKEN, "");

				WXAsyncHttpClient.getUID(openId, accessToken, handler);

				break;

			case RETURN_NICKNAME_UID:

				Bundle bundle = (Bundle) msg.obj;
				String result = bundle.getString("result");

				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(result);
					if (jsonObject != null) {
						String nickname = jsonObject.getString("nickname");
						String sex = jsonObject.getString("sex");
						String openid = jsonObject.getString("openid");
						String portrait = jsonObject.getString("headimgurl");
						String city = jsonObject.getString("city");
						commitUserInfo(openid, nickname, sex, city, portrait);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_elves);

		//createLoadingDialog(WXEntryActivity.this);

		mContext = this;

		api = WXAPIFactory.createWXAPI(this, Contants.WEIXIN_APP_ID, false);
		// 注册到微信列表
		api.registerApp(Contants.WEIXIN_APP_ID);

		try {
			api.handleIntent(getIntent(), this);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		int type = (Integer) SPUtils.get(this, Contants.LOGIN_TYPE, 0);
		if (0 == type && !isAuth) {
			sendAuth();
		} else {
			finish();
			return;
		}
	}

	/**
	 * 申请授权
	 * 
	 * @title sendAuth
	 * @author liuchengbao
	 * @Description TODO
	 */
	private void sendAuth() {
		SendAuth.Req req = new SendAuth.Req();
		// 用于请求用户信息的作用域
		req.scope = "snsapi_userinfo";
		// 自定义
		req.state = "none";
		api.sendReq(req);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	/**
	 * 请求回调接口
	 */
	@Override
	public void onReq(BaseReq req) {
	}

	/**
	 * 请求响应回调接口
	 */
	@Override
	public void onResp(BaseResp resp) {

		switch (resp.errCode) {

		case BaseResp.ErrCode.ERR_AUTH_DENIED:
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == resp.getType())
				ToastUtils.show(mContext, "分享失败", ToastUtils.TOAST_SHORT);
			else {
				ToastUtils.show(mContext, "登录失败", ToastUtils.TOAST_SHORT);
				isAuth = true;
				finish();
			}
			break;
		case BaseResp.ErrCode.ERR_OK:
			switch (resp.getType()) {
			case ConstantsAPI.COMMAND_SENDAUTH:
				isAuth = true;
				// 拿到了微信返回的code,立马再去请求access_token
				String code = ((SendAuth.Resp) resp).code;
				// 就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
				WXAsyncHttpClient.getResult(mContext, code, handler);
				break;

			case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
				ToastUtils.show(mContext, "微信分享成功", ToastUtils.TOAST_SHORT);

				finish();
				break;
			}
			break;
		}

	}

	protected void commitUserInfo(final String openid, final String username, final String sex, final String address,
			final String portrait) {
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();

			}

			@Override
			protected Boolean doInBackground(Void... params) {

				if (NetUtils.isConnected(getApplicationContext())) {

					return true;
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				if (result) {

				} else {
					ToastUtils.show(getApplicationContext(), R.string.no_net, ToastUtils.TOAST_SHORT);
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		api.unregisterApp();
	}
}
