package com.haoche51.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author HaiboXu on 2018/11/6
 * @github https://github.com/JerryHsu1986
 */
public abstract class BaseFragment extends RxFragment {

    private boolean isCreated = false;

    private Unbinder mUnbinder ;

    private String FRAGMENT_STATE_IS_HIDDEN = "fragment_status_is_hidden";

    private  View rootView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveOverlapping(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(getLayoutId(),container,false);
        }
        ViewGroup viewGroup = (ViewGroup) rootView.getParent();
        if (viewGroup != null){
            viewGroup.removeView(rootView);
        }
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this,view);
        initView(view);
        if (savedInstanceState  == null){
            if (!isHidden()){
                isCreated = true;
                lazyLoad();
            }
        }else {
            if (!savedInstanceState.getBoolean(FRAGMENT_STATE_IS_HIDDEN)){
                isCreated = true;
                lazyLoad();
            }
        }

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden() && !isCreated){
            isCreated = true;
            lazyLoad();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) mUnbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FRAGMENT_STATE_IS_HIDDEN,isHidden());
    }


    private void resolveOverlapping(Bundle saveInstanceState ){
        if (saveInstanceState == null) return ;
        boolean isHidden = saveInstanceState.getBoolean(FRAGMENT_STATE_IS_HIDDEN);
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (isHidden){
            ft.hide(this);
        }else {
            ft.show(this);
        }
        ft.commitAllowingStateLoss();
    }


    /*get content view layout id*/
    protected abstract int getLayoutId();

    protected abstract void initView(View v);
    /*初始化*/
    protected abstract void init();
    /*懒加载*/
    protected  void lazyLoad(){init();}
}
