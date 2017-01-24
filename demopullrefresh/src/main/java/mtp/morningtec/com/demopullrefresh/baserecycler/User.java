package mtp.morningtec.com.demopullrefresh.baserecycler;

import yang.developtools.toollibrary.base.widget.BaseRecyclerModel;

/**
 * Created by czk on 2017/1/23.
 */

public class User extends BaseRecyclerModel<UserView> {

    public String name;

    @Override
    public Class bindViewClass() {
        return UserView.class;
    }
}
