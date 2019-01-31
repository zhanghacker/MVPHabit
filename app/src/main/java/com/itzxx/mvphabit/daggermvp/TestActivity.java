package com.itzxx.mvphabit.daggermvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itzxx.helperlibrary.base.BaseDaggerActivity;
import com.itzxx.mvphabit.R;
import com.itzxx.mvphabit.base.MyApplication;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class TestActivity extends BaseDaggerActivity<TestPresenter> implements TestContract.View {



    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.textView)
    TextView mTextView;

    @Inject
    TestPresenter mTestPresenter;



    public static void action(Context context){
        Intent intent = new Intent(context,TestActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void initInjector() {
        super.initInjector();
        DaggerTestComponent.builder()
                .testModule(new TestModule(this))
                .appComponent(MyApplication.getAppComponent(this))
                .build().inject(this);
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
    }

    @Override
    public void initData() {

    }

    @Override
    public void loadDataSuccess(String str) {
        mTextView.setText(str);
        T(str);
    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        mTestPresenter.getData();
    }
}
