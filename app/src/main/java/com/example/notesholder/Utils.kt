package com.example.notesholder

import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.ActionBar

object Utils {
    fun isLandscape(res: Resources): Boolean {
        return (res.configuration.orientation
                == Configuration.ORIENTATION_LANDSCAPE)
    }

    @JvmStatic
    fun setSubtitleName(actionBar: ActionBar?, name: String?) {
        if (actionBar != null) {
            actionBar.subtitle = name
            //
        }
    }
}