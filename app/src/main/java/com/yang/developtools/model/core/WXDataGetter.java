package com.yang.developtools.model.core;

import com.yang.developtools.model.core.callback.OnResponseCallback;
import com.yang.developtools.model.entity.parser.HotWeiXinEntityListParser;
import com.yang.developtools.model.nettool.NewsDataUrlService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yangjh on 2016/11/6 0006.
 * 用于获取微信热门精选数据
 */

public class WXDataGetter {

    private static final String WX_HOST = "http://apis.baidu.com/txapi/weixin/";
    private static final String WX_API_KEY = "1148d462a04db022168176166925c670";

    private static WXDataGetter mInstance;

    private Retrofit wxRetrofit;
    private NewsDataUrlService wxDataGetter;

    private WXDataGetter(){
        //加载微信热门精选的初始化
        wxRetrofit = new Retrofit.Builder()
                //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(WX_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        wxDataGetter = wxRetrofit.create(NewsDataUrlService.class);
    }

    public static WXDataGetter getmInstance(){
        if(null == mInstance){
                    mInstance = new WXDataGetter();
        }
        return mInstance;
    }

    public void getWXHotInfo(int page, int size, String keyword,final OnResponseCallback<HotWeiXinEntityListParser> callback){
        Call<HotWeiXinEntityListParser> call = wxDataGetter.listRepos(WX_API_KEY,size,0,keyword,page);
        call.enqueue(new Callback<HotWeiXinEntityListParser>() {
            @Override
            public void onResponse(Call<HotWeiXinEntityListParser> call, Response<HotWeiXinEntityListParser> response) {
                callback.success(response);
            }
            @Override
            public void onFailure(Call<HotWeiXinEntityListParser> call, Throwable t) {
                callback.fail(t);
            }
        });
    }

}
