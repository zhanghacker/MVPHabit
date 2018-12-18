package com.zxxdream.test.base;

import com.zhangxiaoxiao.helperlibrary.utils.SP;
import com.zxxdream.test.UserApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ProjectName:  MVPHabit
 * Author:  xxzhang
 * CreateAt:  2018/12/17  17:48
 * Description:  类描述
 * Copyright © itzxx Inc. All Rights Reserved
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    SP sp();

    UserApi userApi();

    void inject(MyApplication myApplication);
}
