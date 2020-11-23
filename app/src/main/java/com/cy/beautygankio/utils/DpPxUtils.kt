package com.cy.beautygankio.utils

import android.content.Context
import android.util.DisplayMetrics




class DpPxUtils {

    companion object{
        fun dp2Px(context: Context, dp: Float): Float {
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            val density = displayMetrics.density
            return dp * density
        }


        fun px2Dp(context: Context, px: Float): Float {
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            val density = displayMetrics.density
            return px / density
        }
    }

}