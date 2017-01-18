package yang.developtools.toollibrary.base.widget;

import java.util.HashMap;

/**
 * Created by czk on 2017/1/6.
 */

public class BaseRecyclerPresenter {

    private static BaseRecyclerPresenter mInstance;

    private int mCurrrntIndex = 0;
    private HashMap<String,Integer> modelTypeBinder = new HashMap<String,Integer>();//用于注册model于viewType的关系
    private HashMap<String,String> holderBinder = new HashMap<String,String>();//用于注册model与view的关系

    private BaseRecyclerPresenter(){

    }

    public static BaseRecyclerPresenter getInstance(){
        if(null == mInstance){
            synchronized (mInstance){
                if(null == mInstance){
                    mInstance = new BaseRecyclerPresenter();
                }
            }
        }
        return mInstance;
    }

    /**
     * 注册model的viewType
     * 因为RecyclerView.Adapter的getItemViewType方法返回的是int类型的值
     * 我不想让每个model自己实现返回，容易出错，所以在这里写一个自增来实现
     * @param model
     */
    public void registModel(BaseRecyclerModel model){
        if(null != modelTypeBinder.get(model.getClass().getSimpleName()))return;
        modelTypeBinder.put(model.getClass().getSimpleName(),mCurrrntIndex);
        mCurrrntIndex++;
    }

    public void bindModelView(BaseRecyclerModel model,BaseRecyclerView view){
        if(null != modelTypeBinder.get(model.getClass().getSimpleName()))return;
        modelTypeBinder.put(model.getClass().getSimpleName(),view.getClass().getName());
    }

}
