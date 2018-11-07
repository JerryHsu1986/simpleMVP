package com.haoche51.mvp.presenter;

import com.haoche51.mvp.model.IBaseModel;
import com.haoche51.mvp.view.IBaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.ArrayCompositeSubscription;

/**
 * @author HaiboXu on 2018/10/17
 * @github https://github.com/JerryHsu1986
 * Presenter base on Rx;
 */
public class RxPresenter<T extends IBaseView,M extends IBaseModel> implements IBasePresenter<T> {

    private T mView;
    private M mModel;
    private CompositeDisposable mCompositeDisposable;



    protected void subscribe(Disposable disposable){
        if (mCompositeDisposable == null) mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(disposable);
    }

    protected void unsubscribe(){
        if (mCompositeDisposable !=null) mCompositeDisposable.clear();
    }
    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void destroyView() {
        this.mView = null;
        unsubscribe();
    }
}
