package com.rudenko.alexandr.reddittop.data

import java.io.Serializable

data class Post(
        var id: String,
        var name: String,
        var link: String,
        var title: String,
        var subreddit: String,
        var author: String,
        var numComments: Int,
        var score: Int,
        var created: Long,
        var images: List<PostImage>?
) : Serializable {

    fun getPreviewForWidth(width: Int): PostImage? =
            images?.firstOrNull { it.width >= width } ?: images?.lastOrNull()

}


