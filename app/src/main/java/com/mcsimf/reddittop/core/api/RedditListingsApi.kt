package com.mcsimf.reddittop.core.api

import com.mcsimf.reddittop.core.api.model.ListingResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Maksym Fedyay on 9/13/20 (mcsimf@gmail.com).
 */
interface RedditListingsApi {

    @GET("/top.json")
    suspend fun topList(
        @Query("limit") limit: Int? = null,
        @Query("count") count: Int? = null,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): ListingResponse

}