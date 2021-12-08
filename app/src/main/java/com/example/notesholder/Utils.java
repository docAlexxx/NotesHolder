package com.example.notesholder;

import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.appcompat.app.ActionBar;

public class Utils {
    public static boolean isLandscape(Resources res) {
        return res.getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }
    public static void setSubtitleName(ActionBar actionBar, String name) {
        if (actionBar != null) {
            actionBar.setSubtitle(name);
        }
    }
}
