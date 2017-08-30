package com.csp.myprojec.service;

import com.csp.myprojec.bean.CommentBean;
import com.csp.myprojec.bean.StateBean;
import com.csp.myprojec.bean.NewsBean;
import com.csp.myprojec.bean.UserBean;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by CSP on 2017/3/12.
 */

public interface ApiService {

    /**
     * 获取新闻接口
     *
     * @param action
     * @param page
     * @param category
     * @param token
     * @return
     */
    @GET("newsapi")
    Observable<NewsBean> getNews(@Query("action") String action,
                                 @Query("page") int page,
                                 @Query("category") String category,
                                 @Query("token") String token);

    /**
     * 获取每日一图接口
     *
     * @param action
     * @param page
     * @param token
     * @return
     */
    @GET("picofdayapi")
    Observable<NewsBean> getDailyPhoto(@Query("action") String action,
                                       @Query("page") int page,
                                       @Query("token") String token);

    /**
     * 用户登陆、注册接口
     *
     * @param action
     * @param uname
     * @param upassword
     * @return
     */
    @FormUrlEncoded
    @POST("userapi")
    Observable<UserBean> getUser(@Field("action") String action,
                                 @Field("uname") String uname,
                                 @Field("upassword") String upassword);

    /**
     * 用户收藏接口
     *
     * @param action
     * @param nid
     * @param token
     * @return
     */
    @GET("userapi")
    Observable<StateBean> getCollectState(@Query("action") String action,
                                          @Query("nid") int nid,
                                          @Query("token") String token);

    /**
     * 获取用户收藏接口
     *
     * @param action
     * @param page
     * @param token
     * @return
     */
    @GET("userapi")
    Observable<NewsBean> getCollection(@Query("action") String action,
                                       @Query("page") int page,
                                       @Query("token") String token);

    /**
     * 评论接口
     *
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST("commentapi")
    Observable<StateBean> doComment(@FieldMap(encoded = true) Map<String, String> options);

    /**
     * 获取评论接口
     *
     * @param action
     * @param nid
     * @param parentid
     * @param page
     * @return
     */
    @GET("commentapi")
    Observable<CommentBean> getComment(@Query("action") String action,
                                       @Query("nid") int nid,
                                       @Query("parentid") int parentid,
                                       @Query("page") int page);
}
