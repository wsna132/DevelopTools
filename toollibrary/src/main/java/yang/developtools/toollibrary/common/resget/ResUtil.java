package yang.developtools.toollibrary.common.resget;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * Created by yangjh on 2016/5/26.
 * 这个类用于读取不方便使用R.的方式获取的资源
 */
public class ResUtil {

    private static String packgeName;
    private static Resources resources;
    private static Context applicationContext;

    public static void init(Context context) {
        applicationContext = context.getApplicationContext();
        packgeName = context.getApplicationContext().getPackageName();
        resources = context.getApplicationContext().getResources();
    }

    public static int getLayout(String resName) {
        return resources.getIdentifier(resName, "layout", packgeName);
    }

    public static int getLayout(Context paramContext, String resName) {
        return get(paramContext, resName, "layout");
    }

    public static int getDrawable(String resName) {
        return resources.getIdentifier(resName, "drawable", packgeName);
    }

    public static int getDrawable(Context paramContext, String resName) {
        return get(paramContext, resName, "drawable");
    }

    public static int getString(String resName) {
        try {
            return resources.getIdentifier(resName, "string", packgeName);
        }catch (Exception e){
            init(applicationContext);
            return resources.getIdentifier(resName, "string", packgeName);
        }
    }

    public static int getString(Context paramContext, String resName) {
        return get(paramContext, resName, "string");
    }

    public static int getId(String resName) {
        return resources.getIdentifier(resName, "id", packgeName);
    }

    public static int getId(Context paramContext, String resName) {
        return get(paramContext, resName, "id");
    }

    public static int getStyle(String resName) {
        return resources.getIdentifier(resName, "style", packgeName);
    }

    public static int getStyle(Context paramContext, String resName) {
        return get(paramContext, resName, "style");
    }

    public static int getArray(String resName) {
        return resources.getIdentifier(resName, "array", packgeName);
    }

    public static int getColor(String resName) {
        return resources.getIdentifier(resName, "color", packgeName);
    }

    public static int getColor(Context paramContext, String resName) {
        return get(paramContext, resName, "color");
    }

    public static int getDimen(String resName) {
        return resources.getIdentifier(resName, "dimen", packgeName);
    }

    public static int getAnim(String resName) {
        return resources.getIdentifier(resName, "anim", packgeName);
    }

    public static int getStyleable(String resName) {
        return resources.getIdentifier(resName, "styleable", packgeName);
    }

    private static int get(Context paramContext, String paramString1, String paramString2)
    {
        Resources localResources = paramContext.getResources();
        int resId;
        if ((resId = localResources.getIdentifier(paramString1, paramString2, paramContext.getPackageName())) <= 0) {
            Log.w(ResUtil.class.getSimpleName(), "resource " + paramString1 + "." + paramString2 + " is not defined!");
        }
        return resId;
    }

}
