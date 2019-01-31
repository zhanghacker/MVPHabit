package com.itzxx.mvphabit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.itzxx.helperlibrary.base.BaseActivity;
import com.itzxx.helperlibrary.constants.PermissionConstants;
import com.itzxx.helperlibrary.network.RetrofitUtil;
import com.itzxx.helperlibrary.utils.PermissionUtils;
import com.itzxx.helperlibrary.utils.RxUtils;
import com.itzxx.mvphabit.daggermvp.TestActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName:  helperHabis
 * Author:  xxzhang
 * CreateAt:  2018/9/3  17:02
 * Description:  库代码样例
 * Copyright © itzxx Inc. All Rights Reserved
 */
public class MainActivity extends BaseActivity {

    public static String TAG = "MVPHabit";

    @BindView(R.id.bt)
    Button mBt;
    @BindView(R.id.bt_permission)
    Button mBtPermission;

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.bt, R.id.bt_permission, R.id.dagger_mvp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt:
                //网络请求样例
                UserApi userApi = RetrofitUtil.Builder.create("https://api.apiopen.top/").build(UserApi.class);
                userApi.reqData(1,20)
                        .compose(RxUtils.schedulersTransformer())
                        .subscribe(entity -> T(entity),
                                ex -> T(ex.toString()));
                break;
            case R.id.bt_permission:
                //权限请求样例
                PermissionUtils.permission(PermissionConstants.CAMERA,PermissionConstants.STORAGE)
                        .callback(new PermissionUtils.FullCallback() {
                            @Override
                            public void onGranted(List<String> permissionsGranted) {
                                T("ok");
                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                T("no");
                            }
                        }).request();
                break;
            case R.id.dagger_mvp:
                //MVP+Dagger+Rxjava+Retrofit的架构demo用例
                TestActivity.action(this);
                break;
        }
    }
}
