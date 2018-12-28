package com.rudenko.alexandr.reddittop.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class PostDto(
        @SerializedName("id")
        var id: String,

        @SerializedName("name")
        var name: String,

        @SerializedName("permalink")
        var link: String,

        @SerializedName("title")
        var title: String,

        @SerializedName("subreddit_name_prefixed")
        var subreddit: String,

        @SerializedName("author")
        var author: String,

        @SerializedName("thumbnail")
        var thumbnail: String?,

        @SerializedName("num_comments")
        var numComments: Int,

        @SerializedName("score")
        var score: Int,

        @SerializedName("created_utc")
        var created: Long,

        @SerializedName("preview")
        var preview: PreviewDto?

)


