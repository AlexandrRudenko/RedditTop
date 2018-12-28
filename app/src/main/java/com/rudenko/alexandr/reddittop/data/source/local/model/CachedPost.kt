package com.rudenko.alexandr.reddittop.data.source.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "posts")
data class CachedPost(
        @PrimaryKey
        var id: String,
        var name: String,
        var link: String,
        var title: String,
        var subreddit: String,
        var author: String,
        var numComments: Int,
        var score: Int,
        var created: Long
)