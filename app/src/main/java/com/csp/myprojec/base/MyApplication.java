package com.csp.myprojec.base;

import android.app.Application;
import android.content.Context;

import com.github.lazylibrary.util.PreferencesUtils;
import com.umeng.socialize.*;
import com.umeng.socialize.Config;
import com.umeng.socialize.common.QueuedWork;

/**
 * Created by CSP on 2017/3/13.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        Config.DEBUG=true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);
    }

    {
        PlatformConfig.setWeixin("wx9491ac955d91c0c3","009929339aa8cc858d30db7beaa3e404");
        PlatformConfig.setSinaWeibo("","","");
    }

    public static Context getContext(){
        return context;
    }

    public static String getToken(){
        return PreferencesUtils.getString(getContext(),"token","");
    }
}
