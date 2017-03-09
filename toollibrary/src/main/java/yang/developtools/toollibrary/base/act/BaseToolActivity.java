package yang.developtools.toollibrary.base.act;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yangjh on 2017/3/8.
 * 这个类用于提供一些基础的工具功能的Activity,例如加载中的loading等
 * 以及基本的生命周期
 */
public abstract class BaseToolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView();
    }

    private void contentView(){
        setContentView(getLayoutId());
        initViews();
        initEvent();
        loadData();
    }

    /**
     * 初始化布局id
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化视图Views
     */
    public abstract void initViews();

    /**
     * 初始化点击事件
     */
    public abstract void initEvent();

    /**
     * 初始化数据加载
     */
    public abstract void loadData();

}
