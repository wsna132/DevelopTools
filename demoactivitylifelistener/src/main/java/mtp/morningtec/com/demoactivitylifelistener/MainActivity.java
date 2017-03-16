package mtp.morningtec.com.demoactivitylifelistener;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import yang.developtools.toollibrary.common.util.actlifelistener.ActLifeListener;
import yang.developtools.toollibrary.common.util.actlifelistener.LifeAttchManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LifeAttchManager.getInstance().ObserveAct(this, new ActLifeListener() {
            @Override
            public void onCreate(Bundle bundle) {
                Log.d("MainActivity","onCreate");
            }

            @Override
            public void onStart() {
                Log.d("MainActivity","onStart");
            }

            @Override
            public void onResume() {
                Log.d("MainActivity","onResume");
            }

            @Override
            public void onPause() {
                Log.d("MainActivity","onPause");
            }

            @Override
            public void onStop() {
                Log.d("MainActivity","onStop");
            }

            @Override
            public void onDestroy() {
                Log.d("MainActivity","onDestroy");
            }
        });
    }

}
