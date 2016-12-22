package yang.developtools.toollibrary.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
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


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

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

    private void initView(){

    }

    /**
     * 初始化滑动方面的事件处理
     */
    private void initScroll(){
        scrollProblem();
    }

    /**
     * SwipeRefreshLayout和RecyclerView一起使用的时候，有时出现RecyclerView没有滑动到顶部，手指向下滑动时，触发了SwipeRefreshLayout的刷新事件，造成了冲突
     * 这样可以解决
     */
    private void scrollProblem(){
        if(null == mRecyclerView || null == mSwipeRefreshLayout)return;
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }
}
