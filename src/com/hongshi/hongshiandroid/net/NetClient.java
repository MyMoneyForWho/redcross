package com.hongshi.hongshiandroid.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.hongshi.hongshiandroid.base.BaseApplication;
import com.hongshi.hongshiandroid.model.Commodity;
import com.hongshi.hongshiandroid.util.AppUtils;
import com.hongshi.hongshiandroid.common.Contants;
import com.hongshi.hongshiandroid.util.SPUtils;

import android.content.pm.PackageInfo;

/**
 * 访问网络公共类
 * 
 * @name NetClient
 * @author liuchengbao
 * @date 2015年9月11日
 * @modify
 * @modifyDate 2015年9月11日
 * @modifyContent
 */
public class NetClient {

	// utf-8编码
	public static final String UTF_8 = "UTF-8";

	// 连接超时时间
	private static final int TIMEOUT_CONNECTION = 2000;

	// 重新操作次数
	private static final int RETRY_TIME = 3;

	// 网络错误提示
	private static final String CONNECT_ERROR = "网络链接错误:";

	private static String userAgent;

	private static String getUserAgent(BaseApplication application) {
		if (userAgent == null || userAgent.equals("")) {
			StringBuffer sb = new StringBuffer("qingyang");

			PackageInfo packageInfo = AppUtils.getVersion(application);

			// app版本
			sb.append('/' + packageInfo.versionName + '_' + packageInfo.versionCode);
			// andorid 平台
			sb.append("/Android");
			// 手机系统版本
			sb.append("/" + android.os.Build.VERSION.RELEASE);
			// 手机型号
			sb.append("/" + android.os.Build.MODEL);

			userAgent = sb.toString();
		}
		return userAgent;
	}

	/**
	 * 获取HttpClient
	 * 
	 * @title getHttpClient
	 * @author liuchengbao
	 * @return
	 */
	private static HttpClient getHttpClient() {
		HttpClient httpClient = new HttpClient();

		// 设置默认的超时重试处理策略
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// 设置连接超时时间
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT_CONNECTION);
		httpClient.getParams().setContentCharset(UTF_8);
		return httpClient;
	}

	/**
	 * 获取get方法
	 * 
	 * @title getHttpGet
	 * @author liuchengbao
	 * @param url
	 * @param userAgent
	 * @return
	 */
	private static GetMethod getHttpGet(String url, String userAgent) {
		GetMethod httpGet = new GetMethod(url);
		// 设置请求超时时间
		httpGet.getParams().setSoTimeout(TIMEOUT_CONNECTION);
		httpGet.setRequestHeader("Host", URL.URL_HOST);
		httpGet.setRequestHeader("Connection", "Keep_Alive");
		httpGet.setRequestHeader("User_Agent", userAgent);
		return httpGet;
	}

	/**
	 * 获取post方法
	 * 
	 * @title getHttpPost
	 * @author liuchengbao
	 * @param url
	 * @param userAgent
	 * @return
	 */
	private static PostMethod getHttpPost(String url, String userAgent) {
		PostMethod httpPost = new PostMethod(url);
		// 设置请求超时时间
		httpPost.getParams().setSoTimeout(TIMEOUT_CONNECTION);
		httpPost.setRequestHeader("Host", URL.URL_HOST);
		httpPost.setRequestHeader("Connection", "Keep_Alive");
		httpPost.setRequestHeader("User_Agent", userAgent);
		return httpPost;
	}

	/**
	 * 公共用的get方法
	 * 
	 * @param application
	 * @param url
	 * @return
	 * @throws AppException
	 */
	private static String http_get(BaseApplication application, String url) {
		String userAgent = getUserAgent(application);

		HttpClient httpClient = null;

		GetMethod httpGet = null;

		String responseBody = "";

		int time = 0;

		do {
			try {
				httpClient = getHttpClient();
				httpGet = getHttpGet(url, userAgent);
				int statusCode = httpClient.executeMethod(httpGet);

				if (statusCode != HttpStatus.SC_OK) {
					return CONNECT_ERROR + statusCode;
				}

				responseBody = httpGet.getResponseBodyAsString();

				break;

			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				e.printStackTrace();
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				e.printStackTrace();
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);

		return responseBody;
	}

	/**
	 * 公用的post方法
	 * 
	 * @param application
	 * @param url
	 * @param params
	 * @param files
	 * @return
	 * @throws AppException
	 */
	private static String http_post(BaseApplication application, String url, Map<String, Object> params,
			Map<String, File> files) {

		String userAgent = getUserAgent(application);

		HttpClient httpClient = null;

		PostMethod httpPost = null;

		// post表单参数处理
		int length = (params == null ? 0 : params.size()) + (files == null ? 0 : files.size());

		Part[] parts = new Part[length];

		int i = 0;

		if (params != null) {
			for (String name : params.keySet()) {
				parts[i++] = new StringPart(name, String.valueOf(params.get(name)), UTF_8);
			}
		}

		if (files != null) {
			for (String fileName : files.keySet()) {
				try {
					parts[i++] = new FilePart(fileName, files.get(fileName));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		String responseBody = "";

		int time = 0;

		do {
			try {
				httpClient = getHttpClient();

				httpPost = getHttpPost(url, userAgent);

				httpPost.setRequestEntity(new MultipartRequestEntity(parts, httpPost.getParams()));

				int statusCode = httpClient.executeMethod(httpPost);

				if (statusCode != HttpStatus.SC_OK) {
					return "网络连接超时";
				}
				responseBody = httpPost.getResponseBodyAsString();

				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
			} finally {
				// 释放连接
				httpPost.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);

		return responseBody;
	}

	/**
	 * 手机号登陆验证
	 * 
	 * @title phoneLoginCheck
	 * @author liuchengbao
	 * @param application
	 * @param phone
	 * @param password
	 * @return
	 */
	public static String phoneLoginCheck(BaseApplication application, String phone, String password) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", phone);
		params.put("password", password);
		params.put("clientId", SPUtils.get(application, Contants.GETUI_CLIENT_ID, ""));
		return http_post(application, URL.getURL(URL.PHONE_LOGIN_CHECK_URL), params, null);
	}

	/**
	 * 保存bug信息
	 * 
	 * @title saveBugInfo
	 * @author zhaoqingyang
	 * @param baseApplication
	 * @param result
	 * @return
	 */
	public static String saveBugInfo(BaseApplication application, String result) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", application.getUserId());
		params.put("bugDescription", result);
		params.put("versionCode", AppUtils.getVersion(application).versionCode);
		params.put("versionName", AppUtils.getVersion(application).versionName);
		params.put("phoneModel", android.os.Build.MODEL);
		return http_post(application, URL.getURL(URL.SAVE_BUG_URL), params, null);
	}

	/**
	 * 获取今日江湖求助
	 * 
	 * @title getJRJHHelp
	 * @author liuchengbao
	 * @param application
	 */
	public static void getCommodityList(BaseApplication application) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", application.getUserId() + "");
		Commodity.parse(application, http_post(application, URL.getURL(URL.GET_COMMODITY_LIST), params, null), true);
	}

}
