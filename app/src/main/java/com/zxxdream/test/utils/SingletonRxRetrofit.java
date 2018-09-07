package com.zxxdream.test.utils;

import com.zhangxiaoxiao.helperlibrary.network.RetrofitUtil;
import com.zxxdream.test.UserApi;

/**
 * ProjectName:  HelperHabit
 * Author:  xxzhang
 * CreateAt:  2018/9/6  9:28
 * Description:  类描述
 * Copyright © andacx Inc. All Rights Reserved
 */
public class SingletonRxRetrofit {

    private static SingletonRxRetrofit okHttpClient;
    private UserApi mUserApi;


    public static SingletonRxRetrofit getInstance() {
        if (okHttpClient == null) {
            okHttpClient = new SingletonRxRetrofit();
        }
        return okHttpClient;
    }

    public SingletonRxRetrofit(){
        mUserApi = RetrofitUtil.Builder.create("http://47.106.132.104/").build(UserApi.class);
    }

    public UserApi getApi() {
        return mUserApi;
    }
}
