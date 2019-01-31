package com.itzxx.mvphabit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * ProjectName:  Test
 * Author:  xxzhang
 * CreateAt:  2018/9/3  15:58
 * Description:  类描述
 * Copyright © itzxx Inc. All Rights Reserved
 */
public interface UserApi {
    /**
     * 登录
     */
    @GET("getSongPoetry")
    Observable<String> reqData(@Query("page") int page, @Query("count") int count);
}
