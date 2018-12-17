package com.zhangxiaoxiao.helperlibrary.base;

import android.annotation.SuppressLint;
import android.content.Context;

import com.zhangxiaoxiao.helperlibrary.utils.CrashUtils;

import java.io.File;

/**
 * ProjectName:  helperHabis
 * Author:  xxzhang
 * CreateAt:  2018/9/3  17:02
 * Description:  初始化数据
 * Copyright © itzxx Inc. All Rights Reserved
 */
public class HelperConfig {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static Builder init(Context context) {
        HelperConfig.context = context.getApplicationContext();
        return Builder.create();
    }

    public static class Builder{

        private Builder() {
        }
        /**
         * 添加捕捉异常，无监听回调
         * 文件名称："MVPHabit-Exception-"+time + ".txt"  文件名称
         * 默认路径：SDCard/Android/data/你的应用包名/cache/目录 ->默认的扑捉异常路径
         * @param t 判断是否自定义路径
         * @param <T>  String 或者 File
         * @return
         */
        @SuppressLint("MissingPermission")
        public <T>Builder  addCrashException(T... t){
            try {
                if(t.length>0){
                    if(t[0] instanceof String){
                        CrashUtils.init((String)t[0]);
                    }else if(t[0] instanceof File){
                        CrashUtils.init((File)t[0]);
                    }
                }else {
                    CrashUtils.init();
                }
            } catch (Exception e) {
                throw new RuntimeException("MVPHabit：初始化异常捕捉错误！！！");
            }
            return this;
        }

        /**
         * 添加捕捉异常，有监听监听回调
         * 文件名称："MVPHabit-Exception-"+time + ".txt"  文件名称
         * 默认路径：SDCard/Android/data/你的应用包名/cache/目录 ->默认的扑捉异常路径
         * @param t 判断是否自定义路径->crashDirPath
         * @param m 监听回调
         * @param <T> String 或者 File
         * @param <M> OnCrashListener
         * @return
         */
        @SuppressLint("MissingPermission")
        public <T,M extends CrashUtils.OnCrashListener>Builder  addCrashException(T t, M m) {
            try {
                if(t instanceof String){
                    CrashUtils.init((String)t, m);
                }else if(t instanceof File){
                    CrashUtils.init((File)t,m);
                }
            } catch (Exception e) {
                throw new RuntimeException("MVPHabit：初始化异常捕捉错误！！！");
            }
            return this;
        }

        public  static Builder create(){
            return new Builder();
        }
    }
}
