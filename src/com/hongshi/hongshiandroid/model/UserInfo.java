package com.hongshi.hongshiandroid.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.hongshi.hongshiandroid.base.BaseApplication;

/**
 * 用户信息
 * 
 * @name UserInfo
 * @author liuchengbao
 * @Description TODO
 * @date 2015年11月4日
 * @modify
 * @modifyDate 2015年11月4日
 * @modifyContent
 */
public class UserInfo implements Serializable {

	private static final long serialVersionUID = -3776896662484413483L;

	// 本地数据库id
	private Long id = 0L;

	// web端数据库id
	private String sysId;

	// web端更新时间
	private long updateTime;

	// 账号
	private String account;

	// 密码
	private boolean password;

	// 性别
	private String sex;

	// 年龄
	private String age;

	// 是否是学生
	private boolean isStudent;
	
	//登录状态
	private Boolean loginState;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public boolean isPassword() {
		return password;
	}

	public void setPassword(boolean password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public boolean isStudent() {
		return isStudent;
	}

	public void setStudent(boolean student) {
		isStudent = student;
	}

	public Boolean getLoginState() {
		return loginState;
	}

	public void setLoginState(Boolean loginState) {
		this.loginState = loginState;
	}
				/*
                 * 解析字符串
                 * @title parse
                 * @author liuchengbao
                 * @param application
                 * @param result
                 */
	public static void parse(BaseApplication application, String result) {

		if (!result.equals("")) {

			try {
				JSONObject jsonObject = new JSONObject(result);

				UserInfo info = new UserInfo();

				info.setSysId(jsonObject.optString("userId"));


				// 保存求助信息
				application.dBManager.saveMyInfo(info);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}



}
