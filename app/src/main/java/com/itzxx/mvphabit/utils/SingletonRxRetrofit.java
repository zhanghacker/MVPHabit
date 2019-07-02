package com.itzxx.mvphabit.utils;

import com.itzxx.helperlibrary.network.RetrofitUtil;
import com.itzxx.mvphabit.UserApi;

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
