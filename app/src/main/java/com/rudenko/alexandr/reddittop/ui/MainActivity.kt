package com.rudenko.alexandr.reddittop.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rudenko.alexandr.reddittop.R
import com.rudenko.alexandr.reddittop.ui.news.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val topNewsFragment = supportFragmentManager.findFragmentById(R.id.content_frame)
        topNewsFragment ?: supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, NewsFragment.newInstance())
                .commit()
    }

}