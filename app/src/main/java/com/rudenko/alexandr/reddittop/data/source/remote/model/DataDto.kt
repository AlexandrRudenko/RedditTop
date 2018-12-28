package com.rudenko.alexandr.reddittop.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class DataDto(
        @SerializedName("children")
        var children: List<ChildrenDto>,

        @SerializedName("before")
        var before: String?,

        @SerializedName("after")
        var after: String?
)