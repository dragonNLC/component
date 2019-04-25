/*
 * ************************************************************
 * 文件：PageListViewModel.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.viewmodel;

import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.PageInfo;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.factory.PageListDataSourceFactory;
import com.cody.component.handler.interfaces.OnRequestPageListener;
import com.cody.component.handler.mapper.IDataMapper;
import com.cody.component.handler.source.DataSourceWrapper;
import com.cody.component.handler.source.PageListKeyedDataSource;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * Created by xu.yi. on 2019/4/8.
 * 数据仓库，获取列表数据
 */
public abstract class PageListViewModel<VD extends MaskViewData> extends FriendlyViewModel<VD>
        implements OnRequestPageListener, IDataMapper {
    private DataSourceWrapper mWrapper;
    private LiveData<PagedList<ItemViewDataHolder<?>>> mPagedList;

    public PageListViewModel(final VD friendlyViewData) {
        super(friendlyViewData);
    }

    @Override
    public void initFriendly() {
        final PageListDataSourceFactory sourceFactory = new PageListDataSourceFactory(this);
        mWrapper = new DataSourceWrapper(Transformations.switchMap(sourceFactory.getDataSource(), PageListKeyedDataSource::getRequestStatus),
                Transformations.switchMap(sourceFactory.getDataSource(), PageListKeyedDataSource::getOperation),
                sourceFactory.getDataSource());
        mPagedList = new LivePagedListBuilder<>(sourceFactory, initPageListConfig()).build();
    }

    public LiveData<PagedList<ItemViewDataHolder<?>>> getPagedList() {
        return mPagedList;
    }

    @Override
    public MutableLiveData<Operation> getOperation() {
        return mWrapper.getOperation();
    }

    @Override
    public MutableLiveData<RequestStatus> getRequestStatus() {
        return mWrapper.getRequestStatus();
    }

    @Override
    public void refresh() {
        mWrapper.refresh();
    }

    @Override
    public void retry() {
        mWrapper.retry();
    }

    @Override
    //下次数据开始的位置
    public int getPosition(final List<ItemViewDataHolder<?>> viewDataList) {
        if (viewDataList == null || getOperation().getValue() == Operation.REFRESH) {
            return 0;
        }
        return viewDataList.size();
    }

    /**
     * 分页数据配置
     */
    protected PagedList.Config initPageListConfig() {
        return (new PagedList.Config.Builder())
                .setPrefetchDistance(PageInfo.DEFAULT_PREFETCH_DISTANCE)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(PageInfo.DEFAULT_PAGE_SIZE)
                .setPageSize(PageInfo.DEFAULT_PAGE_SIZE)
                .build();
    }
}