package com.itzxx.helperlibrary.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ProjectName:  helperHabis
 * Author:  xxzhang
 * CreateAt:  2018/9/3  17:02
 * Description: rx生命周期切换
 * Copyright © itzxx Inc. All Rights Reserved
 */
public class RxUtils {
//    /**
//     * 生命周期绑定
//     *
//     * @param lifecycle Activity
//     */
//    public static <T> LifecycleTransformer bindToLifecycle(@NonNull Context lifecycle) {
//        if (lifecycle instanceof LifecycleProvider) {
//            return ((LifecycleProvider) lifecycle).bindToLifecycle();
//        } else {
//            throw new IllegalArgumentException("context not the LifecycleProvider type");
//        }
//
//    }
//
//    /**
//     * 生命周期绑定
//     *
//     * @param lifecycle Fragment
//     */
//    public static <T> LifecycleTransformer bindToLifecycle(@NonNull Fragment lifecycle) {
//        if (lifecycle instanceof LifecycleProvider) {
//            return ((LifecycleProvider) lifecycle).bindToLifecycle();
//        } else {
//            throw new IllegalArgumentException("fragment not the LifecycleProvider type");
//        }
//    }

    /**
     * 线程调度器
     */
    public static <T> ObservableTransformer<T, T> schedulersTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
