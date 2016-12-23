package com.hongshi.hongshiandroid.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.hongshi.hongshiandroid.model.Commodity;
import com.hongshi.hongshiandroid.model.UserInfo;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.hongshi.hongshiandroid.base.BaseModel;
import com.hongshi.hongshiandroid.util.AppUtils;

import android.content.Context;
import android.database.Cursor;

/**
 * 数据库访问的公共类
 * 
 * @name DBManager
 * @author liuchengbao
 * @date 2015年9月11日
 * @modify
 * @modifyDate 2015年9月11日
 * @modifyContent
 */
public class DBManager {

	private DbUtils db;


	public DBManager(Context context) {

		// 数据库版本跟着apk版本走
		int dbVersion = AppUtils.getVersion(context).versionCode;

		// 创建数据库
		db = DbUtils.create(context, Contants.DATABASE_NAME, dbVersion, new DbUpgradeListener() {

			@Override
			public void onUpgrade(DbUtils dbUtils, int oldVersion, int newVersion) {
				if (oldVersion < newVersion) {
					updateDb(dbUtils, "Commodity");
				}

			}
		});

		// 开启事物 支持多线程操作数据库
		db.configAllowTransaction(true);

	}
	

	/**
	 * 修改数据库
	 * 
	 * @title updateDb
	 * @author zhaoqingyang
	 * @param db
	 * @param tableName
	 */
	@SuppressWarnings("unchecked")
	private static void updateDb(DbUtils db, String tableName) {

		try {

			Class<BaseModel> c = (Class<BaseModel>) Class.forName("com.dingshi.dingshiandroid.model." + tableName);// 把要使用的类加载到内存中,并且把有关这个类的所有信息都存放到对象c中

			if (db.tableIsExist(c)) {

				List<String> dbFildsList = new ArrayList<String>();

				tableName = "com_dingshi_dingshiandroid_model_" + tableName;

				String str = "select * from " + tableName;

				Cursor cursor = db.execQuery(str);

				int count = cursor.getColumnCount();

				for (int i = 0; i < count; i++) {

					dbFildsList.add(cursor.getColumnName(i));

				}

				cursor.close();

				Field f[] = c.getDeclaredFields();// 把属性的信息提取出来，并且存放到field类的对象中，因为每个field的对象只能存放一个属性的信息所以要用数组去接收

				for (int i = 0; i < f.length; i++) {

					String fildName = f[i].getName();

					if (fildName.equals("serialVersionUID")) {

						continue;

					}

					String fildType = f[i].getType().toString();

					if (!dbFildsList.contains(fildName)) {

						if (fildType.equals("class java.lang.String")) {

							db.execNonQuery("alter table " + tableName + " add " + fildName + " TEXT ");

						} else if (fildType.equals("int") || fildType.equals("long") || fildType.equals("boolean")) {

							db.execNonQuery("alter table " + tableName + " add " + fildName + " INTEGER ");

						}

					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 保存我的个人信息
	 * 
	 * @title saveMyInfo
	 * @author liuchengbao
	 * @param info
	 */
	public void saveMyInfo(UserInfo info) {
		try {
			UserInfo old = db.findFirst(Selector.from(UserInfo.class).where(WhereBuilder.b("sysID", "=", info.getSysId())));

			if (old != null) {
				info.setId(old.getId());
			}

			db.saveOrUpdate(info);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
	public List<Commodity> getCommodityList() {

		List<Commodity> list = null;
		try {
			list = db.findAll(Selector.from(Commodity.class).orderBy("updateTime", true));
		} catch (DbException e) {
			e.printStackTrace();
		}

		if (list == null) {
			list = new ArrayList<Commodity>();
		}
		return list;

	}
	
	/**
	 * 删除全部咨询
	 * 
	 * @title deleteAllExpressList
	 * @author zhaoqingyang
	 * @param areaId
	 */
	public void deleteAllCommoditysList(String areaId) {
		try {

			db.delete(Commodity.class, WhereBuilder.b("areaId", "=", areaId));

		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 保存速递信息
	 * 
	 * @title saveExpressInfo
	 * @author zhaoqingyang
	 * @param expressList
	 */
	public void saveCommodityInfo(Commodity commodity) {
		try {
			Commodity old = db.findFirst(Selector.from(Commodity.class).where(WhereBuilder.b("sysID", "=", commodity.getSysId())));

			if (old != null) {
				commodity.setId(old.getId());
			}

			db.saveOrUpdate(commodity);
		} catch (DbException e) {
			e.printStackTrace();
		}

	}
}
