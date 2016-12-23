package com.hongshi.hongshiandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hongshi.hongshiandroid.base.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TeacherLoginActivity extends BaseActivity {


    // 帐号
    @ViewInject(R.id.name)
    private EditText account;

    // 密码
    @ViewInject(R.id.password)
    private EditText password;

    // 登录按钮
    @ViewInject(R.id.login_teacher)
    private Button btnSignIn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        // 注入view和事件
        ViewUtils.inject(this);

        initView();

        initEvent();

    }


    public void initView(){



    }


    public void initEvent(){
        //登录按钮
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}
