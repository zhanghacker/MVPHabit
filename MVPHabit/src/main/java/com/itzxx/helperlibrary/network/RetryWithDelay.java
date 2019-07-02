package com.itzxx.helperlibrary.network;

import android.support.annotation.IntDef;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;

/**
 * Desc:rxjava错误重新请求
 * <p>
 * Author: 张潇潇
 * Date: 2019-04-11
 * Copyright: Copyright (c) 2010-2019
 * Company: @咪咕动漫
 * Updater:
 * Update Time:
 * Update Comments:
 */
public class RetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {

    /**
     * 请求次数
     */
    private final int maxRetries;

    /**
     * 延迟请求时间:单位s
     */
    private int retryDelaySeconds;


    /**
     * 原始时间延迟请求时间:单位s
     */
    private int retryOriginDelaySeconds;


    /**
     * 当前请求次数
     */
    private int retryCount;

    @RetryWithDelay.RetryMode.Type
    private int mType;

    public RetryWithDelay(int maxRetries, int retryDelaySeconds, @RetryWithDelay.RetryMode.Type int type) {
        this.maxRetries = maxRetries;
        this.retryDelaySeconds = retryDelaySeconds;
        this.retryOriginDelaySeconds = retryDelaySeconds;
        this.mType = type;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable
                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        if (++retryCount <= maxRetries) {
                            if (mType == RetryMode.ADD) {
                                retryDelaySeconds += 1;
                            } else if (mType == RetryMode.POW) {
                                retryDelaySeconds = (int) (retryOriginDelaySeconds + Math.pow(retryCount + 1, 2));
                            }
                            // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                            return Observable.timer(retryDelaySeconds,
                                    TimeUnit.SECONDS);
                        }
                        // Max retries hit. Just pass the error along.
                        return Observable.error(throwable);
                    }
                });
    }

    interface RetryMode {
        /**
         * 平均固定时间：1 1 1
         */
        int AVERAGE = 1;

        /**
         * 时间递加：1 2 3
         */
        int ADD = 2;

        /**
         * 2次方:1 4 9
         */
        int POW = 3;

        @IntDef(value = {AVERAGE, ADD, POW})
        @interface Type {
        }
    }
}

