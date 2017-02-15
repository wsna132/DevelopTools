package yang.developtools.toollibrary.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
    public RecyclerView mCustomRecyclerView;//左滑右划以及长按拖动的载体
    private BaseRecyclerAdapter mBaseAdapter;//显示数据的Adapter

    private View header;//下拉刷新时头部的视图


    private int currentPage = 0;//当前页码
    private int pageSize = 20;//每页的数量
    private final int loadMoreCount = 4;//距离多少个到底时可以加载更多
    private boolean hasMoreData = true;//是否还有更多的数据可以加载

    private int[] lastPositions;//用于计算还有多少项到底

    protected List<BaseRecyclerModel> mDatas = new ArrayList<BaseRecyclerModel>();

    //加载状态
    private int loadStatus = LOAD_END;
    private static int LOAD_END = 0;
    private static int LOADING = 1;


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
        mCustomRecyclerView = (RecyclerView)contentView.findViewById(R.id.mCustomRecyclerView);
        mCustomRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        initPullRefresh();
        initScroll();
        initAdapter(context);
    }

    private void initAdapter(Context context){
        if(null == mBaseAdapter) {
            mBaseAdapter = new BaseRecyclerAdapter(context,mDatas);
            TextView foot = new TextView(context);
            foot.setText("哈哈哈哈哈");
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            foot.setLayoutParams(params);
            mBaseAdapter.setFooterView(foot);
            mCustomRecyclerView.setAdapter(mBaseAdapter);
        }else{
            mBaseAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 初始化下拉刷新的效果等
     */
    private void initPullRefresh(){
        initPull();
        // 阻尼系数
        mPullRefreshLayout.setResistance(4.0f);
        mPullRefreshLayout.setRatioOfHeaderHeightToRefresh(1.2f);//多少倍头部视图的高度后可以刷新
        mPullRefreshLayout.setDurationToClose(200);//滚回刷新中的高度所用时间
        mPullRefreshLayout.setDurationToCloseHeader(500);//刷新完成后缩回去所用时间
// default is false
        mPullRefreshLayout.setPullToRefresh(false);//能否下拉刷新
// default is true
        mPullRefreshLayout.setKeepHeaderWhenRefresh(true);//刷新时保持头部可见
    }

    private void initPull() {
        if(null == header) {
            header = initHeadView();
        }
        mPullRefreshLayout.addPtrUIHandler((PtrUIHandler)header);//设置下拉的数据监听
        mPullRefreshLayout.setHeaderView(header);//设置默认的下拉头部
        mPullRefreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mDatas.clear();
                currentPage = 1;
                hasMoreData = true;
                loadData(currentPage,pageSize);
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
                //加载更多
                if(canRecyclerViewLoadMore(recyclerView) && loadStatus == LOAD_END && hasMoreData){
                    //先显示footView
                    loadStatus = LOADING;
                    mBaseAdapter.showFootView();
                    loadData(currentPage,pageSize);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    /**
     * 判断是否可以加载更多数据了
     * @param recyclerView
     * @return
     */
    private boolean canRecyclerViewLoadMore(RecyclerView recyclerView){
        boolean canLoadMore = false;
        //是否可以向下滑动
//        canLoadMore = !recyclerView.canScrollVertically(1);
        //首先判断滑动到的位置是否到了可以加载更多数据的地方
        canLoadMore = scrollToLoacMore(recyclerView);
        //然后判断数据是否还有加载更多的余地
        if(!hasMoreData){
            canLoadMore = false;
        }
        return canLoadMore;
    }

    /**
     * 根据还有多少项滑到底部来决定能否加载更多
     * @param recyclerView
     * @return
     */
    private boolean scrollToLoacMore(RecyclerView recyclerView){
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = getLayoutManagerLastVisiblePosition(layoutManager);
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        if(totalItemCount > loadMoreCount && lastVisibleItemPosition >= totalItemCount -loadMoreCount){//还差loadMoreCount项时，就加载更多
            return true;
        }
        return false;
    }

    /**
     * 获取当前可见的是第几项
     * @param layoutManagers
     * @return
     */
    private int getLayoutManagerLastVisiblePosition(RecyclerView.LayoutManager layoutManagers){
        RecyclerView.LayoutManager layoutManager = layoutManagers;
        int lastVisibleItemPosition = -1;
        //瀑布流布局
        if(layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            if (lastPositions == null) {
                lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
            }
            staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
            lastVisibleItemPosition = findMax(lastPositions);
        }
        if(layoutManager instanceof LinearLayoutManager || layoutManager instanceof GridLayoutManager){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager)layoutManager;
            //屏幕中最后一个可见子项的position
            lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        }
        return lastVisibleItemPosition;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


//    /**
//     * 刷新数据
//     */
//    public abstract void refreshData();

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
            currentPage++;
            initAdapter(getContext());
            mBaseAdapter.hideFootView();
            loadStatus = LOAD_END;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 用于添加加载的数据
     * @param models
     */
    public void addDatas(List<BaseRecyclerModel> models){
        //添加数据前先刷新一次显示
//        initAdapter(getContext());
        for(BaseRecyclerModel model:models){
            if(mDatas.contains(model)){
                continue;
            }
            mDatas.add(model);
        }
        if(models.size() < pageSize){
            hasMoreData = false;
        }

    }

    public int getDataSize(){
        return mDatas.size();
    }

    /**
     * 初始化下拉刷新的头部View
     * 如果需要自定义，需要继承了  PtrUIHandler接口的View
     * @return
     */
    private View initHeadView(){
        return new PtrClassicDefaultHeader(getContext());
    }

    /**
     * 设置下拉刷新的头部View
     * @param headView
     */
    public void setHeadView(View headView){
        try {
            if (headView instanceof PtrUIHandler) {
                mPullRefreshLayout.removePtrUIHandler((PtrUIHandler)header);
                header = headView;
                mPullRefreshLayout.setHeaderView(header);//设置默认的下拉头部
                mPullRefreshLayout.addPtrUIHandler((PtrUIHandler)header);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置加载更多的View
     * @param footView
     */
    public void setFootView(View footView){


    }

}
