package com.rudenko.alexandr.reddittop.ui.base

import android.os.Bundle

interface BasePresenter<V : BaseView> {

    fun bind(view: V, savedState: Bundle?)

    fun unbind()

    fun getView(): V?

    fun saveInstanceState(outState: Bundle?)

}