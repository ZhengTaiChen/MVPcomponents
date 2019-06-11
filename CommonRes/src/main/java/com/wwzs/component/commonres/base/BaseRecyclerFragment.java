package com.wwzs.component.commonres.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wwzs.component.commonres.R;
import com.wwzs.component.commonsdk.base.BaseFragment;
import com.wwzs.component.commonsdk.base.BaseLazyLoadFragment;
import com.wwzs.component.commonsdk.entity.PageBean;
import com.wwzs.component.commonsdk.entity.ResultBean;
import com.wwzs.component.commonsdk.mvp.IPresenter;
import com.wwzs.component.commonsdk.utils.ArmsUtils;

import java.util.List;

public abstract class BaseRecyclerFragment<P extends IPresenter> extends BaseLazyLoadFragment<P> implements OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    protected RecyclerView publicRlv;
    protected SmartRefreshLayout publicSrl;
    protected BaseQuickAdapter mAdapter;
    private View mRootView;
    protected int mTotalPage;
    protected int mCurrentPage = 1;

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mRootView = inflater.inflate(R.layout.public_fragment_recyclerview, container, false);
    }

    public void initRecyclerView(@NonNull BaseQuickAdapter mAdapter) {
        publicRlv = mRootView.findViewById(R.id.public_rlv);
        publicSrl = mRootView.findViewById(R.id.public_srl);
        ArmsUtils.configRecyclerView(publicRlv, new LinearLayoutManager(mActivity));
        publicSrl.setOnRefreshListener(this);
        publicSrl.setEnableLoadMore(false);
        mAdapter.setEmptyView(R.layout.public_view_empty, publicRlv);
        mAdapter.bindToRecyclerView(publicRlv);
        mAdapter.setOnLoadMoreListener(this, publicRlv);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mAdapter.setEnableLoadMore(false);
        mCurrentPage = 1;
        dataMap.remove("search_text");
        dataMap.remove("search_content");
        dataMap.put("PageSize", 10);
        dataMap.put("page", mCurrentPage);
        dataMap.put("pagination[page]", mCurrentPage);
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
            if (mCurrentPage == mTotalPage)
                mAdapter.disableLoadMoreIfNotFullPage();
            publicSrl.finishRefresh();

        } else {
            mAdapter.addData((List) resultBean.getData());
            mAdapter.loadMoreComplete();
        }
    }
}
