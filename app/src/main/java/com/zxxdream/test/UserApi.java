package com.zxxdream.test;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * ProjectName:  Test
 * Author:  xxzhang
 * CreateAt:  2018/9/3  15:58
 * Description:  类描述
 * Copyright © andacx Inc. All Rights Reserved
 */
public interface UserApi {
    /**
     * 登录
     */
    @GET("api/iot/wxauth/isregister")
    Observable<String> login(@Query("telephone") String telephone);
}
