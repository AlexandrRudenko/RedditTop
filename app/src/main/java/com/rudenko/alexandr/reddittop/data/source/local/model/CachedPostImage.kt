package com.rudenko.alexandr.reddittop.data.source.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.rudenko.alexandr.reddittop.data.source.local.PostsDao

@Entity(tableName = "images")
@ForeignKey(
        entity = CachedPost::class,
        parentColumns = ["id"],
        childColumns = ["postId"],
        onDelete = ForeignKey.CASCADE)
data class CachedPostImage(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var postId: String,
        var url: String,
        var width: Int,
        var height: Int
)