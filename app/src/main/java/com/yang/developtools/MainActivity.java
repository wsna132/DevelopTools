package com.yang.developtools;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yang.developtools.model.core.WXDataGetter;
import com.yang.developtools.model.core.callback.OnResponseCallback;
import com.yang.developtools.model.entity.parser.HotWeiXinEntityListParser;
import com.yang.developtools.util.MIUIUtils;

public class MainActivity extends AppCompatActivity {

    TextView text = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
//        final Button view = new Button(this);
//        view.setBackgroundColor(Color.RED);
//        final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(500,500);
//        ViewGroup group = getCanAddViewGroup(MainActivity.this);
//        group.addView(view,params);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success=LauncherUtils.clearLanucher(MainActivity.this);
                Toast.makeText(MainActivity.this,"清除结果：" + success,Toast.LENGTH_LONG).show();
            }
        });
        TextView text2 = (TextView) findViewById(R.id.text2);
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //模拟Home键
                LauncherUtils.startLauncher(MainActivity.this);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(;;) {
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){}
                    Log.d("MainActivity", "i am run");
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        boolean show = isViewCovered(text);
//        Toast.makeText(this,"是否可见呢：" + MIUIUtils.isMIUI(),Toast.LENGTH_SHORT).show();
//        windowCanShow(this);

    }

    /**
     * 在Activity的PhineWindow的根View中最上层的可见并且是Relative或者FrameLayout的View，获取到并返回来
     * @param activity
     * @return
     */
    private ViewGroup getCanAddViewGroup(Activity activity){
        //从根view开始获取view
        View view = activity.getWindow().getDecorView();
        if(view instanceof ViewGroup){
            return getViewGroup((ViewGroup)view);
        }
        return null;
    }

    /**
     * 不断地获取子view，当子View instanceof RelativeLayout或者FrameLayout并且不instanceof ViewStub时
     * 一定要在页面已经运行显示之后才可以，否则可能判断不准确
     * @param group
     * @return
     */
    private ViewGroup getViewGroup(ViewGroup group){
        View childView = null;
        ViewGroup childsChildView = null;
        if(null == group){
            return null;
        }
        int childCount = group.getChildCount();
        if(0 >= childCount){
            return null;
        }
        //开始遍历子View，一个个子View以及自View的子View判断过来，找到则返回对象，找不到则返回null;
        for(int i = 0;i < childCount;i++){
            childView = group.getChildAt(i);
            //不是必现的view时，跳过
            if(childView instanceof ViewStub){
                continue;
            }
            //如果这个控件是不可见的，那么就没有意义，那么这里就对这个方法的调用时机有了要求，就是一定要在页面已经运行显示之后才可以
            if(childView.getVisibility() != View.VISIBLE){
                continue;
            }
            //是需要的View时返回本对象
            if(childView instanceof RelativeLayout || childView instanceof FrameLayout){
                return (ViewGroup) childView;
            }
            //不是需要的对象，但是是ViewGroup时
            if(childView instanceof  ViewGroup){
                //获取器子View中的结果
                if(((ViewGroup)childView).getChildCount() > 0) {
                    //该子View中没有找到结果时，循环下一个
                    childsChildView = getViewGroup((ViewGroup) childView);
                    if(null == childsChildView){
                        continue;
                    }
                    //找到对象了，就返回
                    return childsChildView;
                }
            }
        }
        return null;
    }


    /**
     * 不断地获取parentView，不断地判断当前的View是否被ParentView遮挡来判断是否可见
     * @param view
     * @return
     */
    public boolean isViewCovered(final View view)
    {
        View currentView = view;

        Rect currentViewRect = new Rect();
        boolean partVisible =currentView.getGlobalVisibleRect(currentViewRect);
        boolean totalHeightVisible = (currentViewRect.bottom - currentViewRect.top) >= currentView.getMeasuredHeight();
        boolean totalWidthVisible = (currentViewRect.right - currentViewRect.left) >= currentView.getMeasuredWidth();
        boolean totalViewVisible = partVisible && totalHeightVisible && totalWidthVisible;
        if (!totalViewVisible)//if any part of the view is clipped by any of its parents,return true
            return true;

        while (currentView.getParent() instanceof ViewGroup)
        {
            ViewGroup currentParent = (ViewGroup) currentView.getParent();
            if (currentParent.getVisibility() != View.VISIBLE)//if the parent of view is not visible,return true
                return true;

            int start = indexOfViewInParent(currentView, currentParent);
            for (int i = start + 1; i < currentParent.getChildCount(); i++)
            {
                Rect viewRect = new Rect();
                view.getGlobalVisibleRect(viewRect);
                View otherView = currentParent.getChildAt(i);
                Rect otherViewRect = new Rect();
                otherView.getGlobalVisibleRect(otherViewRect);
                if (Rect.intersects(viewRect, otherViewRect))//if view intersects its older brother(covered),return true
                    return true;
            }
            currentView = currentParent;
        }
        return false;
    }


    private int indexOfViewInParent(View view, ViewGroup parent)
    {
        int index;
        for (index = 0; index < parent.getChildCount(); index++)
        {
            if (parent.getChildAt(index) == view)
                break;
        }
        return index;
    }



    private void windowCanShow(Activity context){
        String packname = context.getPackageName();
        PackageManager pm = context.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.SYSTEM_ALERT_WINDOW", packname));
        if(permission){
            Toast.makeText(context,"浮窗可见",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"浮窗不可见",Toast.LENGTH_SHORT).show();
        }
    }







}



/**
 * httpget
 *  WXDataGetter.getmInstance().getWXHotInfo(1, 20, "新闻", new OnResponseCallback<HotWeiXinEntityListParser>() {
@Override
public void onSuccess(HotWeiXinEntityListParser data) {
Log.i("tag","SuccessResult:" + data.newslist.size());
}

@Override
public void onFailure(String failMsg) {
Log.i("tag","FailResult:" + failMsg);
}
});
 */
