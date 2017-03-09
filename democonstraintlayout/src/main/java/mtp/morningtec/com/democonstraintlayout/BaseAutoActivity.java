package mtp.morningtec.com.democonstraintlayout;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by czk on 2017/3/8.
 */

public abstract class BaseAutoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public void setContentView(int layoutId){
        setContentView(layoutId);

    }


}
