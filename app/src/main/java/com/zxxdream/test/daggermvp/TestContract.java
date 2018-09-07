package com.zxxdream.test.daggermvp;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/7 .
 */
public interface TestContract {

    interface View{
        void loadDataSuccess(String str);
    }

    interface Presenter{
        void getData();
    }

}
