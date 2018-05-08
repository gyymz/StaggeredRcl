package com.example.chenlian.staggeredrcl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chenlian.staggeredrcl.BaseApplication;
import com.example.chenlian.staggeredrcl.R;
import com.example.chenlian.staggeredrcl.model.GirlItemData;
import com.example.chenlian.staggeredrcl.util.ImageLoader;
import com.example.chenlian.staggeredrcl.widgets.ScaleImageView;

import java.util.List;

/**
 * Created by cl on 2018/5/3.
 */

public class GirlAdapter extends BaseQuickAdapter<GirlItemData, BaseViewHolder> {

    public GirlAdapter(){
        super(R.layout.item_girl_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, GirlItemData item) {
        ScaleImageView imageView = helper.getView(R.id.girl_item_iv);
        imageView.setInitSize(item.getWidth(), item.getHeight());
        ImageLoader.load(BaseApplication.getContext(),
                item.getUrl(), imageView);
    }

    public void deleteItem(int position){
        remove(position);
        notifyDataSetChanged();
    }
}
