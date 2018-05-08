package com.example.chenlian.staggeredrcl.widgets;

import com.example.chenlian.staggeredrcl.model.GirlItemData;

import java.util.List;

/**
 * Created by cl on 2018/5/3.
 */

public interface GirlItemView extends IBaseView{
    void onSuccess(List<GirlItemData> data);
}

