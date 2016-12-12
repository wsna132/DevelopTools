package com.yang.developtools.model.core.callback;


import com.yang.developtools.util.LogUtil;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by yangjh on 2016/11/6 0006.
 */

public abstract class OnResponseCallback<T> {

    /**
     * 网络访问成功时的处理
     * @param response
     */
    public void success(Response<T> response){
        logResponseData(response);

        if(response.isSuccessful()){//code >= 200 && code < 300    访问成功时
            onSuccess(response.body());
        }else{//网络访问出错
            onFailure("网络出错啦");
        }
    }

    /**
     * 其他错误，解析出错？
     * @param t
     */
    public void fail(Throwable t){
        logResponseFail(t);
        //这里需要根据报错来判断是哪几种类型的报错
        onFailure(t.getLocalizedMessage());
    }

    /**
     * 成功的回调
     * @param data
     */
    public abstract void onSuccess(T data);

    /**
     * 失败的回调
     * @param failMsg
     */
    public abstract void onFailure(String failMsg);

    /**
     * 用于输出网络返回信息
     * @param response
     */
    private void logResponseData(Response<T> response){
        LogUtil.logI("NetResponseIsSuccessFUL:" + response.isSuccessful());
        LogUtil.logI("NetResponseMsg:" + response.message());
        LogUtil.logI("NetResponseEntity:" + response.body().getClass().getSimpleName());
        LogUtil.logI("NetResponseCode:" + response.code());
        try {
            LogUtil.logI("NetResponseRaw:" + response.raw().body().string());
            LogUtil.logI("NetResponseRaw2:" + response.raw().body().string());
        }catch (Exception ioe){
            ioe.printStackTrace();
        }
        try {
            LogUtil.logI("NetResponseErrorMsg:" + response.errorBody().string());
        }catch (Exception ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * 用于输出网络访问失败的原因
     * @param t
     */
    private void logResponseFail(Throwable t){
        LogUtil.logI("NetResponseFail:" + t.toString());
    }



}
