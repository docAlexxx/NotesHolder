package com.example.notesholder;

import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.appcompat.app.ActionBar;

public class Utils {
    public static boolean isLandscape(Resources res) {
        return res.getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

}
