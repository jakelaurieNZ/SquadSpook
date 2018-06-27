package com.jakelaurie.squadspook.ui

import android.content.res.Resources
import android.util.DisplayMetrics

class UIUtils {
    companion object {
        fun dpToPixels(dp: Float): Int {
            val metrics = Resources.getSystem().displayMetrics
            val px = dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
            return Math.round(px)
        }
    }
}