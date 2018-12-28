package com.rudenko.alexandr.reddittop.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class ImageDto(
        @SerializedName("source")
        var source: ResolutionDto,

        @SerializedName("resolutions")
        var resolutions: List<ResolutionDto>
)