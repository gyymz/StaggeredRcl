package com.example.chenlian.staggeredrcl.adapter;

import android.content.Context;

import com.example.chenlian.staggeredrcl.BaseApplication;
import com.example.chenlian.staggeredrcl.model.GirlItemData;
import com.example.chenlian.staggeredrcl.R;
import com.example.chenlian.staggeredrcl.widgets.ViewHolder;
import com.example.chenlian.staggeredrcl.util.ImageLoader;
import com.example.chenlian.staggeredrcl.widgets.ScaleImageView;

import java.util.List;

/**
 * Created by cl on 2018/5/3.
 */

public class GirlItemAdapter extends BaseAdapter<GirlItemData> {

    public GirlItemAdapter(Context context, List<GirlItemData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, GirlItemData girlItemData) {
        ScaleImageView imageView = holder.getView(R.id.girl_item_iv);
        imageView.setInitSize(girlItemData.getWidth(), girlItemData.getHeight());
        ImageLoader.load(BaseApplication.getContext(),
                girlItemData.getUrl(), imageView);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_girl_layout;
    }
}