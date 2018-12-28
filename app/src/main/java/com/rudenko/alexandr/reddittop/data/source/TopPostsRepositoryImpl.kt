package com.rudenko.alexandr.reddittop.data.source

import com.rudenko.alexandr.reddittop.App
import com.rudenko.alexandr.reddittop.Const
import com.rudenko.alexandr.reddittop.data.Post
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.functions.Function
import javax.inject.Inject
import javax.inject.Named

class TopPostsRepositoryImpl : TopPostsRepository {

    @field:[Inject Named(Const.REMOTE)]
    lateinit var remoteDataSource: TopPostsDataSource

    @field:[Inject Named(Const.LOCAL)]
    lateinit var localDataSource: TopPostsDataSource

    @field:[Inject Named(Const.UI_THREAD)]
    lateinit var uiThread: Scheduler

    @field:[Inject Named(Const.IO_THREAD)]
    lateinit var ioThread: Scheduler

    init {
        App.instance.component.inject(this)
    }

    override fun getTopPosts(after: String?, limit: Int): Flowable<List<Post>> {
        return remoteDataSource.getTopNews(after, limit)
                .doAfterSuccess { after ?: localDataSource.saveTopNews(it)}
                .toFlowable()
                .onErrorResumeNext(Function {
                    if (after == null) {
                        Flowable.mergeDelayError(
                                localDataSource.getTopNews(after, limit).toFlowable(),
                                Flowable.error(it)
                        )
                    } else {
                        Flowable.error(it)
                    }
                })
                .subscribeOn(ioThread)
                .observeOn(uiThread, true)
    }
}