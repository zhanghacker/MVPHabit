package com.itzxx.mvphabit.daggermvp;

import com.itzxx.helperlibrary.base.BasePresenter;
import com.itzxx.helperlibrary.utils.RxUtils;
import com.itzxx.mvphabit.UserApi;

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
        mCompositeDisposable.add(mUserApi.reqData(1,2)
                .compose(RxUtils.schedulersTransformer())
                .subscribe((String entity) -> mView.loadDataSuccess(entity),
                        ex -> mView.loadDataSuccess(ex.toString())));
    }
}
