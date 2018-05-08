package com.example.chenlian.staggeredrcl;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chenlian.staggeredrcl.Presenter.GirlItemPresenter;
import com.example.chenlian.staggeredrcl.adapter.GirlAdapter;
import com.example.chenlian.staggeredrcl.adapter.GirlItemAdapter;
import com.example.chenlian.staggeredrcl.listener.OnItemClickListeners;
import com.example.chenlian.staggeredrcl.listener.OnLoadMoreListener;
import com.example.chenlian.staggeredrcl.model.GirlItemData;
import com.example.chenlian.staggeredrcl.net.DataService;
import com.example.chenlian.staggeredrcl.widgets.GirlItemView;
import com.example.chenlian.staggeredrcl.widgets.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GirlItemView,SwipeRefreshLayout.OnRefreshListener{

    private int PAGE_COUNT = 1;
    private String mSubtype = "4";
    private int mTempPageCount = 2;

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private GirlAdapter mAdapter;

    private boolean isItemDeleted = false;

    private ArrayList<GirlItemData> mAllList = new ArrayList<>();

    private boolean isLoadMore;//是否是底部加载更多

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshLayout = findViewById(R.id.type_item_swipfreshlayout);
        mRecyclerView = findViewById(R.id.type_item_recyclerview);
        initView();
        initData();
    }

    private void initView(){
        EventBus.getDefault().register(this);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(MainActivity.this);
        //实现首次自动显示加载提示
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        mAdapter = new GirlAdapter();
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setPreLoadNumber(3);

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (PAGE_COUNT == mTempPageCount) {
                    return;
                }
                isLoadMore = true;
                PAGE_COUNT = mTempPageCount;
                initData();
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                delete(position);
                return false;
            }
        });

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//可防止Item切换
        mRecyclerView.setLayoutManager(layoutManager);

        //曾经删除过Item，则滑到顶部的时候刷新布局，避免错乱。
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isItemDeleted){
                    StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                    int[] firstVisibleItem = null;
                    firstVisibleItem = layoutManager.findFirstVisibleItemPositions(firstVisibleItem);
                    if (firstVisibleItem != null && firstVisibleItem[0] == 0) {
                        if (mAdapter!=null) {
                            isItemDeleted = false;
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData(){
        GirlItemPresenter presenter = new GirlItemPresenter(this);
        presenter.getGirlItemData(mSubtype, PAGE_COUNT);
    }

    @Override
    public void onError() {
        if (isLoadMore) {
            mAdapter.loadMoreFail();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * 接受数据成功先在IntentService中处理长宽再填充
     * @param data
     */
    @Override
    public void onSuccess(List<GirlItemData> data) {

        DataService.startService(MainActivity.this, data, mSubtype);
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        PAGE_COUNT = 1;
        mTempPageCount = 2;
        mAllList.clear();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dataEvent(List<GirlItemData> data) {
        mAllList.addAll(data);
        setData(data);
    }

    private void setData(List<GirlItemData> data){
        int size = data.size();
        if (!data.get(0).getSubtype().equals(mSubtype)) {
            return;
        }

        if (isLoadMore) {
            if (data.size() == 0) {
                mAdapter.loadMoreFail();
            } else {
                mTempPageCount++;
                mAdapter.addData(data);
            }
        } else {
            mAdapter.setNewData(data);
            mSwipeRefreshLayout.setRefreshing(false);
        }

        if (size < 10) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(!isLoadMore);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void delete(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want delete this photo?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isItemDeleted = true;
                        mAllList.remove(position);
                        mAdapter.deleteItem(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
