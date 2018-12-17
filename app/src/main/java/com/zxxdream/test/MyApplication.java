package com.zxxdream.test;

import android.app.Application;

import com.zhangxiaoxiao.helperlibrary.base.HelperConfig;

/**
 * ProjectName:  MVPHabit
 * Author:  xxzhang
 * CreateAt:  2018/12/17  11:06
 * Description:  类描述
 * Copyright © andacx Inc. All Rights Reserved
 */
public class MyApplication extends Application{
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
    }
}
