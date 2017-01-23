package mtp.morningtec.com.demopullrefresh.baserecycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import mtp.morningtec.com.demopullrefresh.R;
import yang.developtools.toollibrary.base.widget.BaseRecyclerView;

/**
 * Created by czk on 2017/1/23.
 */

public class UserView extends BaseRecyclerView<User> {

    private TextView mName;

    public UserView(Context context) {
        super(context);
    }

    public UserView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getContentId() {
        return R.layout.layout_user_info;
    }

    @Override
    public void initViews(View layout) {
        mName = findView(R.id.mName);
    }

    @Override
    public void bindViewAndData(User model) {
        mName.setText(model.name);
    }
}
