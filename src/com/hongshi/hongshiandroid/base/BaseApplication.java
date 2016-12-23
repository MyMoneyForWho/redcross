package com.hongshi.hongshiandroid.base;

import java.util.List;

import com.hongshi.hongshiandroid.service.WifiBroad;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.hongshi.hongshiandroid.LoginActivity;
import com.hongshi.hongshiandroid.R;
import com.hongshi.hongshiandroid.common.Contants;
import com.hongshi.hongshiandroid.common.DBManager;
import com.hongshi.hongshiandroid.model.Commodity;
import com.hongshi.hongshiandroid.net.NetClient;
import com.hongshi.hongshiandroid.util.CrashHandler;
import com.hongshi.hongshiandroid.util.NetUtils;
import com.hongshi.hongshiandroid.util.SPUtils;
import com.hongshi.hongshiandroid.view.TitleBarView;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;

/**
 * 整个应用的Application
 *
 * @author zhaoqingyang
 * @name BaseApplication
 * @date 2015年9月11日
 * @modify
 * @modifyDate 2015年9月11日
 * @modifyContent
 */
public class BaseApplication extends Application {

    public DBManager dBManager;

    public ImageLoader imageLoader;

    public DisplayImageOptions options;

    @Override
    public void onCreate() {
        super.onCreate();

        dBManager = new DBManager(this);

        imageLoader = ImageLoader.getInstance();

        initImageLoader();


        // // 初始化异常处理类
//		 CrashHandler crashHandler = CrashHandler.getInstance();
//		 crashHandler.init(getApplicationContext(), this);

    }

    /*
     * 自定义配置
     */
    public void initImageLoader() {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPoolSize(3);// 线程池内加载的数量
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();// 不缓存图片的多种尺寸在内存中
        config.discCacheFileNameGenerator(new Md5FileNameGenerator());// 将保存的时候的URI名称用MD5
        config.discCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();// Remove for release app
        // 初始化ImageLoader
        ImageLoader.getInstance().init(config.build());

        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.grey)// 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.grey)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.grey)// 设置图片加载/解码过程中错误时候显示的图片
                .delayBeforeLoading(100)// 设置延时多少时间后开始下载
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的资源是否缓存在SD卡中
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)// 设置图片以何种编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
                .displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
                .build();

    }



    /**
     * 获取用户ID
     *
     * @return
     * @title getUserId
     * @author liuchengbao
     */
    public String getUserId() {
        return (String) SPUtils.get(this, Contants.USER_ID, "");
    }

    /**
     * 获取用户名
     *
     * @return
     * @title getUserName
     * @author liuchengbao
     */
    public String getUserName() {
        return (String) SPUtils.get(this, Contants.USER_NAME, "");
    }

    /**
     * 判断用户是否登陆
     *
     * @return
     * @title getLoginState
     * @author liuchengbao
     */
    public boolean getLoginState() {
        int type = (Integer) SPUtils.get(this, Contants.LOGIN_TYPE, 0);

        if (type == 0) {
            return true;
        } else {
            return true;
        }
    }

    /**
     * 启动登陆页面
     *
     * @title startLogin
     * @author liuchengbao
     */
    public void startLogin(Context context) {
        Intent in = new Intent(this, LoginActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(in);
    }

    /**
     * 获取时间戳
     *
     * @return
     * @title getUpdateTime
     * @author zhaoqingyang
     */
    public long getUpdateTime() {
        return (Long) SPUtils.get(this, Contants.UPDATE_TIME, System.currentTimeMillis());
    }

    /**
     * 保存bug信息
     *
     * @title saveBugInfo
     * @author zhaoqingyang
     */
    public boolean saveBugInfo(String info) {
        String result = NetClient.saveBugInfo(this, info);

        if (result.equals("ok")) {
            return true;
        }

        return false;

    }

    /**
     * 获取今日江湖速递
     *
     * @return
     * @title getJRJHExpress
     * @author liuchengbao
     */
    public List<Commodity> getCommodityList() {
        if (NetUtils.isConnected(this)) {
            NetClient.getCommodityList(this);
        }
        return dBManager.getCommodityList();
    }

    /**
     * 设置提醒图标
     *
     * @title setMessageImg
     * @author zhaoqingyang
     */
    public void setMessageImg(TitleBarView titleBarView) {

        // 如果有未读的消息s
        if (IsNotReadMsg()) {
            titleBarView.setBtnLeft(R.drawable.mail_notread_button);
        } else {
            titleBarView.setBtnLeft(R.drawable.mail_button);
        }

    }


    /**
     * 是否有未读的消息
     *
     * @return
     * @title IsNotReadMsg
     * @author zhaoqingyang
     */
    public boolean IsNotReadMsg() {

        // true 有未读的系统消息 false 没有未读的系统消息
        boolean sysMsg = (Boolean) SPUtils.get(this, Contants.SYSTEM_MSG, false);

        // true 有未读的聊天消息 false 没有未读的聊天消息
        boolean chatMsg = (Boolean) SPUtils.get(this, Contants.CHAT_MSG, false);

        if (sysMsg || chatMsg) {
            // 有未读的消息
            return true;
        }

        // 没有未读的消息
        return false;
    }


    /**
     * 手机号登陆验证
     *
     * @param phone
     * @param password
     * @return
     * @title phoneLoginCheck
     * @author liuchengbao
     */
    public String phoneLoginCheck(String phone, String password) {
        return NetClient.phoneLoginCheck(this, phone, password);
    }
}
