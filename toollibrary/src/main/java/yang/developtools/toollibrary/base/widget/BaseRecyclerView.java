package yang.developtools.toollibrary.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yangjh on 2017/1/5.
 * 用于BaseRecyclerAdapter的适配的View
 */

public abstract class BaseRecyclerView extends View{


    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化这个View的布局
     * @return
     */
    public abstract View createView();

    /**
     * 初始化每个控件
     */
    public abstract void initViews();


    public abstract void show(BaseRecyclerModel model);

}
