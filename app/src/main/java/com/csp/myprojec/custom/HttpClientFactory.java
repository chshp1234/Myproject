package com.csp.myprojec.custom;

import android.util.Log;

import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.utils.NetUtils;
import com.github.lazylibrary.util.NetWorkUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.path;

/**
 * Created by CSP on 2017/3/21.
 */

public class HttpClientFactory {

    private OkHttpClient httpClient;

    public HttpClientFactory() {
        File cacheFile = new File(MyApplication.getContext().getCacheDir().getAbsolutePath(),
                "HttpCache");//缓存路径
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);//缓存文件为100MB
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
                .build();
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    //cache
    Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {

//        首先，给OkHttp设置拦截器
//        client.interceptors().add(interceptor);
//        -然后，在拦截器内做Request拦截操作
//        在每个请求发出前，判断一下网络状况，如果没问题继续访问，如果有问题，则设置从本地缓存中读取
//        接下来是设置Response
        Request request = chain.request();
        if (!NetUtils.isConnected(MyApplication.getContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Log.i("OkHttp", "网络不可用");
        }

        //先判断网络，网络好的时候，移除header后添加haunch失效时间为1小时，网络未连接的情况下设置缓存时间为4周
        Response response = chain.proceed(request);
        if (NetUtils.isConnected(MyApplication.getContext())) {
            int maxAge = 60 * 60; // read from cache for 1 minute
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
            Log.i("OkHttp", "网络可用");
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    };

}
