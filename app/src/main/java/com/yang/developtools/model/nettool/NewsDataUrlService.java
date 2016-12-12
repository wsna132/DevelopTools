package com.yang.developtools.model.nettool;

import com.yang.developtools.model.entity.parser.HotWeiXinEntityListParser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yangjh on 2016/11/6 0006.
 */

public interface NewsDataUrlService {

    //获取微信热门精选
    @GET("wxhot")
    Call<HotWeiXinEntityListParser> listRepos(@Header("apikey") String apiKey,
                                              @Query("num") int num,//请求数量
                                              @Query("rand") int rand,//是否随机，1为随机
                                              @Query("word") String word,//关键字
                                              @Query("page") int page//页码
//                                      @Path("src") String src//指定文章来源，默认：人民日报
                                        );


}
