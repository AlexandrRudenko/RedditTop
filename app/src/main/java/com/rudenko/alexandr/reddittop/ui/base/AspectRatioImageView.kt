package com.rudenko.alexandr.reddittop.ui.base

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet


class AspectRatioImageView constructor(context: Context, attrs: AttributeSet? = null) : AppCompatImageView(context, attrs) {

    private var aspectRatio: Float = 1f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(measuredWidth, (measuredWidth / aspectRatio).toInt())
    }

    /** Set the aspect ratio for this image view. This will update the view instantly.  */
    fun setAspectRatio(aspectRatio: Float) {
        this.aspectRatio = aspectRatio
        requestLayout()
    }

}