package com.rudenko.alexandr.reddittop.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rudenko.alexandr.reddittop.data.source.local.model.CachedPost
import com.rudenko.alexandr.reddittop.data.source.local.model.CachedPostImage

@Database(entities = [CachedPost::class, CachedPostImage::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postsDao(): PostsDao

}