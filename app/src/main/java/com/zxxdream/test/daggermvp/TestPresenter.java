package com.zxxdream.test.daggermvp;

import com.zhangxiaoxiao.helperlibrary.base.BasePresenter;
import com.zhangxiaoxiao.helperlibrary.utils.RxUtils;
import com.zxxdream.test.UserApi;

import javax.inject.Inject;


public class TestPresenter extends BasePresenter implements TestContract.Presenter {

    private TestContract.View mView;

    UserApi mUserApi;

    @Inject
    public TestPresenter(TestContract.View view,UserApi userApi) {
        this.mView = view;
        this.mUserApi = userApi;
    }


    @Override
    public void getData() {
        //mCompositeDisposable管理生命周期
        mCompositeDisposable.add(mUserApi.login("15880858837")
                .compose(RxUtils.schedulersTransformer())
                .subscribe((String entity) -> mView.loadDataSuccess(entity),
                        ex -> mView.loadDataSuccess(ex.toString())))
       ;
    }
}
