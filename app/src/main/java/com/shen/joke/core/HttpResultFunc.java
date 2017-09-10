package com.shen.joke.core;

import com.shen.netclient.util.LogUtils;

import rx.functions.Func1;

/**
 * Created by shenjianli on 2016/8/15.
 */
public class HttpResultFunc<T> implements Func1<HttpResult<T>,T>{

    @Override
    public T call(HttpResult<T> httpResult) {
        if (httpResult.getCode() == HttpResult.REQ_EXCE) {
            throw new ApiException(httpResult.getMsg());
        }
        else if (httpResult.getCode() == HttpResult.REQ_FAIL) {
           LogUtils.i("请求失败" + httpResult.getMsg());
        }
        return httpResult.getData();
    }

}
