package com.hongshi.hongshiandroid.net;

/**
 * 访问网络的地址管理类
 * 
 * @name URL
 * @author liuchengbao
 * @date 2015年9月11日
 * @modify
 * @modifyDate 2015年9月11日
 * @modifyContent
 */
public class URL {

	// 网络
	public final static String URL_HOST = "10.0.12.13";

	public final static String LOGIN_URL = "/fortel/login";
	
	public final static String GET_COMMODITY_LIST = "/application/test";
	
	// 保存bug信息
	public final static String SAVE_BUG_URL = "/Systems/saveSysBug";
	
	public final static String PHONE_LOGIN_CHECK_URL = "/users/loginCheck";

	public static String getURL(String url) {
		return "http://" + getUSERURL()+ ":" + 8080 + url;

	}

	public static String getUSERURL() {
		return  URL_HOST;

	}
	
}
