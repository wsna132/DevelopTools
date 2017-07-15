package yang.developtools.toollibrary.common.util.releasedebugstatus;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by czk on 2017/7/13.
 */

public class DebugStatus {

    private static Boolean isDebug = null;

    public static boolean isDebug() {
        return isDebug == null ? false : isDebug.booleanValue();
    }

    /**
     * Sync lib debug with app's debug value. Should be called in module Application
     *
     * @param context
     */
    public static void syncIsDebug(Context context) {
        if (isDebug == null) {
            isDebug = context.getApplicationInfo() != null &&
                    (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }

}
