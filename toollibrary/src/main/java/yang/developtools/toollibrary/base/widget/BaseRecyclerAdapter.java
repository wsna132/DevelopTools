package yang.developtools.toollibrary.base.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by czk on 2017/1/5.
 */

public class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseHolder> {

    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    //获取从Activity中传递过来每个item的数据集合
    private List<BaseRecyclerModel> mDatas;
    //HeaderView, FooterView
    private View mHeaderView;
    private View mFooterView;

    private Context mContext;

    //构造函数
    public BaseRecyclerAdapter(Context context, List<BaseRecyclerModel> list){
        this.mDatas = list;
        this.mContext = context;
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getFooterView() {
        return mFooterView;
    }
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount()-1);
    }

    /** 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    * */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            Log.i("tagg","getItemViewType:" + mDatas.get(position).getViewType());
            return mDatas.get(position).getViewType();
        }
        //因为用了下拉刷新的GroupView，所以这里暂时不需要
        if (position == 0 && null != mHeaderView){
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount()-1 && null != mFooterView){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return mDatas.get(position).getViewType();
//        return TYPE_NORMAL;
    }

    //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
    @Override
    public BaseRecyclerAdapter.BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new BaseHolder(mHeaderView);
        }
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new BaseHolder(mFooterView);
        }
        View layout = BaseRecyclerPresenter.getInstance().createView(mContext,viewType);
        return new BaseHolder(layout);
    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    @Override
    public void onBindViewHolder(BaseRecyclerAdapter.BaseHolder holder, int position) {
        Log.i("tagg","getItemViewType:" + getItemViewType(position));
        if(getItemViewType(position) == TYPE_FOOTER){
            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }else{
            if(holder instanceof BaseRecyclerAdapter.BaseHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
//                ((BaseHolder)holder).tv.setText(mDatas.get(position-1));
                //总觉得这里会有BUG
                holder.showView.refreshShow(mDatas.get(position));
                return;
            }

        }
    }

    //在这里面加载ListView中的每个item的布局
    class BaseHolder extends RecyclerView.ViewHolder{

        BaseRecyclerView showView;

        public BaseHolder(View itemView) {
            super(itemView);
            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView){
                return;
            }
            if (itemView == mFooterView){
                return;
            }
            showView = (BaseRecyclerView)itemView;
        }
    }

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if(mHeaderView == null && mFooterView == null){
            return mDatas.size();
        }else if(mHeaderView == null && mFooterView != null){
            return mDatas.size() + 1;
        }else if (mHeaderView != null && mFooterView == null){
            return mDatas.size() + 1;
        }else {
            return mDatas.size() + 2;
        }
    }
}
