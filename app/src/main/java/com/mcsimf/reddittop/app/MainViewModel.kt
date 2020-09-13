package com.mcsimf.reddittop.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.mcsimf.reddittop.core.ListingTopPagedSource
import com.mcsimf.reddittop.core.api.model.TypedEntry

/**
 * @author Maksym Fedyay on 9/13/20 (mcsimf@gmail.com).
 */
class MainViewModel: ViewModel() {
    /* We can use flow or live data */

//    val topReddit: Flow<PagingData<TypedEntry>> = Pager(PagingConfig(pageSize = 25)) {
//        ListingsManager.top
//    }.flow.cachedIn(viewModelScope)

    val topReddit: LiveData<PagingData<TypedEntry>> = Pager(PagingConfig(pageSize = 25)) {
        ListingTopPagedSource()
    }.liveData.cachedIn(viewModelScope)
}