package com.haoche51.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.haoche51.mvp.app.AppConfig;
import com.haoche51.mvp.presenter.RxPresenter;
import com.haoche51.mvp.utils.PresenterUtils;
import com.haoche51.mvp.view.IBaseView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author HaiboXu on 2018/11/6
 * @github https://github.com/JerryHsu1986
 */
public abstract  class RxBaseActivity<P extends RxPresenter> extends RxAppCompatActivity implements IBaseView{

    protected P mPresenter;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        mPresenter = PresenterUtils.getParadigm(this,0);
        if (mPresenter !=null) {
            mPresenter.attachView(this);
        }
        mUnbinder = ButterKnife.bind(this);
    }


    /*get content view Id*/
    protected abstract int getContentViewId();
    /*init page data*/
    protected abstract void init();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) mUnbinder.unbind();
        if (mPresenter !=null) mPresenter.destroyView();
        AppConfig.removeActivity(this);

    }

}
