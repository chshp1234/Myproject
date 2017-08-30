package com.csp.myprojec.custom;

import com.csp.myprojec.base.Config;
import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.service.ApiService;
import com.github.lazylibrary.util.DeviceStatusUtils;
import com.github.lazylibrary.util.PreferencesUtils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class RetrofitFactory {
    private static HttpClientFactory httpClient = new HttpClientFactory();

    private static ApiService apiService = new Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(FastJsonConverterFactory.create())//添加fastjson转换器
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加rxjava适配器
            .client(httpClient.getHttpClient())//添加一个httpclient
            .build()
            .create(ApiService.class);

    public static ApiService getInstance() {
        return apiService;
    }
}
