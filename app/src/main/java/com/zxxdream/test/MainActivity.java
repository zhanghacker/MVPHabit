package com.zxxdream.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zhangxiaoxiao.helperlibrary.base.HelperConfig;
import com.zhangxiaoxiao.helperlibrary.network.RetrofitUtil;
import com.zhangxiaoxiao.helperlibrary.utils.PermissionsTool;
import com.zhangxiaoxiao.helperlibrary.utils.RxUtils;

public class MainActivity extends AppCompatActivity {


    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        HelperConfig.init(this);
        //网络请求样例
        findViewById(R.id.bt).setOnClickListener(v -> {
                UserApi userApi = new RetrofitUtil.Builder("http://47.106.132.104/").build(UserApi.class);
                userApi.login("15880858837")
                        .compose(RxUtils.schedulersTransformer())
                        .subscribe(entity->{
                            Log.e("tag",entity);
                        },ex->{
                            Log.e("tag",ex.toString());
                        });
        });
        //权限请求样例
        findViewById(R.id.bt_permission).setOnClickListener(v -> {
            PermissionsTool.with(this)
                    .addPermission( "android.permission.READ_EXTERNAL_STORAGE")
                    .addPermission( "android.permission.WRITE_EXTERNAL_STORAGE" )
                    .initPermission();
        });
    }
}
