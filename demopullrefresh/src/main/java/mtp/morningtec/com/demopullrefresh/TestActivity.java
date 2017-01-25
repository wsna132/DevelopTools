package mtp.morningtec.com.demopullrefresh;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import mtp.morningtec.com.demopullrefresh.pullrefresh.TestPull;

/**
 * Created by czk on 2017/1/25.
 */

public class TestActivity extends Activity {

    private TestPull pull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("tagg","onCreate");
        setContentView(R.layout.layout_test_pull);
        pull = (TestPull)findViewById(R.id.testpull);
    }
}
