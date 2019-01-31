package com.itzxx.mvphabit.base;

import android.app.Application;
import android.content.Context;

import com.itzxx.helperlibrary.base.HelperConfig;

/**
 * ProjectName:  MVPHabit
 * Author:  xxzhang
 * CreateAt:  2018/12/17  11:06
 * Description:  全局Application
 * Copyright © itzxx Inc. All Rights Reserved
 */
public class MyApplication extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        //SDCard/Android/data/你的应用包名/cache/目录 ->默认的扑捉异常路径
        HelperConfig.init(this).addCrashException();

        //异常扑捉自定义异常路径和回调
        //HelperConfig.init(this).addCrashException(Environment.getExternalStorageDirectory(), (crashInfo, e) -> {
        //    Log.e(TAG,crashInfo);
        //});

        //在全局dagger注解
        appComponent = getAppComponent(this);
        //dagger正式注入
        appComponent.inject(this);
    }

    /**
     * 防止为空崩溃
     * @param context 上下文
     * @return
     */
    public static AppComponent getAppComponent(Context context) {
        if (null == appComponent) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(context))
                    .build();
        }
        return appComponent;
    }

}
