package com.shen.joke.core.api;


import com.shen.joke.core.HttpResult;
import com.shen.joke.model.Joke;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by shenjianlis on 2016/8/31.
 * 测试网络请求的接口
 */
public interface JokeApi {

    //请求的url地址
    @GET("joke/query")
    Observable<HttpResult<List<Joke>>> queryJokeInfo();

    //请求的url地址
    @GET("joke/query_by_date")
    Observable<HttpResult<List<Joke>>> queryJokeInfoByDate(@Query("date") String date);

    //请求的url地址
    @GET("joke/query/base")
    Observable<HttpResult<List<Joke>>> queryJokeBaseInfo(@Query("num") String num);

    //请求的url地址
    @GET("/joke/update")
    Observable<HttpResult<List<Joke>>> updateJokeBaseInfo(@Query("lastId") String lastId, @Query("num") String num);

}
