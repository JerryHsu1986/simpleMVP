package com.haoche51.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.haoche51.mvp.app.AppConfig;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author HaiboXu on 2018/11/6
 * @github https://github.com/JerryHsu1986
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        mUnbinder = ButterKnife.bind(this);
        AppConfig.addActivity(this);
    }


    /*get content view Id*/
    protected abstract int getContentViewId();
    /*init page data*/
    protected abstract void init();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder !=null) mUnbinder.unbind();
        AppConfig.removeActivity(this);
    }
}
