package yang.developtools.toollibrary.common.util.actlifelistener;

import android.support.annotation.NonNull;

/**
 * Created by yangjh on 2017/3/16.
 * 用于监听权限的回调
 */

public interface ActPermissionListener extends ActListener {

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

}
