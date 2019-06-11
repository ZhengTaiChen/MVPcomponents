package com.wwzs.component.commonres.base;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wwzs.component.commonres.R;
import com.wwzs.component.commonsdk.base.BaseActivity;
import com.wwzs.component.commonsdk.entity.PageBean;
import com.wwzs.component.commonsdk.entity.ResultBean;
import com.wwzs.component.commonsdk.mvp.IPresenter;

import java.util.List;

public abstract class BaseRecyclerActivity<P extends IPresenter> extends BaseActivity<P> implements OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    protected RecyclerView publicRlv;
    protected SmartRefreshLayout publicSrl;
    protected BaseQuickAdapter mAdapter;
    protected TextView mToolbarTitle;
    protected Toolbar mToolbar;
    protected int mTotalPage;
    protected int mCurrentPage = 1;


    public void initRecyclerView(@NonNull BaseQuickAdapter mAdapter) {
        publicRlv = findViewById(R.id.public_rlv);
        publicSrl = findViewById(R.id.public_srl);
        publicRlv.setLayoutManager(new LinearLayoutManager(mActivity));
        publicSrl.setOnRefreshListener(this);
        publicSrl.setEnableLoadMore(false);
        mToolbar = findViewById(R.id.public_toolbar);
        mAdapter.setEmptyView(R.layout.public_view_empty, publicRlv);
        mAdapter.bindToRecyclerView(publicRlv);
        mAdapter.setOnLoadMoreListener(this, publicRlv);
    }

    public void initRecyclerView(@NonNull BaseQuickAdapter mAdapter, String title) {
        mToolbarTitle = findViewById(R.id.public_toolbar_title);
        mToolbarTitle.setText(title);
        initRecyclerView(mAdapter);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.public_activity_recyclerview;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mAdapter.setEnableLoadMore(false);
        mCurrentPage = 1;
        dataMap.put("page", mCurrentPage);
        onRefreshData();
    }

    public void onRefresh() {
        onRefresh(publicSrl);
    }


    @Override
    public void onLoadMoreRequested() {
        mCurrentPage = mCurrentPage + 1;
        if (mCurrentPage > mTotalPage) {
            mAdapter.setEnableLoadMore(false);
            mAdapter.loadMoreEnd();
        } else {
            dataMap.put("page", mCurrentPage);
            dataMap.put("pagination[page]", mCurrentPage);
            onRefreshData();
        }
    }

    protected abstract void onRefreshData();

    public void updateUI(ResultBean resultBean) {
        if (mCurrentPage == 1) {
            if (resultBean.getPaginated() != null) {
                PageBean pageBean = resultBean.getPaginated();
                mTotalPage = pageBean.getTotal() / pageBean.getCount() + (pageBean.getTotal() % pageBean.getCount() == 0 ? 0 : 1);

            } else {
                mTotalPage = 0;
            }

            mAdapter.setNewData((List) resultBean.getData());
            mAdapter.disableLoadMoreIfNotFullPage();
            publicSrl.finishRefresh();

        } else {
            mAdapter.addData((List) resultBean.getData());

            mAdapter.loadMoreComplete();
        }
    }
}
