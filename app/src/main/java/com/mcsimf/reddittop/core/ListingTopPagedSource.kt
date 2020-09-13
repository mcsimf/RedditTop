package com.mcsimf.reddittop.core

import android.util.Log
import androidx.paging.PagingSource
import com.mcsimf.reddittop.core.api.Api
import com.mcsimf.reddittop.core.api.model.TypedEntry
import retrofit2.HttpException
import java.io.IOException

/**
 * @author Maksym Fedyay on 9/13/20 (mcsimf@gmail.com).
 */
class ListingTopPagedSource : PagingSource<String, TypedEntry>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, TypedEntry> {

        return try {
            val response = Api.listings.topList(
                after = if (params is LoadParams.Append) params.key else null,
                before = if (params is LoadParams.Prepend) params.key else null
            )

            LoadResult.Page(
                data = response.data.children,
                prevKey = response.data.before,
                nextKey = response.data.after
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}