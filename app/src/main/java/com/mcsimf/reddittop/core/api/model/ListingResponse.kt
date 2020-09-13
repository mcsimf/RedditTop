package com.mcsimf.reddittop.core.api.model

/**
 * @author Maksym Fedyay on 9/13/20 (mcsimf@gmail.com).
 */
data class ListingResponse(val kind: String, val data: Data)


data class Data(
    val modehash: String,
    val dist: Int,
    val children: List<TypedEntry>,
    val after: String?,
    val before: String?
)


data class TypedEntry(val kind: String, val data: Entry)


data class Entry(
    val name: String,
    val title: String,
    val created: Long,
    val created_utc: Long,
    val thumbnail: String,
    val num_comments: Long,
    val url: String
)