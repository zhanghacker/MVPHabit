package com.itzxx.helperlibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itzxx.helperlibrary.base.i.IBaseView;
import com.itzxx.helperlibrary.utils.T;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ProjectName:  helperHabit
 * Author:  xxzhang
 * CreateAt:  2018/9/3  15:58
 * Description:  集成dagger继承基类
 * Copyright © itzxx Inc. All Rights Reserved
 */
public abstract class BaseDaggerFragment<T1 extends BasePresenter> extends android.support.v4.app.Fragment implements IBaseView {

    protected Context mContext;
    protected View mRootView;

    Unbinder unbinder;

    @Nullable
    @Inject
    protected T1 mPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        } else {
            mRootView = createView(inflater, container, savedInstanceState);
        }
        initInjector();
        AppManager.getAppManager().addFragment(this);
        mContext = mRootView.getContext();
        initData();//初始化数据
        return mRootView;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view, savedInstanceState);
    }


    @Nullable
    @Override
    public View getView() {
        return mRootView;
    }

    /**
     * dagger注解框架
     */
    protected void initInjector(){

    }

    protected void T(String string) { T.showShort(mContext, string); }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        AppManager.getAppManager().removeFragment(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
