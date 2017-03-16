package mtp.morningtec.com.democonstraintlayout;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import yang.developtools.toollibrary.base.act.BaseAdaptionActivity;

/**
 * 这个类做了自适应的布局
 */
public class MainActivity extends BaseAdaptionActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////        View mainlayout = findViewById(R.id.mainlayout);
////        mainlayout.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(MainActivity.this,Activity2.class);
////                startActivity(intent);
////            }
////        });
//    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        final ImageView im1 = (ImageView)findViewById(R.id.im1);
        im1.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams view = im1.getLayoutParams();
                Log.d("tag","AutoPost:Width:" + view.width + ",Height:" + view.height);
            }
        });
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Activity2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void loadData() {

    }
}
