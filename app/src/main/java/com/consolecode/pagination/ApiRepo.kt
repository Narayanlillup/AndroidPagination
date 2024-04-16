package com.consolecode.pagination

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRepo {
    @GET("posts")
    suspend fun getPosts(
        @Query("_page") _page: Int,
        @Query("_limit") _limit: Int
    ): List<PostData>
}