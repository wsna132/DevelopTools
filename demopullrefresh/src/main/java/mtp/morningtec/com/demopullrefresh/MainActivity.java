package mtp.morningtec.com.demopullrefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import yang.developtools.toollibrary.common.widget.pullrefresh.PtrClassicDefaultHeader;
import yang.developtools.toollibrary.common.widget.pullrefresh.PtrClassicFrameLayout;
import yang.developtools.toollibrary.common.widget.pullrefresh.PtrDefaultHandler;
import yang.developtools.toollibrary.common.widget.pullrefresh.PtrFrameLayout;
import yang.developtools.toollibrary.common.widget.pullrefresh.PtrHandler;

public class MainActivity extends AppCompatActivity {

    private PtrFrameLayout pullLayout;
    private PtrClassicFrameLayout mPtrFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        demoPtrClassicFrameLayout();//有默认刷新头部的
        demoPtrFrameLayout();//没有默认刷新头部的
    }


    private void demoPtrClassicFrameLayout(){
        mPtrFrame = (PtrClassicFrameLayout)findViewById(R.id.material_style_ptr_frame);



        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                // mPtrFrame.autoRefresh();
            }
        }, 100);
    }


    private void demoPtrFrameLayout(){
        pullLayout = (PtrFrameLayout)findViewById(R.id.material_style_ptr_frame);
        initPull();
        // 阻尼系数
        pullLayout.setResistance(1.7f);
        pullLayout.setRatioOfHeaderHeightToRefresh(1.2f);//多少倍头部视图的高度后可以刷新
        pullLayout.setDurationToClose(200);//滚回刷新中的高度所用时间
        pullLayout.setDurationToCloseHeader(1000);//刷新完成后缩回去所用时间
// default is false
        pullLayout.setPullToRefresh(false);//能否下拉刷新
// default is true
        pullLayout.setKeepHeaderWhenRefresh(true);//刷新时保持头部可见
    }

    private void initPull(){
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(this);
        pullLayout.addPtrUIHandler(header);//设置下拉的数据监听
        pullLayout.setHeaderView(header);//设置默认的下拉头部
        pullLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
//                updateData();
                pullLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullLayout.refreshComplete();
                    }
                },3000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        //下拉时是否保持内部布局不下拉
//        pullLayout.setPinContent(true);
    }
}
