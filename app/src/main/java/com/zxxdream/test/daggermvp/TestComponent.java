package com.zxxdream.test.daggermvp;

import dagger.Component;

/**
 * ProjectName:  HelperHabit
 * Author:  xxzhang
 * CreateAt:  2018/9/7  16:08
 * Description:  类描述
 * Copyright © andacx Inc. All Rights Reserved
 */

@Component(modules = TestModule.class)
public interface TestComponent {
    void inject(TestActivity activity);
}
