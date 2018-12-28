package com.rudenko.alexandr.reddittop.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class ResolutionDto(
        @SerializedName("url")
        var url: String,
        @SerializedName("width")
        var width: Int,
        @SerializedName("height")
        var height: Int
)