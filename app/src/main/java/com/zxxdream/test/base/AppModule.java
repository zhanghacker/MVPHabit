package com.zxxdream.test.base;

import android.content.Context;

import com.zhangxiaoxiao.helperlibrary.network.RetrofitUtil;
import com.zhangxiaoxiao.helperlibrary.utils.SP;
import com.zxxdream.test.UserApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ProjectName:  MVPHabit
 * Author:  xxzhang
 * CreateAt:  2018/12/17  17:43
 * Description:  MyApplication
 * Copyright © itzxx Inc. All Rights Reserved
 */
@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }


    /**
     * 设置全局单例
     * @param context
     * @return
     */
    @Provides
    @Singleton
    public SP provideSP(Context context){
        return SP.getInstance(context);
    }

    /**
     * 设置全局单例网络请求
     * @return
     */
    @Provides
    @Singleton
    public  UserApi provideUserApi(){
        return RetrofitUtil.Builder.create("http://47.106.132.104/").build(UserApi.class);
    }
}
