package com.zxxdream.test.daggermvp;

import com.zhangxiaoxiao.helperlibrary.base.BasePresenter;
import com.zhangxiaoxiao.helperlibrary.utils.RxUtils;
import com.zxxdream.test.utils.SingletonRxRetrofit;

import javax.inject.Inject;


public class TestPresenter extends BasePresenter implements TestContract.Presenter {

    private TestContract.View mView;

    @Inject
    public TestPresenter(TestContract.View view) {
        this.mView = view;
    }


    @Override
    public void getData() {
        //mCompositeDisposable管理生命周期
        mCompositeDisposable.add(SingletonRxRetrofit.getInstance()
                .getApi().login("15880858837")
                .compose(RxUtils.schedulersTransformer())
                .subscribe((String entity) -> mView.loadDataSuccess(entity),
                        ex -> mView.loadDataSuccess(ex.toString())))
       ;
    }
}
