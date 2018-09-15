package com.zhangxiaoxiao.helperlibrary.network.Interceptor;

import android.annotation.SuppressLint;

import com.zhangxiaoxiao.helperlibrary.constants.GlobalConstans;
import com.zhangxiaoxiao.helperlibrary.base.HelperConfig;
import com.zhangxiaoxiao.helperlibrary.utils.SP;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ProjectName:  Test
 * Author:  xxzhang
 * CreateAt:  2018/9/3  16:55
 * Description:  在要请求时候做拦截，添加本地储存key
 * Copyright © itzxx Inc. All Rights Reserved
 */
public class AddCookiesInterceptor implements Interceptor {


    @SuppressLint("CheckResult")
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        Observable.just(SP.getInstance(HelperConfig.getContext()).getString(GlobalConstans.SET_COOKIE,""))
                .subscribe(cookie -> {
                    //添加cookie
                    builder.addHeader("Set-Cookie", cookie);
                });
        return chain.proceed(builder.build());
    }

}
