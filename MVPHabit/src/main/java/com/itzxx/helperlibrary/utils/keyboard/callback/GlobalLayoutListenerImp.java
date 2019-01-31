package com.itzxx.helperlibrary.utils.keyboard.callback;

import android.app.Activity;
import android.graphics.Rect;
import android.view.ViewTreeObserver;

import com.itzxx.helperlibrary.utils.keyboard.core.KeyBoardEventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName:  helperHabit
 * Author:  xxzhang
 * CreateAt:  2019-1-31  15:58
 * Description:  用于真正的监听布局变化的回调类
 * Copyright © itzxx Inc. All Rights Reserved
 */
public class GlobalLayoutListenerImp implements ViewTreeObserver.OnGlobalLayoutListener {

    private Activity activity;
    private List<IkeyBoardCallback> ikeyBoardCallbackList;
    private final int NONE = -1;
    private final int SHOW = 1;
    private final int HIDDEN = 2;
    private int status = NONE;


    public GlobalLayoutListenerImp(Activity activity) {
        this.activity = activity;
        ikeyBoardCallbackList = new ArrayList<>();
    }

    @Override
    public void onGlobalLayout() {
        //activity为null不执行
        if (activity == null) {
            return;
        }
        //获取可视范围
        Rect r = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //获取屏幕高度
        int screenHeight = KeyBoardEventBus.getDefault().getFullScreenHeight(activity);
        //获取状态栏高度
        int statusBarHeight = KeyBoardEventBus.getDefault().getStatusBarHeight(activity);

        //获取被遮挡高度（键盘高度）(屏幕高度-状态栏高度-可视范围)
        int keyBoardHeight = screenHeight - statusBarHeight - r.height();

        //显示或者隐藏
        boolean isShowKeyBoard = keyBoardHeight >= screenHeight / 3;

        //当首次或者和之前的状态不一致的时候会回调，反之不回调(用于当状态变化后才回调，防止多次调用)
        if (status == NONE || (isShowKeyBoard && status == HIDDEN) || (!isShowKeyBoard && status == SHOW)) {
            if (isShowKeyBoard) {
                status = SHOW;
                dispatchKeyBoardShowEvent();
            } else {
                status = HIDDEN;
                dispatchKeyBoardHiddenEvent();
            }
        }
    }


    /**
     * 添加监听回调
     *
     * @param callback 监听的回调类
     */
    public void addCallback(Object callback) {
        if (ikeyBoardCallbackList == null || !(callback instanceof IkeyBoardCallback)) {
            return;
        }
        ikeyBoardCallbackList.add((IkeyBoardCallback) callback);
    }

    /**
     * 移除监听回调
     *
     * @param callback 监听的回调类
     */
    public void removeCallback(Object callback) {
        if (ikeyBoardCallbackList == null) {
            return;
        }
        ikeyBoardCallbackList.remove(callback);
    }

    /**
     * 判断是不是没有监听回调
     *
     * @return true:空 false:不空
     */
    public boolean isEmpty() {
        if (ikeyBoardCallbackList == null) {
            return true;
        }
        return ikeyBoardCallbackList.isEmpty();
    }

    /**
     * 清除内部内存引用
     */
    public void release() {
        status = NONE;
        activity = null;
        ikeyBoardCallbackList.clear();
        ikeyBoardCallbackList = null;
    }

    /**
     * 分发隐藏事件
     */
    private void dispatchKeyBoardHiddenEvent() {
        if (ikeyBoardCallbackList == null) {
            return;
        }

        for (IkeyBoardCallback callback : ikeyBoardCallbackList) {
            callback.onKeyBoardHidden();
        }
    }

    /**
     * 分发显示事件
     */
    private void dispatchKeyBoardShowEvent() {
        if (ikeyBoardCallbackList == null) {
            return;
        }

        for (IkeyBoardCallback callback : ikeyBoardCallbackList) {
            callback.onKeyBoardShow();
        }
    }


}
