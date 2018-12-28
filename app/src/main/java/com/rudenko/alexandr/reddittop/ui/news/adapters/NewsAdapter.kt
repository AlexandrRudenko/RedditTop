package com.rudenko.alexandr.reddittop.ui.news.adapters

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rudenko.alexandr.reddittop.R
import com.rudenko.alexandr.reddittop.data.Post
import com.rudenko.alexandr.reddittop.load
import com.rudenko.alexandr.reddittop.utils.NumberFormatter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_loading.*
import kotlinx.android.synthetic.main.item_news.*
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class NewsAdapter(
        private val onClick: (Post) -> Unit,
        private val onRetryClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_NEWS = 0
        private const val ITEM_LOADING = 1
        private const val PREVIEW_WIDTH = 720
    }

    private val prettyTime: PrettyTime = PrettyTime(Locale("en"))
    private val items: MutableList<Post> = ArrayList()
    private var retryPageLoad: Boolean = false
    private var errorMsg: String = ""

    fun add(items: List<Post>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun showLoading() {
        retryPageLoad = false
        notifyItemChanged(items.size)
    }

    fun showError(msg: String) {
        retryPageLoad = true
        errorMsg = msg
        notifyItemChanged(items.size)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            ITEM_NEWS -> {
                val viewNews = inflater.inflate(R.layout.item_news, viewGroup, false)
                NewsViewHolder(viewNews)
            }
            else -> {
                val viewLoading = inflater.inflate(R.layout.item_loading, viewGroup, false)
                LoadingViewHolder(viewLoading)
            }
        }
    }

    override fun getItemCount(): Int = items.size + 1

    override fun getItemViewType(position: Int): Int = if (position == items.size) ITEM_LOADING else ITEM_NEWS

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)

        when (viewType) {
            ITEM_NEWS -> {
                val item = items[position]
                (viewHolder as NewsViewHolder).bind(item)
                viewHolder.itemView.setOnClickListener { onClick(item) }
            }
            ITEM_LOADING -> (viewHolder as LoadingViewHolder).showRetry(retryPageLoad, errorMsg)
        }
    }

    internal inner class NewsViewHolder(
            override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Post) {
            author.text = item.author
            subreddit.text = item.subreddit
            title.text = item.title
            val preview = item.getPreviewForWidth(PREVIEW_WIDTH)
            if (preview != null) {
                thumbnail.visibility = View.VISIBLE
                thumbnail.setAspectRatio(preview.width.toFloat() / preview.height.toFloat())
                thumbnail.load(Html.fromHtml(preview.url).toString())
            } else {
                thumbnail.visibility = View.GONE
            }

            score.text = NumberFormatter.format(item.score.toLong())
            comments.text = NumberFormatter.format(item.numComments.toLong())
            time.text = prettyTime.format(Date(item.created * 1000))
        }
    }

    internal inner class LoadingViewHolder(
            override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer{

        init {
            loadmore_retry.setOnClickListener { onRetryClick() }
            loadmore_errorlayout.setOnClickListener { onRetryClick() }
        }

        fun showRetry(retry: Boolean, errorMsg: String?) {
            loadmore_progress.visibility = if (retry) View.GONE else View.VISIBLE
            loadmore_errorlayout.visibility = if (retry) View.VISIBLE else View.GONE
            loadmore_errortxt.text = errorMsg
        }
    }
}
