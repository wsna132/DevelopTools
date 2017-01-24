package yang.developtools.toollibrary.base.widget;


/**
 * Created by yangjh on 2017/1/6.
 * 这个类用来适配BaseRecyclerView
 * 注意：继承这个类的对象，在构造方法中一定要调用到BaseRecyclerModel的构造器
 *  可以在构造器调用super()方法
 */

public abstract class BaseRecyclerModel<T extends BaseRecyclerView> {

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
    public abstract Class<T> bindViewClass();


}
