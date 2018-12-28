package com.rudenko.alexandr.reddittop.di

import com.rudenko.alexandr.reddittop.data.source.TopPostsRepository
import com.rudenko.alexandr.reddittop.data.source.TopPostsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideTopPostsRepository(): TopPostsRepository = TopPostsRepositoryImpl()
}