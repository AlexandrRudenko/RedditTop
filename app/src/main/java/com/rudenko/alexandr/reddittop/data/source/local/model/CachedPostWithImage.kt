package com.rudenko.alexandr.reddittop.data.source.local.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class CachedPostWithImage(@Embedded var post: CachedPost) {
    @Relation(parentColumn = "id", entityColumn = "postId")
    var images: List<CachedPostImage>? = null
}