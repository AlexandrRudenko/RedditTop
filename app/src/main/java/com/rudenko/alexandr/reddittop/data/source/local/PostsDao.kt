package com.rudenko.alexandr.reddittop.data.source.local

import android.arch.persistence.room.*
import com.rudenko.alexandr.reddittop.data.source.local.model.CachedPost
import com.rudenko.alexandr.reddittop.data.source.local.model.CachedPostImage
import com.rudenko.alexandr.reddittop.data.source.local.model.CachedPostWithImage
import io.reactivex.Single

@Dao
abstract class PostsDao {


    @Transaction
    @Query("SELECT * FROM posts")
    abstract fun getAll(): Single<List<CachedPostWithImage>>

    @Transaction
    open fun insert(posts: List<CachedPostWithImage>) {
        posts.forEach {
            insert(it.post)
            it.images?.forEach { image ->
                insert(image)
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(posts: CachedPost)

    @Insert
    abstract fun insert(images: CachedPostImage)

    @Query("DELETE FROM posts")
    abstract fun deleteAll()

}