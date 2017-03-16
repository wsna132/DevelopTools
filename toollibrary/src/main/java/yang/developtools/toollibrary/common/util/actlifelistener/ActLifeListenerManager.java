package yang.developtools.toollibrary.common.util.actlifelistener;

import android.content.Intent;
import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjh on 2017/3/16.
 * 用于管理ActLifeListener以及生命周期的回调执行
 *
 */

public class ActLifeListenerManager {

    List<ActListener> listeners = new ArrayList<ActListener>();

    public void addLifeListener(ActListener listener){
        if(!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeLifeListener(ActListener listener){
        listeners.remove(listener);
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        for(ActListener listener:listeners){
            if(listener instanceof ActLifeListener){
                ((ActLifeListener)listener).onCreate(savedInstanceState);
            }
        }
    }

    
    public void onStart() {
        for(ActListener listener:listeners){
            if(listener instanceof ActLifeListener){
                ((ActLifeListener)listener).onStart();
            }
        }
    }

    
    public void onResume() {
        for(ActListener listener:listeners){
            if(listener instanceof ActLifeListener){
                ((ActLifeListener)listener).onResume();
            }
        }
    }

    
    public void onPause() {
        for(ActListener listener:listeners){
            if(listener instanceof ActLifeListener){
                ((ActLifeListener)listener).onPause();
            }
        }
    }

    
    public void onStop() {
        for(ActListener listener:listeners){
            if(listener instanceof ActLifeListener){
                ((ActLifeListener)listener).onStop();
            }
        }
    }

    
    public void onDestroy() {
        //倒过来循环防止删除时有影响
        for(int i = listeners.size()-1;i >= 0;i--){
            if(listeners.get(i) instanceof ActLifeListener){
                ActLifeListener listener = (ActLifeListener)listeners.get(i);
                listener.onDestroy();
                recycleListener(listener);//回收防止持有页面对象
            }
        }
    }

    /**
     * 权限申请的回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for(ActListener listener:listeners){
            if(listener instanceof ActPermissionListener){
                ((ActPermissionListener)listener).onRequestPermissionsResult(requestCode,permissions,grantResults);
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    /**
     *
     * 直接全部回收，页面回收之后已经没有必要继续监听
     *
     *
     * 回收本身就是Activity或Fragment的listener
     * 否则页面会被持有导致无法回收
     * @param listener
     */
    private void recycleListener(ActListener listener){
//        if(listener instanceof Activity || listener instanceof AppCompatActivity){
//            listeners.remove(listener);
//            listener = null;
//        }
//        if(null != listener && (listener instanceof Fragment || listener instanceof android.support.v4.app.Fragment)){
//            listeners.remove(listener);
//            listener = null;
//        }
        listeners.remove(listener);
        listener = null;
    }


}
