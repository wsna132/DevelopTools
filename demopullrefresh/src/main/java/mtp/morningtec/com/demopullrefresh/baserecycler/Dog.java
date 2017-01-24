package mtp.morningtec.com.demopullrefresh.baserecycler;

import yang.developtools.toollibrary.base.widget.BaseRecyclerModel;

/**
 * Created by czk on 2017/1/23.
 */

public class Dog extends BaseRecyclerModel<DogView>{

    public String mName;
    public String Gender;

    public Dog(){
        super();
    }

    public Dog(int size){
        super();
    }


    @Override
    public Class<DogView> bindViewClass() {
        return DogView.class;
    }
}
