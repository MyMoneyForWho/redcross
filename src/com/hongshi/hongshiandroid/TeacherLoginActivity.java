package com.hongshi.hongshiandroid;

import android.app.Activity;
import android.os.Bundle;

import com.lidroid.xutils.ViewUtils;

public class TeacherLoginActivity extends Activity {

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

    }


}
