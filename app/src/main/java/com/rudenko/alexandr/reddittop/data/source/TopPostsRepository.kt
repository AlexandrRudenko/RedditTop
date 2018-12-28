package com.rudenko.alexandr.reddittop.data.source

import com.rudenko.alexandr.reddittop.data.Post
import io.reactivex.Flowable

interface TopPostsRepository {

    fun getTopPosts(after: String?, limit: Int): Flowable<List<Post>>

}