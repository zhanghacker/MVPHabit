package com.itzxx.helperlibrary.utils.keyboard.callback;

/**
 * ProjectName:  helperHabit
 * Author:  xxzhang
 * CreateAt:  2019-1-31  15:58
 * Description:  用于接收键盘事件的回调
 * Copyright © itzxx Inc. All Rights Reserved
 */
public interface IkeyBoardCallback {
    /**
     * 当键盘显示时回调
     */
    public void onKeyBoardShow();

    /**
     * 当键盘隐藏时回调
     */
    public void onKeyBoardHidden();
}
