package com.example.chenlian.staggeredrcl.Presenter;

import com.example.chenlian.staggeredrcl.widgets.IBaseView;

import rx.Subscription;

/**
 * Created by cl on 2018/5/3.
 */

public class BasePresenter<V extends IBaseView> {
    public V mView;
    protected Subscription mSubscription;

    public BasePresenter(V view){
        mView = view;
    }

    public void detach() {
        mView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}