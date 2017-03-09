package yang.developtools.toollibrary.common.adaption;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import yang.developtools.toollibrary.common.adaption.util.AutoUtils;

/**
 * Created by yangjh on 2017/3/8.
 * 用于适配尺寸的View，在onSizeChanged中做了自己以及子View的尺寸处理
 */

public class AdaptionLayout extends LinearLayout {


    public AdaptionLayout(Context context) {
        super(context);
    }

    public AdaptionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdaptionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AdaptionLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(!isInEditMode()){
            //适配尺寸,存在缺陷，子View被动态设置尺寸时，怎么适配
            AutoUtils.auto(this);
        }
    }

    /**
     * 添加子布局，因为是LinearLayout并且设置的orientation为vertical
     * 所以是纵向布局下去
     * @param layoutId
     */
    public void addLayout(int layoutId){
        View layout = LayoutInflater.from(getContext()).inflate(layoutId,null);
        addView(layout);
    }
}
