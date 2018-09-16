package com.zhangxiaoxiao.helperlibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhangxiaoxiao.helperlibrary.base.i.IBaseView;
import com.zhangxiaoxiao.helperlibrary.utils.T;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * ProjectName:  helperHabit
 * Author:  xxzhang
 * CreateAt:  2018/9/3  15:58
 * Description:  集成dagger继承基类
 * Copyright © itzxx Inc. All Rights Reserved
 */
public abstract class BaseActivity<T1 extends BasePresenter> extends AppCompatActivity implements IBaseView {

    protected View mRootView;
    Unbinder unbinder;

    @Nullable
    protected T1 mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = createView(null, null, savedInstanceState);
        setContentView(mRootView);
        AppManager.getAppManager().addActivity(this);
        mPresenter = createPresenter();//创建presenter
        bindView(mRootView, savedInstanceState);
        initData();//初始化数据
        initListener();//初始化监听
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(getContentLayout(), container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public View getView() {
        return mRootView;
    }

    /**
     * 用于有初始化MVP的p层
     */
    protected T1 createPresenter(){
        return null;
    }

    /**
     * 初始化监听
     */
    protected void initListener(){
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        AppManager.getAppManager().removeActivity(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 继承可直接使用对话框
     * @param string
     */
    protected void T(String string) {
        T.showShort(this, string);
    }

}
