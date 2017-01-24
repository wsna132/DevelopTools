package mtp.morningtec.com.demopullrefresh.baserecycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import mtp.morningtec.com.demopullrefresh.R;
import yang.developtools.toollibrary.base.widget.BaseRecyclerModel;
import yang.developtools.toollibrary.base.widget.BaseRecyclerView;

/**
 * Created by czk on 2017/1/24.
 */

public class DogView extends BaseRecyclerView<Dog> {

    private TextView mName;
    private TextView mGender;

    public DogView(Context context) {
        super(context);
    }

    public DogView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getContentId() {
        return R.layout.layout_dog_info;
    }

    @Override
    public void initViews(View layout) {
        mName = findView(layout,R.id.mName);
        mGender = findView(layout,R.id.mGender);
    }

    @Override
    public void bindViewAndData(Dog model) {
        mName.setText(model.mName);
        mGender.setText(model.Gender);
    }
}
