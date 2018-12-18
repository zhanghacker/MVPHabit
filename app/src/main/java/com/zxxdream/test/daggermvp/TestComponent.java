package com.zxxdream.test.daggermvp;

import com.zhangxiaoxiao.helperlibrary.annotation.ActivityScoped;
import com.zxxdream.test.base.AppComponent;

import dagger.Component;

/**
 * ProjectName:  HelperHabit
 * Author:  xxzhang
 * CreateAt:  2018/9/7  16:08
 * Description:  类描述
 * Copyright © itzxx Inc. All Rights Reserved
 */
@ActivityScoped
@Component(modules = {TestModule.class},dependencies = AppComponent.class)
public interface TestComponent {

    void inject(TestActivity activity);

}
