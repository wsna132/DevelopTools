package mtp.morningtec.com.demopullrefresh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.util.Util;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import mtp.morningtec.com.demopullrefresh.baserecycler.Dog;
import mtp.morningtec.com.demopullrefresh.baserecycler.User;
import yang.developtools.toollibrary.base.widget.BaseRecyclerAdapter;
import yang.developtools.toollibrary.base.widget.BaseRecyclerModel;
import yang.developtools.toollibrary.base.widget.BaseRecyclerView;
import yang.developtools.toollibrary.common.device.AppLocalInfo;
import yang.developtools.toollibrary.common.widget.pullrefresh.PtrClassicDefaultHeader;
import yang.developtools.toollibrary.common.widget.pullrefresh.PtrClassicFrameLayout;
import yang.developtools.toollibrary.common.widget.pullrefresh.PtrDefaultHandler;
import yang.developtools.toollibrary.common.widget.pullrefresh.PtrFrameLayout;
import yang.developtools.toollibrary.common.widget.pullrefresh.PtrHandler;

public class MainActivity extends AppCompatActivity {

    private PtrFrameLayout pullLayout;
    private PtrClassicFrameLayout mPtrFrame;
    private RecyclerView mCustomRecyclerView;

    private MyAdapter adapter;
    private BaseRecyclerAdapter baseAdapter;


    List<BaseRecyclerModel> views = new ArrayList<BaseRecyclerModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        demoInstallDataApk();
//        demoPtrClassicFrameLayout();//有默认刷新头部的
        demoPtrFrameLayout();//没有默认刷新头部的
    }

    private void initView(){
        mCustomRecyclerView = (RecyclerView)findViewById(R.id.mCustomRecyclerView);
        mCustomRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter();


        for(int i = 0;i < 10;i++){
            User user = new User();
            user.name = i + "";
            views.add(user);
            Dog dog = new Dog();
            dog.mName = "dog:" + i;
            dog.Gender = "dogGender:" + i;
            views.add(dog);
        }
        baseAdapter = new BaseRecyclerAdapter(this,views);

        mCustomRecyclerView.setAdapter(baseAdapter);
//        mCustomRecyclerView.setAdapter(adapter);
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

    private void demoInstallDataApk(){
//        try {
//            copyApk(this, "Gquan.apk", getCacheDir().getAbsolutePath() + "/Gquan.apk");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        TextView text = (TextView)findViewById(R.id.text);
//        text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String cmd = "chmod 777 " +getCacheDir().getAbsolutePath() + "/Gquan.apk";
//                try {
//                    java.lang.Process pross = Runtime.getRuntime().exec(cmd);
//                    int result = pross.exitValue();
//                    if(result == 0){
//                        //表示成功
//                    }else{
//                        pross.getErrorStream();//获取报错的数据流
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(new File(getCacheDir().getAbsolutePath() + "/Gquan.apk")),
//                        "application/vnd.android.package-archive");
//                startActivity(intent);
//            }
//        });
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
//                        TextView nnn = new TextView(MainActivity.this);
//                        nnn.setText("iAmNew");
//                        mCustomRecyclerView.addView(nnn);
                        for(int i = 10;i < 20;i++){
                            User user = new User();
                            user.name = i + "";
                            views.add(user);
                            Dog dog = new Dog();
                            dog.mName = "dog:" + i;
                            dog.Gender = "dogGender:" + i;
                            views.add(dog);
                        }
                        baseAdapter.notifyDataSetChanged();

                        pullLayout.refreshComplete();

                        Intent intent = new Intent(MainActivity.this,TestActivity.class);
                        startActivity(intent);

                    }
                },3000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
//                return false;
            }
        });
        //下拉时是否保持内部布局不下拉
//        pullLayout.setPinContent(true);
    }


    /**
     * 这个方法用于将需要安装的asstes下的apk拷贝到指定路径
     * @param assetsName
     * @param strOutFileName
     */
    private void copyApk(Context context, String assetsName, String strOutFileName)throws IOException {
        SharedPreferences copy = context.getSharedPreferences("copyHistory",Context.MODE_PRIVATE);
        if(copy.getBoolean("copyed",false))return;
        InputStream myInput;
        File gquanFile = new File(strOutFileName);
        if(!gquanFile.exists()){
            gquanFile.getParentFile().mkdirs();
            gquanFile.createNewFile();
        }
        OutputStream myOutput = new FileOutputStream(strOutFileName);
        myInput = context.getAssets().open(assetsName);
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while(length > 0)
        {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();
        copy.edit().putBoolean("copyed",false).commit();
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            TextView text = new TextView(MainActivity.this);
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,AppLocalInfo.dip2px(MainActivity.this,20));
//            text.setGravity(Gravity.CENTER);
//            text.setLayoutParams(params);
//            text.setHeight(AppLocalInfo.dip2px(MainActivity.this,40));
            View layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_user_info,null);




            return new MyViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            holder.tv.setText(position + "");
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            View layout;
            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
                layout = view;
                tv = (TextView)layout.findViewById(R.id.mName);
            }


        }
    }
}
