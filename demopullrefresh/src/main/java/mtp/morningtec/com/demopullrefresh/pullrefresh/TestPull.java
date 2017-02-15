package mtp.morningtec.com.demopullrefresh.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mtp.morningtec.com.demopullrefresh.baserecycler.User;
import yang.developtools.toollibrary.base.widget.BasePulltoRefreshView;

/**
 * Created by czk on 2017/1/25.
 */

public class TestPull extends BasePulltoRefreshView {

    public TestPull(Context context) {
        super(context);
    }

    public TestPull(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestPull(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void loadData(final int page,final int pageSize) {
        mCustomRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                List list = new ArrayList();
                for(int i = getDataSize(); i < getDataSize() + 20; i++){
                    if(i > 55){
                        break;
                    }
                    User user = new User();
                    user.name = ( i) + "";
                    list.add(user);
                }
                addDatas(list);
                refreshComplete();
            }
        },500);
    }

    @Override
    public View getRefreshHeadView() {
        return null;
    }

    @Override
    public View getLoadMoreFootView() {
        return null;
    }
}
