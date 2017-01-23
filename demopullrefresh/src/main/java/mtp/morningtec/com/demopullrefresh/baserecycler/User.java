package mtp.morningtec.com.demopullrefresh.baserecycler;

import yang.developtools.toollibrary.base.widget.BaseRecyclerModel;
import yang.developtools.toollibrary.base.widget.BaseRecyclerView;

/**
 * Created by czk on 2017/1/23.
 */

public class User extends BaseRecyclerModel {

    public String name;


    @Override
    public Class<BaseRecyclerView> bindViewClass() {
        return null;
    }
}
