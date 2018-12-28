package com.rudenko.alexandr.reddittop

import android.app.Application
import android.arch.persistence.room.Room
import com.rudenko.alexandr.reddittop.data.source.local.AppDatabase
import com.rudenko.alexandr.reddittop.di.AppComponent
import com.rudenko.alexandr.reddittop.di.DaggerAppComponent

open class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    lateinit var component: AppComponent
        private set

    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database")
                .fallbackToDestructiveMigration()
                .build()

        component = buildComponent()
    }

    open fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder().build()
    }
}