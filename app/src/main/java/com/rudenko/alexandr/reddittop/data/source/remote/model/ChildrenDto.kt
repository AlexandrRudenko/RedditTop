package com.rudenko.alexandr.reddittop.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class ChildrenDto(
        @SerializedName("data")
        var data: PostDto
)