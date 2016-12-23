package com.hongshi.hongshiandroid.adapter;

import java.util.List;

import com.hongshi.hongshiandroid.R;
import com.hongshi.hongshiandroid.base.BaseApplication;
import com.hongshi.hongshiandroid.model.Commodity;
import com.hongshi.hongshiandroid.view.MyGridView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 今日江湖速递Adapter
 * 
 * @name JrjhExpressAdapter
 * @author zhaoqingyang
 * @date 2015年11月4日
 * @modify
 * @modifyDate 2015年11月4日
 * @modifyContent
 */
public class CommodityAdapter extends BaseAdapter {

	private Context context;

	private List<Commodity> list;

	private BaseApplication application;

	private Handler handler;

	public CommodityAdapter(Context context, BaseApplication application, Handler handler) {
		this.context = context;
		this.application = application;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(context).inflate(R.layout.home_item_view, null);
			holder.detail = (LinearLayout) convertView.findViewById(R.id.jinri_item_detail);
			holder.title = (TextView) convertView.findViewById(R.id.jinri_item_title);
			holder.content = (TextView) convertView.findViewById(R.id.jinri_item_content);
			holder.user = (TextView) convertView.findViewById(R.id.jinri_item_user);
			holder.time = (TextView) convertView.findViewById(R.id.jinri_item_time);
			holder.count = (TextView) convertView.findViewById(R.id.jinri_item_count);
			holder.picture = (MyGridView) convertView.findViewById(R.id.jinri_item_picture);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		final int p = position;

		holder.title.setText(list.get(position).getContent());
		holder.content.setText(list.get(position).getContent());
		
		return convertView;
	}

	class Holder {
		LinearLayout detail;
		TextView title;
		TextView content;
		TextView user;
		TextView time;
		TextView count;
		MyGridView picture;
	}

	public List<Commodity> getList() {
		return list;
	}

	public void setList(List<Commodity> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	
	public void clearList() {
		this.list.clear();
		notifyDataSetChanged();
	}

}