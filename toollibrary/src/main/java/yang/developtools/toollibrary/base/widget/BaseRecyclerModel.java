package yang.developtools.toollibrary.base.widget;

/**
 * Created by yangjh on 2017/1/6.
 * 这个类用来适配BaseRecyclerView
 */

public abstract class BaseRecyclerModel {

    private String mViewType = getClass().getName();

    public String getViewType() {
        return mViewType;
    }

    public void setViewType(String mViewType) {
        this.mViewType = mViewType;
    }

    public abstract BaseRecyclerView createModelView(String mViewType);

}
