package mtp.morningtec.com.demopullrefresh.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;

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
                for(int i = 0; i < 10; i++){
                    User user = new User();
                    user.name = (page + i) + "";
                    list.add(user);
                }
                addDatas(list);
            }
        },3000);
    }
}
