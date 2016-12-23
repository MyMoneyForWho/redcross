package com.hongshi.hongshiandroid.common;

import android.os.Environment;

/**
 * 系统常量
 * 
 * @name Contants
 * @author zhaoqingyang
 * @date 2015年9月11日
 * @modify
 * @modifyDate 2015年9月11日
 * @modifyContent
 */
public class Contants {

	// SharedPreferences 名称
	public final static String SHARD_PRE_NAME = "MT_SR_NAME";

	// 下载保存路径
	public final static String DOWN_URL = "/xinqiao/down/";

	// 下载APK
	public final static String DOWN_APP = "/xinqiao/app/";

	// 分页数据
	public final static int PAGE_SIZE = 10;

	// 分页数据较大
	public final static int PAGE_SIZE_BIGGER = 10;

	// 显示照片的最大个数
	public final static int PICURE_COUNT = 6;

	// 显示回复最大图片数量
	public final static int PICURE_COUNT_REPLY = 3;

	// 时间戳
	public final static String UPDATE_TIME = "update_time";

	// 新消息提醒
	public final static String SETTING_NOTIFY = "setting_notify";

	// 图片保存地址
	public final static String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ Contants.DOWN_URL;
	// 图片保存地址
	public final static String DOWN_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/meitan/";

	// 百科webWiew缓存
	public static final String WIKI_APP_CACAHE_DIRNAME = "/webcache";

	// 数据库名称
	public final static String DATABASE_NAME = "DSZB_DB";

	// 更新提醒
	public final static String NOTIFICATION_FLAG = "notification_flag";

	// 正在聊天的用户ID
	public final static String CHAT_USER_ID = "chat_user_id";

	// 个推:clientid
	public final static String GETUI_CLIENT_ID = "getui_clientid";

	// 登录状态
	public final static String LOGIN_STATE = "login_state";

	// 是否有未读的系统消息 true 有未读的消息
	public final static String SYSTEM_MSG = "system_msg";

	// 是否有未读的聊天消息 true 有未读的消息
	public final static String CHAT_MSG = "chat_msg";

	// 用户ID
	public final static String USER_ID = "user_id";

	// 用户姓名
	public final static String USER_NAME = "user_name";

	// 用户头像
	public final static String USER_PORTRAIT = "user_portrait";

	// 应用在SD卡保存数据的根路径
	public final static String ROOT_SD_PATH = "meitan/";

	// 捕获异常保存路径
	public final static String CRASH_SD_PAHT = ROOT_SD_PATH + "crash/";

	// 填写从短信SDK应用后台注册得到的APPKEY
	public final static String SMS_APPKEY = "a5cb844239c0";

	// 填写从短信SDK应用后台注册得到的APPSECRET
	public final static String SMS_APPSECRET = "4de1d33f9794d2885adae21a0fa986b2";

	// QQ登陆appid
	public static final String QQ_APP_ID = "1104955278";

	// 微博登陆APP_KEY
	public static final String WEIBO_APP_KEY = "2965687052";

	// 微信登陆appid
	public static final String WEIXIN_APP_ID = "wx3758c37c706bc98a";

	// 微信申请的app_secret
	public static final String WEIXIN_SECRET = "633322662ef3179616a38078635e4d29";

	/**
	 * 当前应用的回调页。
	 * 
	 * <p>
	 * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响， 但是没有定义将无法使用 SDK 认证登录。
	 * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
	 * </p>
	 */
	public static final String WEIBO_REDIRECT_URL = "http://www.sina.com";

	/**
	 * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
	 * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利 选择赋予应用的功能。
	 * 
	 * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的 使用权限，高级权限需要进行申请。
	 * 
	 * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
	 * 
	 * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
	 * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
	 */
	public static final String WEIBO_SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read," + "follow_app_official_microblog,"
			+ "invitation_write";

	// 登录类型(1.QQ登陆2.微博登陆3.微信登陆4.账号登陆)
	public final static String LOGIN_TYPE = "login_type";

	// QQ登陆的token
	public final static String QQ_TOKEN = "login_qq_token";

	// QQ登陆的openid
	public final static String QQ_OPENID = "login_qq_openid";

	// QQ登陆的有效时间
	public final static String QQ_EXPIRES = "login_qq_expires";

	// 微博登陆的token
	public final static String WEIBO_TOKEN = "login_weibo_token";

	// 微博登陆的uid
	public final static String WEIBO_UID = "login_weibo_uid";

	// 微博登陆的有效时间
	public final static String WEIBO_EXPIRES = "login_weibo_expires";

	// 微博登陆的刷新refresh_token
	public final static String WEIBO_REFRESH_TOKEN = "login_weibo_refresh_token";

	// 微信登陆的openid
	public final static String WEIXIN_PENID = "login_weixin_openid";

	// 微信登陆access_token
	public final static String WEIXIN_ACCESS_TOKEN = "login_weixin_access_token";

	// 微信登录有效时间
	public final static String WEIXIN_EXPIRES = "login_weixin_expires";

	// 微信登陆的刷新refresh_token
	public final static String WEIXIN_REFRESH_TOKEN = "login_weixn_refresh_token";

	// 编辑词条
	public final static int WIKI_EDITOR = 4;

	// 添加词条
	public final static int WIKI_ADD = 5;

}
