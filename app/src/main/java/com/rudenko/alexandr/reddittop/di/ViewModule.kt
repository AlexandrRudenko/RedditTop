package com.rudenko.alexandr.reddittop.di

import com.rudenko.alexandr.reddittop.ui.news.NewsContract
import com.rudenko.alexandr.reddittop.ui.news.NewsPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModule {

    @Provides
    @Singleton
    fun provideNewsPresenter(): NewsContract.Presenter = NewsPresenter()

}