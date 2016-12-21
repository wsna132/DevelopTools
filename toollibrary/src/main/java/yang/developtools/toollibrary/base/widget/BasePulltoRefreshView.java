package yang.developtools.toollibrary.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import yang.developtools.toollibrary.R;

/**
 * Created by yanjh on 2016/12/21 0021.
 * 一个基础的用于下拉刷新，上拉加载更多的控件
 */

public class BasePulltoRefreshView extends LinearLayout{

    public BasePulltoRefreshView(Context context) {
        super(context);
        initView(context);
    }

    public BasePulltoRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BasePulltoRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @SuppressLint("NewApi")
    public BasePulltoRefreshView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    /**
     * 初始化
     */
    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.widget_pull_to_refresh,this,true);
    }
}
