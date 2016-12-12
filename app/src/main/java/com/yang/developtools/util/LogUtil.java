package com.yang.developtools.util;

import android.util.Log;

/**
 * Created by yangjh on 2016/11/6 0006.
 */

public class LogUtil {

    private static final String TAG = "Debug_data";

    public static void logI(String logData){
        Log.i(TAG,"log_data:" + logData);
    }

}
