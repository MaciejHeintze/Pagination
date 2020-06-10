package com.gaminidsystems.pagination

import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewPaginator(recyclerView: RecyclerView) : RecyclerView.OnScrollListener() {


    private val batchSize = 19L
    private var currentPage: Long = 0L
    private val threshold = 2
    private var endWithAuto = false
    private val layoutManager: RecyclerView.LayoutManager?

    val startSize: Long
        get() = ++currentPage

    val maxSize: Long
        get() = currentPage + batchSize

    //public abstract boolean isLoading();

    abstract val isLastPage: Boolean

    init {
        recyclerView.addOnScrollListener(this)
        this.layoutManager = recyclerView.layoutManager
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == SCROLL_STATE_IDLE) {
            val visibleItemCount = layoutManager!!.childCount
            val totalItemCount = layoutManager.itemCount

            var firstVisibleItemPosition = 0
            if (layoutManager is LinearLayoutManager) {
                firstVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

            } else if (layoutManager is GridLayoutManager) {
                firstVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            }

            if (isLastPage) return

            if (visibleItemCount + firstVisibleItemPosition + threshold >= totalItemCount) {
                if (!endWithAuto) {
                    endWithAuto = true
                    loadMore(startSize, maxSize)
                }
            } else {
                endWithAuto = false
            }
        }
    }

    fun reset() {
        currentPage = 0L
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
    }

    abstract fun loadMore(start: Long, count: Long)
}