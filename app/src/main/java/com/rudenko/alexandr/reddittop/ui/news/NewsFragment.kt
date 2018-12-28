package com.rudenko.alexandr.reddittop.ui.news

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rudenko.alexandr.reddittop.App
import com.rudenko.alexandr.reddittop.Const
import com.rudenko.alexandr.reddittop.R
import com.rudenko.alexandr.reddittop.data.Post
import com.rudenko.alexandr.reddittop.ui.news.adapters.NewsAdapter
import kotlinx.android.synthetic.main.fragment_top_news.*
import javax.inject.Inject

class NewsFragment : Fragment(), NewsContract.View {

    companion object {
        fun newInstance(): NewsFragment = NewsFragment()
    }

    @Inject
    lateinit var presenter: NewsContract.Presenter
    private lateinit var adapter: NewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = NewsAdapter(
                {presenter.onItemClick(it)},
                {presenter.onDownScrolled()}
        )

        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(PaginationScrollListener(
                linearLayoutManager, {presenter.onDownScrolled()}
        ))
        error_btn_retry.setOnClickListener { presenter.onClickRetry() }
        refresh_layout.setOnRefreshListener { presenter.onRefresh() }

        presenter.bind(this, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.saveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

    override fun setData(list: List<Post>) {
        adapter.clear()
        addData(list)
    }

    override fun addData(list: List<Post>) {
        adapter.showLoading()
        adapter.add(list)
        refresh_layout.visibility = View.VISIBLE
        loading_progress.visibility = View.GONE
        error_layout.visibility = View.GONE
    }

    override fun openEntityDetails(newsItem: Post) {
        val customTabsIntent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(resources.getColor(R.color.colorPrimary))
                .enableUrlBarHiding()
                .build()
        customTabsIntent.launchUrl(context, Uri.parse(Const.BASE_URL + newsItem.link))
    }

    override fun showRefreshing(refreshing: Boolean) {
        refresh_layout.isRefreshing = refreshing
    }

    override fun showFullscreenLoading() {
        refresh_layout.visibility = View.GONE
        loading_progress.visibility = View.VISIBLE
        error_layout.visibility = View.GONE
    }

    override fun showFullscreenLoadingError(resId: Int) {
        showFullscreenLoadingError(resources.getString(resId))
    }

    override fun showFullscreenLoadingError(msg: String) {
        refresh_layout.visibility = View.GONE
        loading_progress.visibility = View.GONE
        error_layout.visibility = View.VISIBLE
        error_txt_cause.text = msg
    }

    override fun showLoadingMore() {
        adapter.showLoading()
    }

    override fun showLoadingMoreError(resId: Int) {
        showLoadingMoreError(resources.getString(resId))
    }

    override fun showLoadingMoreError(msg: String) {
        adapter.showError(msg)
    }

    override fun showError(resId: Int) {
        showError(resources.getString(resId))
    }

    override fun showError(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}