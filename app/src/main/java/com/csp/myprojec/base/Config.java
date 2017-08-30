package com.csp.myprojec.base;

import com.github.lazylibrary.util.PreferencesUtils;

/**
 * Created by CSP on 2017/3/12.
 */

public class Config {
    public static String BASE_URL = PreferencesUtils.getString(MyApplication.getContext()
            , "ip_config", "http://123.57.13.94/csp/");//本机IP:192.168.137.1
    //服务器IP:123.57.13.94
    public final static String MASTER = "Master";
    public final static String PHOTOGRAPHY = "PhotoGraphy";
    public final static String TRAVEL = "Travel";
    public final static String CULTURAL = "Cultural";
    public final static String OCEAN = "Ocean";
    public final static String NATURAL_DISASTER = "NaturalDisaster";
    public final static String KNOWLEDGE_OF_ANIMALS = "KnowledgeOfAnimals";
    public final static String COSMIC_SPACE = "CosmicSpace";
    public final static String DAILY_PHOTO = "DailyPhoto";

}
