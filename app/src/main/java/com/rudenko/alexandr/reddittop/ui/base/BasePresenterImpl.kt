package com.rudenko.alexandr.reddittop.ui.base

import android.os.Bundle

abstract class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

    private var view: V? = null

    override fun bind(view: V, savedState: Bundle?) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun getView(): V? = this.view

}