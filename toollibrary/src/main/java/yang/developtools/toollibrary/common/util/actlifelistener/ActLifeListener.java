package yang.developtools.toollibrary.common.util.actlifelistener;

import android.os.Bundle;

/**
 * Created by yangjh on 2017/3/15.
 * 用于监听Activity或Fragment的生命周期的接口
 */

public interface ActLifeListener extends ActListener{

    public void onCreate(Bundle bundle);

    public void onStart();

    public void onResume();

    public void onPause();

    public void onStop();

    public void onDestroy();

}
