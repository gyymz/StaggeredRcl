package com.example.chenlian.staggeredrcl.net;

import com.example.chenlian.staggeredrcl.util.NetManager;

/**
 * Created by cl on 2018/5/3.
 */

public class ApiService {
    public static ApiService getInstance() {
        return ApiService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ApiService INSTANCE = new ApiService();
    }

    public <S> S initService(Class<S> service) {
        if (service.equals(GankService.class) || service.equals(SplashService.class)) {
            return NetManager.getInstance().create(service);
        } else if (service.equals(GirlService.class)) {
            return NetManager.getInstance().create1(service);
        }
        return null;
    }
}