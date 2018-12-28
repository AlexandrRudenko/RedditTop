package com.rudenko.alexandr.reddittop.data

import java.io.Serializable

data class PostImage(
        var url: String,
        var width: Int,
        var height: Int
) : Serializable