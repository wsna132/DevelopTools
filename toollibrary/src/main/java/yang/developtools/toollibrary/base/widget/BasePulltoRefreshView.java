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
import yang.developtools.toollibrary.common.widget.listview.SwipeMenuRecyclerView;
import yang.developtools.toollibrary.common.widget.pullrefresh.PtrClassicDefaultHeader;
import yang.developtools.toollibrary.common.widget.pullrefresh.PtrDefaultHandler;
import yang.developtools.toollibrary.common.widget.pullrefresh.PtrFrameLayout;
import yang.developtools.toollibrary.common.widget.pullrefresh.PtrHandler;
import yang.developtools.toollibrary.common.widget.pullrefresh.PtrUIHandler;

/**
 * Created by yanjh on 2016/12/21 0021.
 * 一个基础的用于下拉刷新，上拉加载更多的控件
 */

public abstract class BasePulltoRefreshView extends LinearLayout{

    private PtrFrameLayout mPullRefreshLayout;//下拉刷新的载体
    private SwipeMenuRecyclerView mCustomRecyclerView;//左滑右划以及长按拖动的载体

    private View header;//下拉刷新时头部的视图


    private int currentPage = 0;//当前页码
    private int pageSize = 20;//每页的数量


//    private SwipeRefreshLayout mSwipeRefreshLayout;
//    private RecyclerView mRecyclerView;

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
        View contentView = LayoutInflater.from(context).inflate(R.layout.widget_pull_to_refresh,this,true);
        mPullRefreshLayout = (PtrFrameLayout)contentView.findViewById(R.id.mPullRefreshLayout);
        mCustomRecyclerView = (SwipeMenuRecyclerView)contentView.findViewById(R.id.mCustomRecyclerView);

        initPullRefresh();
        initScroll();
    }

    /**
     * 初始化下拉刷新的效果等
     */
    private void initPullRefresh(){
        initPull();
        // 阻尼系数
        mPullRefreshLayout.setResistance(1.7f);
        mPullRefreshLayout.setRatioOfHeaderHeightToRefresh(1.2f);//多少倍头部视图的高度后可以刷新
        mPullRefreshLayout.setDurationToClose(200);//滚回刷新中的高度所用时间
        mPullRefreshLayout.setDurationToCloseHeader(1000);//刷新完成后缩回去所用时间
// default is false
        mPullRefreshLayout.setPullToRefresh(false);//能否下拉刷新
// default is true
        mPullRefreshLayout.setKeepHeaderWhenRefresh(true);//刷新时保持头部可见
    }

    private void initPull() {
        header = initHeadView();
        mPullRefreshLayout.addPtrUIHandler((PtrUIHandler)header);//设置下拉的数据监听
        mPullRefreshLayout.setHeaderView(header);//设置默认的下拉头部
        mPullRefreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
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
        if(null == mCustomRecyclerView || null == mCustomRecyclerView)return;
        mCustomRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mCustomRecyclerView.setEnabled(topRowVerticalPosition >= 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    /**
     * 刷新数据
     */
    public abstract void refreshData();

    /**
     * 加载数据
     * @param page          页码
     * @param pageSize      每页数据数量
     */
    public abstract void loadData(int page,int pageSize);

    /**
     * 刷新结束
     */
    public void refreshComplete(){
        if(null == mPullRefreshLayout)return;
        try{
            mPullRefreshLayout.refreshComplete();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 初始化下拉刷新的头部View
     * 如果需要自定义，需要继承了  PtrUIHandler接口的View
     * @return
     */
    public View initHeadView(){
        return new PtrClassicDefaultHeader(getContext());
    }

}
