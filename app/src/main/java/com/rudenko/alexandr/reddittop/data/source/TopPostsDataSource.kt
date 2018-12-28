package com.rudenko.alexandr.reddittop.data.source

import com.rudenko.alexandr.reddittop.data.Post
import io.reactivex.Single

interface TopPostsDataSource {

    fun getTopNews(after: String?, limit: Int): Single<List<Post>>

    fun saveTopNews(posts: List<Post>)

}