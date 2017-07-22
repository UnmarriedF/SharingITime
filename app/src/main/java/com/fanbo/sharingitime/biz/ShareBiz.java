package com.fanbo.sharingitime.biz;

import com.fanbo.sharingitime.entity.TitleNews;
import com.fanbo.sharingitime.util.Const;
import com.fanbo.sharingitime.util.StringUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 分享界面Biz
 * Created by fanbo on 2017-07-21.
 */

public class ShareBiz {


    /**
     * okhttp访问网络资源
     */
    public void getTitleNewsDateOKHTTP(int start, int num,String type, final GetTitleNewsListener getTitleNewsListener){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS)
                .build();
        //创建网络请求，如果没有设置，默认为get
        //在请求的对象里面传入连接
        Request request = new Request.Builder().url(StringUtil.getNewsUrl(start,num,type)).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                TitleNews titleNews = gson.fromJson(response.body().string(),TitleNews.class);
                List<TitleNews.ResultBean.ListBean> listBeen = titleNews.getResult().getList();
                getTitleNewsListener.onDataGetEnd(listBeen);
            }
        });

    }
    /**
     * retrofit + rxjava访问网络资源
     */

    public void getTitleNewsDateRETROFIT(Subscriber<TitleNews> subscriber, int start, int num, String type){
        //   http://api.jisuapi.com/news/get?channel=头条&start=0&num=10&appkey=yourappkey
        String baseUrl = "http://api.jisuapi.com/news/";
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(baseUrl).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ITitleNewsApi iTitleNewsApi = retrofit.create(ITitleNewsApi.class);
        iTitleNewsApi.getNewsList(type,start,num, Const.TITLE_NEWS_APP_KEY).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(subscriber);
    }
    //不使用rxjava
//    public void getTitleNewsDateRETROFIT(Subscriber<TitleNews> subscriber,int start, int num, String type){
//        //   http://api.jisuapi.com/news/get?channel=头条&start=0&num=10&appkey=yourappkey
//        String baseUrl = "http://api.jisuapi.com/news/";
//        Retrofit retrofit = new Retrofit.Builder().
//                baseUrl(baseUrl).
//                addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ITitleNewsApi iTitleNewsApi = retrofit.create(ITitleNewsApi.class);
//        retrofit2.Call<TitleNews> call = iTitleNewsApi.getNewsList(type,start,num, Const.TITLE_NEWS_APP_KEY);
//        call.enqueue(new retrofit2.Callback<TitleNews>() {
//            @Override
//            public void onResponse(retrofit2.Call<TitleNews> call, retrofit2.Response<TitleNews> response) {
//
//            }
//            @Override
//            public void onFailure(retrofit2.Call<TitleNews> call, Throwable throwable) {
//
//            }
//        });
//    }
    public interface GetTitleNewsListener{
      void   onDataGetEnd (List<TitleNews.ResultBean.ListBean> listBeen);
    }
}
