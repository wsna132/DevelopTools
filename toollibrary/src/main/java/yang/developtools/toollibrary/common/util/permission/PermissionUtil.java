package yang.developtools.toollibrary.common.util.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import yang.developtools.toollibrary.common.util.actlifelistener.LifeAttchManager;

/**
 * Created by yangjh on 2017/3/10.
 * 这个类用于6.0以上权限的处理
 */

public class PermissionUtil {

    private final String TAG = "PermissionUtil";
    private int REQUEST_CODE_PERMISSION = 0x00099;

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     * @param requestCode 请求权限的请求码
     */
    @TargetApi(Build.VERSION_CODES.M)
    public boolean requestPermission(String[] permissions, int requestCode,Activity activity) {
        this.REQUEST_CODE_PERMISSION = requestCode;
        //检查是否具有一些权限
        if (checkPermissions(permissions,activity)) {
            permissionSuccess(REQUEST_CODE_PERMISSION);
            return true;
        } else {//发现有权限未申请到,则准备发出申请
            //获取未申请到的权限列表
            List<String> needPermissions = getDeniedPermissions(permissions,activity);
            Fragment fragment = LifeAttchManager.getInstance().getSupportActLifeListenerFragment(activity);
            //发出权限申请
            fragment.requestPermissions(needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
            return false;
        }
    }


    /**
     * 系统请求权限回调,返回TRUE表示权限都通过了不需要处理
     *                  返回FALSE表示有未通过的需要提示处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            //确认所有权限是否都已授权
            if (verifyPermissions(grantResults)) {
                permissionSuccess(REQUEST_CODE_PERMISSION);
                return true;
            } else {//发现有没有授权的
                permissionFail(REQUEST_CODE_PERMISSION);
//                showTipsDialog();
                return false;
            }
        }
        return true;
    }


    /**
     * 检测所有的权限是否都已授权
     *
     * @param permissions
     * @return
     */
    private boolean checkPermissions(String[] permissions,Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 确认所有的权限是否都已授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(String[] permissions,Activity activity) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            //发现这条权限没有获取到权限，或者系统认为需要提示用户申请该权限的缘由时
            if (ContextCompat.checkSelfPermission(activity, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                //添加需要提示的权限进集合
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }


    /**
     * 获取权限成功
     *
     * @param requestCode
     */
    public void permissionSuccess(int requestCode) {
        Log.d(TAG, "获取权限成功=" + requestCode);
    }

    /**
     * 权限获取失败
     * @param requestCode
     */
    public void permissionFail(int requestCode) {
        Log.d(TAG, "获取权限失败=" + requestCode);
    }

}
