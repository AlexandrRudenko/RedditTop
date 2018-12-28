package com.rudenko.alexandr.reddittop.data.source.remote

import com.rudenko.alexandr.reddittop.data.Post
import com.rudenko.alexandr.reddittop.data.PostImage
import com.rudenko.alexandr.reddittop.data.source.TopPostsDataSource
import com.rudenko.alexandr.reddittop.data.source.remote.model.ResponseDto
import io.reactivex.Single
import javax.inject.Inject


class TopPostsRemoteDataSource @Inject constructor(
        private val api: ApiInterface
) : TopPostsDataSource {

    override fun getTopNews(after: String?, limit: Int): Single<List<Post>> {
        return api.getTopNews(after, limit)
                .map { responseToPosts(it) }
    }

    override fun saveTopNews(posts: List<Post>) {
        throw UnsupportedOperationException("not used")
    }

    private fun responseToPosts(response: ResponseDto): List<Post> {
        val result = ArrayList<Post>()

        response.data.children
                .forEach {
                    val images = ArrayList<PostImage>()

                    it.data.preview?.images?.get(0)?.let { imageDto ->

                        imageDto.source.let {
                            image -> PostImage(image.url, image.width, image.height)
                        }
                        imageDto.resolutions.forEach {
                            image -> images.add(PostImage(image.url, image.width, image.height))
                        }

                    }

                    val post = Post(
                            it.data.id,
                            it.data.name,
                            it.data.link,
                            it.data.title,
                            it.data.subreddit,
                            it.data.author,
                            it.data.numComments,
                            it.data.score,
                            it.data.created,
                            //source,
                            images
                    )
                    result.add(post)
                }

        return result
    }
}