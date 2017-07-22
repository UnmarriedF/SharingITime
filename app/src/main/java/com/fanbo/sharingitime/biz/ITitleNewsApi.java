package com.fanbo.sharingitime.biz;

import com.fanbo.sharingitime.entity.TitleNews;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by fanbo on 2017-07-22.
 */

public interface ITitleNewsApi {
    //   http://api.jisuapi.com/news/get?channel=头条&start=0&num=10&appkey=yourappkey
    @GET("get")
    Observable<TitleNews> getNewsList(@Query("channel") String type,
                                      @Query("start")int start,
                                      @Query("num")int num, @Query("appkey")String appkey);
}
