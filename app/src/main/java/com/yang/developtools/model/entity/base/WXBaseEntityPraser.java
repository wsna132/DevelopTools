package com.yang.developtools.model.entity.base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangjh on 2016/11/6 0006.
 * 微信热门精选的用语解析的基类
 */

public class WXBaseEntityPraser<E> implements Serializable{

    //返回状态码
    public int code;

    //返回状态消息
    public String msg;

    //数据列表
    public List<E> newslist;

}
