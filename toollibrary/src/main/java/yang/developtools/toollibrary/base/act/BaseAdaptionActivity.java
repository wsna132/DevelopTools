package yang.developtools.toollibrary.base.act;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import yang.developtools.toollibrary.R;
import yang.developtools.toollibrary.common.adaption.AdaptionLayout;
import yang.developtools.toollibrary.common.adaption.util.AutoUtils;

/**
 * Created by yangjh on 2017/3/8.
 * 这个类用于屏幕适配的Activity，继承了这个类的Activity可以使用px来写layout中的控件宽高
 * 在这里面会自动进行比例适配
 */

public abstract class BaseAdaptionActivity extends BaseToolActivity {

    private static final String KEY_DESIGN_WIDTH = "design_width";
    private static final String KEY_DESIGN_HEIGHT = "design_height";

    private AdaptionLayout userLayout;//根布局

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //初始化视图的尺寸
        //自适应布局
        initAutoSize();
        super.onCreate(savedInstanceState);
    }

    private void initAutoSize(){
        PackageManager packageManager = getPackageManager();
        ApplicationInfo applicationInfo;
        int mDesignWidth = 0;
        int mDesignHeight = 0;
        try
        {
            applicationInfo = packageManager.getApplicationInfo(this
                    .getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null && applicationInfo.metaData != null)
            {
                mDesignWidth = (int) applicationInfo.metaData.get(KEY_DESIGN_WIDTH);
                mDesignHeight = (int) applicationInfo.metaData.get(KEY_DESIGN_HEIGHT);
            }
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        AutoUtils.setSize(this, hasStatusBar(), mDesignWidth == 0 ? 320 : mDesignWidth, mDesignHeight == 0 ? 480 : mDesignHeight);//没有状态栏,设计尺寸的宽高
    }

    /**
     * 适配尺寸
     * @param layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //这个自定义View中在onMeasure做了尺寸适配
        userLayout = (AdaptionLayout)LayoutInflater.from(this).inflate(R.layout.layout_activity_adaption_root,null);
        setContentView(userLayout);
        //接下来初始化外面设置的layout
        userLayout.addLayout(layoutResID);
        //然后初始化默认的toolBar是否展示
        View autoToolBar = userLayout.findViewById(R.id.autoToolBar);
        autoToolBar.setVisibility(showToolBar()?View.VISIBLE:View.GONE);
    }

    /**
     * 是否需要包含状态栏来适配
     * @return
     */
    public boolean hasStatusBar(){
        return true;
    }

    /**
     * 是否展示标题栏
     * @return
     */
    public boolean showToolBar(){
        return false;
    }

    /**
     * 获取根布局
     * @return
     */
    public AdaptionLayout getUserLayout() {
        return userLayout;
    }
}
