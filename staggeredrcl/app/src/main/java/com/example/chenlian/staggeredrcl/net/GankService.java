package com.example.chenlian.staggeredrcl.net;

import com.example.chenlian.staggeredrcl.model.GankItemData;
import com.example.chenlian.staggeredrcl.model.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by cl on 2018/5/3.
 */

public interface GankService {
    String BASE_URL = Api.URL_GET_GANK;

    @GET("{suburl}")
    Observable<HttpResult<List<GankItemData>>> getGankItemData(@Path("suburl") String suburl);
}