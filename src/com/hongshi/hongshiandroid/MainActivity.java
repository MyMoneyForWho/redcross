package com.hongshi.hongshiandroid;

import com.hongshi.hongshiandroid.base.BaseActivity;
import com.hongshi.hongshiandroid.service.WifiBroad;
import com.umeng.analytics.MobclickAgent;
import com.hongshi.hongshiandroid.fragment.HomeFragment;
import com.hongshi.hongshiandroid.fragment.MerchandiseFragment;
import com.hongshi.hongshiandroid.fragment.CommunityFragment;
import com.hongshi.hongshiandroid.fragment.CartFragment;
import com.hongshi.hongshiandroid.fragment.MyFragment;
import com.hongshi.hongshiandroid.common.Contants;
import com.hongshi.hongshiandroid.util.SPUtils;

import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends BaseActivity {

	private RadioGroup myTabRg;

	private HomeFragment homeFragment;

	private MerchandiseFragment merchandiseFragment;

	private CommunityFragment communityFragment;

	private CartFragment cartFragment;

	private MyFragment myFragment;

	private String checkMsg = "";

	private FragmentManager fragmentManager = null;

	private FragmentTransaction transaction = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		createLoadingDialog(MainActivity.this);

		initView();

		checkUpdate(false);

		loginState();


	}


	protected void checkUpdate(final boolean isPopup) {
		
	}

	/**
	 * 初始化底部导航栏
	 */
	public void initView() {
		fragmentManager = this.getSupportFragmentManager();

		showFragment(0);

		myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
		myTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					if (application.getLoginState()) {
						showFragment(0);
					}
					break;
				case R.id.rb_merchandise:
					if (application.getLoginState()) {
						showFragment(1);
					} else {
						myTabRg.check(R.id.rb_home);
						application.startLogin(getApplicationContext());
					}
					break;
				case R.id.rb_community:
					if (application.getLoginState()) {
						showFragment(2);
					} else {
						myTabRg.check(R.id.rb_home);
						application.startLogin(getApplicationContext());
					}
					break;
				case R.id.rb_cart:
					if (application.getLoginState()) {
						showFragment(3);
					} else {
						myTabRg.check(R.id.rb_home);
						application.startLogin(getApplicationContext());
					}
					break;
				case R.id.rb_my:
					if (application.getLoginState()) {
						showFragment(4);
					} else {
						myTabRg.check(R.id.rb_home);
						application.startLogin(getApplicationContext());
					}
					break;
				default:
					break;
				}
			}
		});
	}
	

	/**
	 * 用于显示在线人员数量
	 */
	private void loginState() {
		int login_type = (Integer) SPUtils.get(getApplicationContext(), Contants.LOGIN_TYPE, 0);
		switch (login_type) {
		case 1:
			MobclickAgent.onProfileSignIn("QQ", application.getUserId());
			break;
		case 2:
			MobclickAgent.onProfileSignIn("WB", application.getUserId());
			break;
		case 3:
			MobclickAgent.onProfileSignIn("WX", application.getUserId());
			break;
		case 4:
			MobclickAgent.onProfileSignIn(application.getUserId());
			break;
		default:
			break;
		}
	}

	/**
	 * 显示fragment
	 */
	public void showFragment(int index) {
		transaction = fragmentManager.beginTransaction();

		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

		// 想要显示一个fragment,先隐藏所有fragment，防止重叠
		hideFragments(transaction);

		switch (index) {
		case 0:
			if (homeFragment != null)
				transaction.show(homeFragment);
			else {
				homeFragment = HomeFragment.newInstance();
				transaction.add(R.id.main_content, homeFragment);
			}
			break;
		case 1:
			// 如果fragment1已经存在则将其显示出来
			if (merchandiseFragment != null)
				transaction.show(merchandiseFragment);
			// 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
			else {
				merchandiseFragment = MerchandiseFragment.newInstance();
				transaction.add(R.id.main_content, merchandiseFragment);
			}
			break;
		case 2:
			if (communityFragment != null)
				transaction.show(communityFragment);
			else {
				communityFragment = CommunityFragment.newInstance();
				transaction.add(R.id.main_content, communityFragment);
			}
			break;
		case 3:
			if (cartFragment != null)
				transaction.show(cartFragment);
			else {
				cartFragment = CartFragment.newInstance();
				transaction.add(R.id.main_content, cartFragment);
			}
			break;
		case 4:
			if (myFragment != null)
				transaction.show(myFragment);
			else {
				myFragment = MyFragment.newInstance();
				transaction.add(R.id.main_content, myFragment);
			}
			break;
		}
		transaction.commitAllowingStateLoss();
	}

	/**
	 * 当fragment已被实例化，就隐藏起来
	 */
	public void hideFragments(FragmentTransaction ft) {
		if (homeFragment != null)
			ft.hide(homeFragment);
		if (merchandiseFragment != null)
			ft.hide(merchandiseFragment);
		if (communityFragment != null)
			ft.hide(communityFragment);
		if (cartFragment != null)
			ft.hide(cartFragment);
		if (myFragment != null)
			ft.hide(myFragment);
	}
}