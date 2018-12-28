package com.rudenko.alexandr.reddittop.data.source.local

import com.rudenko.alexandr.reddittop.data.Post
import com.rudenko.alexandr.reddittop.data.PostImage
import com.rudenko.alexandr.reddittop.data.source.TopPostsDataSource
import com.rudenko.alexandr.reddittop.data.source.local.model.CachedPost
import com.rudenko.alexandr.reddittop.data.source.local.model.CachedPostImage
import com.rudenko.alexandr.reddittop.data.source.local.model.CachedPostWithImage
import io.reactivex.Single
import javax.inject.Inject

class TopPostsLocalDataSource @Inject constructor(
        private val postsDao: PostsDao
) : TopPostsDataSource {

    override fun getTopNews(after: String?, limit: Int): Single<List<Post>> {
        return postsDao.getAll()
                .map { cachedToPosts(it) }
    }

    override fun saveTopNews(posts: List<Post>) {
        postsDao.deleteAll()
        postsDao.insert(postsToCached(posts))
    }

    private fun cachedToPosts(list: List<CachedPostWithImage>): List<Post> {
        return list.map {
            Post(
                    it.post.id,
                    it.post.name,
                    it.post.link,
                    it.post.title,
                    it.post.subreddit,
                    it.post.author,
                    it.post.numComments,
                    it.post.numComments,
                    it.post.created,
                    it.images?.map { image ->
                        PostImage(
                                image.url,
                                image.width,
                                image.height
                        )
                    }
            )
        }
    }

    private fun postsToCached(list: List<Post>): List<CachedPostWithImage> {
        return list.map {
            val post = CachedPostWithImage(
               CachedPost(
                        it.id,
                        it.name,
                        it.link,
                        it.title,
                        it.subreddit,
                        it.author,
                        it.numComments,
                        it.score,
                        it.created
                )
            )

            post.images = it.images?.map { image ->
                CachedPostImage(
                        0,
                        it.id,
                        image.url,
                        image.width,
                        image.height
                )
            }
            return@map post
         }
    }
}