package com.example.chenlian.staggeredrcl.Presenter;

import com.example.chenlian.staggeredrcl.net.ApiService;
import com.example.chenlian.staggeredrcl.net.GirlService;
import com.example.chenlian.staggeredrcl.util.JsoupUtil;
import com.example.chenlian.staggeredrcl.util.RxManager;
import com.example.chenlian.staggeredrcl.util.RxSubscriber;
import com.example.chenlian.staggeredrcl.widgets.GirlItemView;

/**
 * Created by cl on 2018/5/3.
 */

public class GirlItemPresenter extends BasePresenter<GirlItemView> {

    public GirlItemPresenter(GirlItemView view) {
        super(view);
    }

    public void getGirlItemData(String cid, int page) {
        mSubscription = RxManager.getInstance()
                .doSubscribe(ApiService.getInstance().initService(GirlService.class).getGirlItemData(cid, page),
                        new RxSubscriber<String>(false) {
                            @Override
                            protected void _onNext(String s) {
                                mView.onSuccess(JsoupUtil.parseGirls(s));
                            }

                            @Override
                            protected void _onError() {
                                mView.onError();
                            }
                        });
    }
}
