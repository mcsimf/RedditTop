package com.mcsimf.reddittop.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.mcsimf.reddittop.core.Downloader
import com.mcsimf.reddittop.core.ListingTopPagedSource
import com.mcsimf.reddittop.core.api.model.TypedEntry


/**
 * @author Maksym Fedyay on 9/13/20 (mcsimf@gmail.com).
 */
class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    /**
     *
     */
    val topReddit: LiveData<PagingData<TypedEntry>> = Pager(PagingConfig(pageSize = 25)) {
        ListingTopPagedSource()
    }.liveData.cachedIn(viewModelScope)


    /**
     *
     */
    fun downloads(url: String) {
        Downloader.download(app, url)
    }

}