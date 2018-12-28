package com.rudenko.alexandr.reddittop.di

import com.rudenko.alexandr.reddittop.data.source.TopPostsRepositoryImpl
import com.rudenko.alexandr.reddittop.ui.news.NewsFragment
import com.rudenko.alexandr.reddittop.ui.news.NewsPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ModelModule::class, PresenterModule::class, ViewModule::class])
interface AppComponent {

    fun inject(presenter: NewsPresenter)

    fun inject(fragment: NewsFragment)

    fun inject(fragment: TopPostsRepositoryImpl)

}