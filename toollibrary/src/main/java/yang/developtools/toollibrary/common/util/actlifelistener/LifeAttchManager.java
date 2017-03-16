package yang.developtools.toollibrary.common.util.actlifelistener;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Looper;

/**
 * Created by yangjh on 2017/3/15.
 * 用于给指定的Activity添加生命周期的监听的开放调用类
 */

public class LifeAttchManager {

    private static final String FRAGMENT_TAG = "ActLifeListenerFragment";

    private static volatile LifeAttchManager mInstance;

    private LifeAttchManager(){

    }

    public static LifeAttchManager getInstance(){
        if(null == mInstance){
            synchronized (LifeAttchManager.class){
                if(null == mInstance){
                    mInstance = new LifeAttchManager();
                }
            }
        }
        return mInstance;
    }


    /**
     * 开始监听生命周期
     */
    public void ObserveAct(Activity activity , ActLifeListener actListener){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a observe for a destroyed activity");
        }
        if(!(Looper.myLooper() == Looper.getMainLooper())){
            throw new IllegalArgumentException("You must start a observe on mainThread");
        }
        android.app.FragmentManager fm = activity.getFragmentManager();
        SupportActLifeListenerFragment fragment = findFragment(fm,actListener);//找到绑定的Fragment
        ActLifeListenerManager manager = findLifeListenerManager(fragment);//找到指定Fragment的ActLifeListenerManager
        manager.addLifeListener(actListener);//添加监听
    }



    /**
     * 找到用于监听生命周期的空白的Fragment
     */
    private SupportActLifeListenerFragment findFragment(FragmentManager fm,ActLifeListener actListener){
        SupportActLifeListenerFragment current = (SupportActLifeListenerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
            if (current == null) {//没有找到，则新建
                current = new SupportActLifeListenerFragment();
                fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();//添加Fragment
            }
        return current;
    }

    /**
     * 用于获取管理对应Fragment的ActLifeListenerManager
     * @param fragment
     * @return
     */
    private ActLifeListenerManager findLifeListenerManager(SupportActLifeListenerFragment fragment){
        ActLifeListenerManager manager = fragment.getLifeListenerManager();
        if(null == manager){
            manager = new ActLifeListenerManager();
        }
        fragment.setActLifeListenerManager(manager);
        return manager;
    }


}
