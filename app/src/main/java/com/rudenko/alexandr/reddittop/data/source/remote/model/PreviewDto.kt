package com.rudenko.alexandr.reddittop.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class PreviewDto(
        @SerializedName("images")
        var images: List<ImageDto>
)