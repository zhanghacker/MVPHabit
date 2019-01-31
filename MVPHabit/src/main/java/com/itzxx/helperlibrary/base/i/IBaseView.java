package com.itzxx.helperlibrary.base.i;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public interface IBaseView {

    View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    View getView();

    int getContentLayout();

    void bindView(View view, Bundle savedInstanceState);

    void initData();

}
