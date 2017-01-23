package yang.developtools.toollibrary.base.widget;

/**
 * Created by yangjh on 2017/1/6.
 * 这个类用来适配BaseRecyclerView
 */

public abstract class BaseRecyclerModel {

    public BaseRecyclerModel(){
        //注册自己的ViewType
        BaseRecyclerPresenter.getInstance().registModel(this);
        //绑定BaseRecyclerView
        BaseRecyclerPresenter.getInstance().bindModelView(this,bindViewClass());
    }

    public int getViewType() {
        return BaseRecyclerPresenter.getInstance().getModelType(this);
    }

    /**
     * 绑定用于在Adapter中显示的View
     * @return
     */
    public abstract Class<BaseRecyclerView> bindViewClass();


}
