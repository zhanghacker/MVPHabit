package com.itzxx.helperlibrary.base;

import com.itzxx.helperlibrary.base.i.IBasePresenter;

import io.reactivex.disposables.CompositeDisposable;

/**
 * ProjectName:  HelperHabit
 * Author:  xxzhang
 * CreateAt:  2018/9/5  16:05
 * Description:  管理生命周期
 * Copyright © itzxx Inc. All Rights Reserved
 */
public class BasePresenter implements IBasePresenter {

    protected CompositeDisposable mCompositeDisposable;

    public BasePresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView() {
    }

    @Override
    public void detachView() {
        mCompositeDisposable.clear();
    }
}
