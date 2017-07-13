package yang.developtools.toollibrary.common.util.releasedebugstatus;

import android.util.Log;

/**
 * Created by yangjh on 2017/7/13.
 * 打印工具
 */
public class LogUtil {

    private static final String TAG = "YANG_LOG";

    public static void log(String tag,String value){
        if(DebugStatus.isDebug()) {
            Log.i(TAG, "tag:" + tag + ",Value:" + value);
        }
    }

    public static void log(String value){
        if(DebugStatus.isDebug()) {
            Log.i(TAG, "Value:" + value);
        }
    }

    public static void logd(String tag,String value){
        if(DebugStatus.isDebug()) {
            Log.d(TAG, "tag:" + tag + "DebugValue:" + value);
        }
    }

    public static void logd(String value){
        if(DebugStatus.isDebug()) {
            Log.d(TAG, "DebugValue:" + value);
        }
    }


}
