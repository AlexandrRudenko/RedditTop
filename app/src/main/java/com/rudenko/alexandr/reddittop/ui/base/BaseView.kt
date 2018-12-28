package com.rudenko.alexandr.reddittop.ui.base

import android.support.annotation.StringRes

interface BaseView {

    fun showError(msg: String)

    fun showError(@StringRes resId: Int)

}