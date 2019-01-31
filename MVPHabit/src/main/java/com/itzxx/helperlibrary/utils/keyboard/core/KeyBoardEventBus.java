package com.itzxx.helperlibrary.utils.keyboard.core;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.itzxx.helperlibrary.utils.keyboard.callback.GlobalLayoutListenerImp;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * ProjectName:  helperHabit
 * Author:  xxzhang
 * CreateAt:  2019-1-31  15:58
 * Description:  用于管理键盘事件
 * Copyright © itzxx Inc. All Rights Reserved
 */
public final class KeyBoardEventBus {

    /**
     * TAG
     */
    private static final String TAG = "MVPHabit";
    /**
     * 单例
     */
    private static KeyBoardEventBus instance = new KeyBoardEventBus();

    /**
     * 用于缓存监听回调信息
     */
    private Map<Object, GlobalLayoutListenerImp> callbackCache = new HashMap<>();

    /**
     * 状态栏高度
     */
    private int statusBarHeight = -1;

    /**
     * 全屏时的高度
     */
    private int fullScreenHeight = -1;

    /**
     * 私有化构造函数
     */
    private KeyBoardEventBus() {

    }

    /**
     * 用于外部获取KeyBoardEventBus 对象
     *
     * @return 返回 KeyBoardEventBus 对象
     */
    public static KeyBoardEventBus getDefault() {
        if (instance == null) {
            synchronized (KeyBoardEventBus.class) {
                if (instance == null) {
                    instance = new KeyBoardEventBus();
                }
            }
        }
        return instance;
    }


    /**
     * 用于注册键盘监听，此方法适用于 View、Dialog、Fragement(v4、app)、FragementActivity、Activity
     *
     * @param object 需要监听的类（）
     */
    public void register(Object object) {
        //获取失败则直接停止，反之进行注册
        Activity activity = getActivity(object);
        if (activity == null) {
            print("获取activity失败！");
            return;
        }
        register(activity, object);
    }

    /**
     * 此方法区别于 {@link #register(Object)} ,之前的方法会限制注册的类型，当前的不会限制类型
     *
     * @param activity 宿主activity
     * @param object   监听的类
     */
    public void register(Activity activity, Object object) {

        if (activity == null || object == null) {
            print("activity或object为null!");
            return;
        }

        GlobalLayoutListenerImp imp = callbackCache.get(activity);
        if (imp == null) {
            imp = new GlobalLayoutListenerImp(activity);
        }
        //移除对应的回调
        imp.addCallback(object);

        //如果不是空
        if (!imp.isEmpty()) {
            //设置监听
            activity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(imp);
            //缓存
            callbackCache.put(activity, imp);
        }
    }

    /**
     * 反注册
     *
     * @param object 取消监听的类
     */
    public void unregister(Object object) {
        //获取失败则直接停止，反之进行反注册
        Activity activity = getActivity(object);
        if (activity == null) {
            print("获取activity失败！");
            return;
        }
        unregister(activity, object);
    }

    /**
     * 反注册
     *
     * @param activity 宿主activity
     * @param object   监听的类
     */
    public void unregister(Activity activity, Object object) {

        if (activity == null || object == null) {
            print("activity或object为null!");
            return;
        }

        GlobalLayoutListenerImp imp = callbackCache.get(activity);
        if (imp == null) {
            return;
        }
        //移除对应的回调
        imp.removeCallback(object);

        //如果回调集合为空则直接移除
        if (imp.isEmpty()) {
            //去掉监听
            activity.getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(imp);
            //释放监听内缓存、引用
            imp.release();
            //释放缓存
            callbackCache.remove(activity);
        }


    }


    /**
     * 获取对应View、Dialog、Fragement(v4、app)、FragementActivity、Activity 的Activity
     * (如果Object为null或者不是支持的类型则返回null)
     *
     * @param object 需要获取的类
     * @return 返回对应的activity
     */
    public Activity getActivity(Object object) {

        if (object == null) {
            return null;
        }

        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof android.support.v4.app.Fragment) {
            return ((android.support.v4.app.Fragment) object).getActivity();
        } else if (object instanceof Dialog) {
            return (Activity) ((Dialog) object).getContext();
        } else if (object instanceof View) {
            return (Activity) ((View) object).getContext();
        }

        return null;

    }

    /**
     * 用于打印信息
     *
     * @param info 待打印的内容
     */
    private void print(String info) {
        Log.e(TAG, info);
    }

    /**
     * 用于获取状态栏高度
     *
     * @return 状态栏高度
     */
    public int getStatusBarHeight(Activity activity) {
        if (statusBarHeight == -1) {
            statusBarHeight = realGetStatusBarHeight(activity);
            return statusBarHeight;
        }
        return statusBarHeight;
    }

    /**
     * 使用反射获取状态栏高度
     *
     * @return 状态栏高度
     */
    private int realGetStatusBarHeight(Activity activity) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            int dimensionPixelSize = activity.getResources().getDimensionPixelSize(x);
            return dimensionPixelSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 用于获取全屏时的整体高度
     *
     * @return 屏幕高度
     */
    public int getFullScreenHeight(Activity activity) {
        if (fullScreenHeight == -1) {
            fullScreenHeight = realGetFullScreenHeight(activity);
            return fullScreenHeight;
        }
        return fullScreenHeight;
    }

    /**
     * 用于获取全屏高度
     *
     * @return 屏幕高度
     */
    private int realGetFullScreenHeight(Activity activity) {
        WindowManager wm = activity.getWindowManager();
        return wm.getDefaultDisplay().getHeight();
    }


}
