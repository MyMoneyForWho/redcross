package com.hongshi.hongshiandroid;

import com.hongshi.hongshiandroid.base.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.hongshi.hongshiandroid.common.ProgressGenerator;
import com.hongshi.hongshiandroid.model.UserInfo;
import com.hongshi.hongshiandroid.common.Contants;
import com.hongshi.hongshiandroid.util.NetUtils;
import com.hongshi.hongshiandroid.util.SPUtils;
import com.hongshi.hongshiandroid.util.StringUtils;
import com.hongshi.hongshiandroid.util.ToastUtils;
import com.hongshi.hongshiandroid.view.ContainsEmojiEditText;

import org.json.JSONException;
import org.json.JSONObject;

import com.hongshi.hongshiandroid.view.processbtn.ActionProcessButton;
import com.google.gson.Gson;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends BaseActivity implements ProgressGenerator.OnCompleteListener {

    final ProgressGenerator progressGenerator = new ProgressGenerator(this);

    // 帐号
    @ViewInject(R.id.account)
    private ContainsEmojiEditText account;

    // 密码
    @ViewInject(R.id.password)
    private ContainsEmojiEditText password;

    // 登录按钮
    @ViewInject(R.id.btnSignIn)
    private ActionProcessButton btnSignIn;

    // 没有帐号
    @ViewInject(R.id.tv_no_account)
    private TextView tvNoAccount;

    // 忘记密码
    @ViewInject(R.id.tv_forget_password)
    private TextView tvForgetPassword;


    public Context mContext;

    private String loginMessage;

    private String qqMessage;

    public static LoginActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createLoadingDialog(LoginActivity.this);
        // 注入view和事件
        ViewUtils.inject(this);

        activity = LoginActivity.this;
        mContext = LoginActivity.this;



        initView();

        initEvent();


    }

    public void initView() {

    }


    public void initEvent() {
        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneAccount = account.getText().toString().trim();
                String passWord = password.getText().toString().trim();
                if (StringUtils.isEmpty(phoneAccount)) {
                    ToastUtils.show(getApplicationContext(), "账号不能为空", ToastUtils.TOAST_SHORT);
                    return;
                } else if (StringUtils.isEmpty(passWord)) {
                    ToastUtils.show(getApplicationContext(), "密码不能为空", ToastUtils.TOAST_SHORT);
                    return;
                } else {
                    btnSignIn.setEnabled(false);
                    login();
                }
            }
        });

        tvForgetPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(TeacherLoginActivity.class);
            }
        });
    }


    protected void login() {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                if (NetUtils.isConnected(mContext)) {
//					loginMessage = application.phoneLoginCheck(account.getText().toString().trim(), password.getText().toString().trim());
                    return true;
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (result) {
                    try {
                        JSONObject jsonObject = new JSONObject(loginMessage);
                        Gson gson = new Gson();
                        UserInfo userInfo = gson.fromJson(loginMessage, UserInfo.class);

                        if (userInfo.getLoginState()) {
                            progressGenerator.start(btnSignIn);
                            btnSignIn.setEnabled(false);
                            account.setEnabled(false);
                            password.setEnabled(false);
                            SPUtils.put(mContext, Contants.LOGIN_TYPE, 4);
                            SPUtils.put(mContext, Contants.USER_ID, userInfo.getId());
                        } else {
                            btnSignIn.setEnabled(true);
                            ToastUtils.show(getApplicationContext(), jsonObject.optString("errorMessage"), ToastUtils.TOAST_SHORT);
                        }
                    } catch (JSONException e) {
                        btnSignIn.setEnabled(true);
                        e.printStackTrace();
                    }
                } else {
                    btnSignIn.setEnabled(true);
                    ToastUtils.show(getApplicationContext(), R.string.no_net, ToastUtils.TOAST_SHORT);
                }
            }
        });
    }

    @Override
    public void onComplete() {
        finish();
    }
}