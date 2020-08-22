package com.byl.jdrefresh.utils;

import android.content.Context;

public class SysUtils {
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
