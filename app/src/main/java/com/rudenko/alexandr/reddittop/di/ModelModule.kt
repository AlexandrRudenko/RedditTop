package com.rudenko.alexandr.reddittop.di

import com.rudenko.alexandr.reddittop.App
import com.rudenko.alexandr.reddittop.Const
import com.rudenko.alexandr.reddittop.data.source.TopPostsDataSource
import com.rudenko.alexandr.reddittop.data.source.local.TopPostsLocalDataSource
import com.rudenko.alexandr.reddittop.data.source.remote.ApiInterface
import com.rudenko.alexandr.reddittop.data.source.remote.TopPostsRemoteDataSource
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class ModelModule {

    @Provides
    @Singleton
    fun provideApiInterface(): ApiInterface {
        val builder = Retrofit.Builder().baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        return builder.build().create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    @Named(Const.UI_THREAD)
    fun provideSchedulerUI(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    @Named(Const.IO_THREAD)
    fun provideSchedulerIO(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    @Named(Const.LOCAL)
    fun provideLocalDataSource(): TopPostsDataSource = TopPostsLocalDataSource(App.instance.database.postsDao())

    @Provides
    @Singleton
    @Named(Const.REMOTE)
    fun provideRemoteDataSource(apiInterface: ApiInterface): TopPostsDataSource = TopPostsRemoteDataSource(apiInterface)

}