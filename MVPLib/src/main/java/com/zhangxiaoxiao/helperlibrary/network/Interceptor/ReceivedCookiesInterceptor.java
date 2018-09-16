package com.zhangxiaoxiao.helperlibrary.network.Interceptor;

import android.annotation.SuppressLint;

import com.zhangxiaoxiao.helperlibrary.constants.GlobalConstans;
import com.zhangxiaoxiao.helperlibrary.base.HelperConfig;
import com.zhangxiaoxiao.helperlibrary.utils.SP;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * ProjectName:  Test
 * Author:  xxzhang
 * CreateAt:  2018/9/3  17:10
 * Description:  接收数据做cookie保存
 * Copyright © itzxx Inc. All Rights Reserved
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    @SuppressLint("CheckResult")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Observable.fromIterable(originalResponse.headers("Set-Cookie"))
                    .map(new Function<String, String>() {
                        @Override
                        public String apply(String s) throws Exception {
                            String[] cookieArray = s.split(";"); // JSESSIONID=aaaQElHouiqmGh-oaQCtv; path=/
                            return cookieArray[0];
                        }
                    })
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String cookie) throws Exception {
                                cookieBuffer.append(cookie).append(";");
                        }
                    });
            SP.getInstance(HelperConfig.getContext()).putString(GlobalConstans.SET_COOKIE,cookieBuffer.toString());
        }
        return originalResponse;
    }
}
