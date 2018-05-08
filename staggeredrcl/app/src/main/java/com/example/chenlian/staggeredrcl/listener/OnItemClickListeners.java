package com.example.chenlian.staggeredrcl.listener;

import android.support.v7.widget.RecyclerView;

import com.example.chenlian.staggeredrcl.widgets.ViewHolder;

/**
 * Created by cl on 2018/5/3.
 */

public interface OnItemClickListeners<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position);
}
