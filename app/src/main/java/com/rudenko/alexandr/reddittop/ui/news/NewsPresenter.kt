package com.rudenko.alexandr.reddittop.ui.news

import android.os.Bundle
import com.rudenko.alexandr.reddittop.App
import com.rudenko.alexandr.reddittop.R
import com.rudenko.alexandr.reddittop.data.Post
import com.rudenko.alexandr.reddittop.data.source.TopPostsRepository
import com.rudenko.alexandr.reddittop.ui.base.BasePresenterImpl
import com.rudenko.alexandr.reddittop.utils.NetworkUtils
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.subscribeBy
import java.net.SocketTimeoutException
import javax.inject.Inject

class NewsPresenter : BasePresenterImpl<NewsContract.View>(), NewsContract.Presenter {

    companion object {
        private const val BUNDLE_NEWS_LIST_KEY = "BUNDLE_NEWS_LIST_KEY"
        private const val LOAD_COUNT = 10
    }

    @Inject lateinit var repository: TopPostsRepository
    private var disposable: Disposable = Disposables.empty()

    private var items: MutableList<Post> = ArrayList()
    private var loading: Boolean = false

    init {
        App.instance.component.inject(this)
    }

    override fun bind(view: NewsContract.View, savedState: Bundle?) {
        super.bind(view, savedState)
        val savedItems = savedState?.getSerializable(BUNDLE_NEWS_LIST_KEY)
        if (savedItems != null && (savedItems as ArrayList<Post>).isNotEmpty()) {
            items = savedItems
            getView()?.setData(items)
        } else {
            loadNews(true)
        }
    }

    override fun saveInstanceState(outState: Bundle?) {
        outState?.putSerializable(BUNDLE_NEWS_LIST_KEY, ArrayList(items))
    }

    override fun onClickRetry() {
        loadNews(true)
    }

    override fun onRefresh() {
        loadNews(true)
    }

    override fun onDownScrolled() {
        loadNews(false)
    }

    override fun onItemClick(item: Post) {
        getView()?.openEntityDetails(item)
    }

    private fun loadNews(reset: Boolean) {
        if (loading) {
            return
        }

        loading = true

        if (!disposable.isDisposed) {
            disposable.dispose()
        }

        if (reset) {
            if (items.isEmpty()) {
                getView()?.showFullscreenLoading()
            } else {
                getView()?.showRefreshing(true)
            }
        } else {
            getView()?.showLoadingMore()
        }


        val after = if (!reset && items.isNotEmpty()) items[items.size - 1].name else null

        /*disposable = repository.getTopNews(after, LOAD_COUNT)
                .map(newsMapper)
                .subscribeBy(
                        onNext = {
                            if (it != null) {

                                if (reset) {
                                    items.clear()
                                    getView()?.setData(it)
                                } else {
                                    getView()?.addData(it)
                                }
                                items.addAll(it)
                            }
                            getView()?.showRefreshing(false)
                        },
                        onError =  {
                            val errorResId = getMessageResIdForError(it)

                            if (reset) {
                                if (items.isEmpty()) {
                                    getView()?.showFullscreenLoadingError(errorResId)
                                } else {
                                    getView()?.showError(errorResId)
                                }
                            } else {
                                getView()?.showLoadingMoreError(errorResId)
                            }
                            getView()?.showRefreshing(false)
                            loading = false
                        },
                        onComplete = { loading = false }
                )*/
        disposable = repository.getTopPosts(after, LOAD_COUNT)
                .subscribeBy(
                        onNext = {
                            if (reset) {
                                items.clear()
                                getView()?.setData(it)
                            } else {
                                getView()?.addData(it)
                            }
                            items.addAll(it)
                            getView()?.showRefreshing(false)
                        },
                        onError =  {
                            val errorResId = getMessageResIdForError(it)

                            if (reset) {
                                if (items.isEmpty()) {
                                    getView()?.showFullscreenLoadingError(errorResId)
                                } else {
                                    getView()?.showError(errorResId)
                                }
                            } else {
                                getView()?.showLoadingMoreError(errorResId)
                            }
                            getView()?.showRefreshing(false)
                            loading = false
                        },
                        onComplete = { loading = false }
                )
    }

    override fun unbind() {
        super.unbind()
        loading = false
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    private fun getMessageResIdForError(e: Throwable): Int {
        return if (!NetworkUtils.isOnline(App.instance.applicationContext)) {
            R.string.error_msg_no_internet
        } else if (e is SocketTimeoutException) {
            R.string.error_msg_timeout
        } else {
            R.string.error_msg_unknown
        }
    }
}