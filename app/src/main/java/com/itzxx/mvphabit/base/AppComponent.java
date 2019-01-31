package com.itzxx.mvphabit.base;

import com.itzxx.helperlibrary.utils.SP;
import com.itzxx.mvphabit.UserApi;

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
