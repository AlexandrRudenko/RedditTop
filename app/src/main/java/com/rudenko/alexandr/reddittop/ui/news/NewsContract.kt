package com.rudenko.alexandr.reddittop.ui.news

import android.support.annotation.StringRes
import com.rudenko.alexandr.reddittop.data.Post
import com.rudenko.alexandr.reddittop.ui.base.BasePresenter
import com.rudenko.alexandr.reddittop.ui.base.BaseView

interface NewsContract {

    interface View : BaseView {

        fun setData(list: List<Post>)

        fun addData(list: List<Post>)

        fun openEntityDetails(newsItem: Post)

        fun showRefreshing(refreshing: Boolean)

        fun showFullscreenLoading()

        fun showFullscreenLoadingError(@StringRes resId: Int)

        fun showFullscreenLoadingError(msg: String)

        fun showLoadingMore()

        fun showLoadingMoreError(msg: String)

        fun showLoadingMoreError(@StringRes resId: Int)
    }

    interface Presenter : BasePresenter<View> {

        fun onClickRetry()

        fun onRefresh()

        fun onDownScrolled()

        fun onItemClick(item: Post)
    }
}