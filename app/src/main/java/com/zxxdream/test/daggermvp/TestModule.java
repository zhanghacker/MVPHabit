package com.zxxdream.test.daggermvp;

import dagger.Module;
import dagger.Provides;

/**
 * 功能描述：
 */
@Module
public class TestModule {

    private TestContract.View mView;

    public TestModule(TestContract.View view) {
        mView = view;
    }

    @Provides
    TestContract.View provideTestContractView() {
        return mView;
    }

}
