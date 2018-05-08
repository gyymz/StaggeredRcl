package com.example.chenlian.staggeredrcl.net;

import com.example.chenlian.staggeredrcl.model.SplashData;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by cl on 2018/5/3.
 */

public interface SplashService {
    String BASE_URL = Api.URL_GET_SPLASH_PIC;

    @GET("1080*1920")
    Observable<SplashData> getSplashPic();
}