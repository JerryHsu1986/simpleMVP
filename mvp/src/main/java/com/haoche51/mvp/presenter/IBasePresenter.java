package com.haoche51.mvp.presenter;

import com.haoche51.mvp.view.IBaseView;

/**
 * @author HaiboXu on 2018/10/17
 */
public interface IBasePresenter<V extends IBaseView> {
    void attachView(V view);
    void destroyView();
}
