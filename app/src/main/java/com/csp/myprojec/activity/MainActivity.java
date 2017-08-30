package com.csp.myprojec.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.csp.myprojec.R;
import com.csp.myprojec.modular.dailyphoto.DailyPhotoFragment;
import com.csp.myprojec.modular.me.MeFragment;
import com.csp.myprojec.modular.news.NewsFragment;
import com.umeng.socialize.UMShareAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

/**
 * Created by CSP on 2017/3/12.
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainfragment)
    FrameLayout mainfragment;
    @BindView(R.id.bottomTabLayout)
    PagerBottomTabLayout bottomTabLayout;

    DailyPhotoFragment dailyPhotoFragment;
    NewsFragment newsFragment;
    MeFragment meFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        replaceFragment(dailyPhotoFragment);
    }

    private void init() {
        dailyPhotoFragment = new DailyPhotoFragment();
        newsFragment = new NewsFragment();
        meFragment = new MeFragment();

        Controller controller = bottomTabLayout.builder()
                .addTabItem(R.mipmap.ic_insert_photo, "日推", Color.parseColor("#29b6f6"))
                .addTabItem(R.mipmap.ic_local_library, "新闻", Color.parseColor("#e84e40"))
                .addTabItem(R.mipmap.ic_person, "我的", Color.parseColor("#9e9e9e"))
                .setMode(TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();
        controller.addTabItemClickListener(new OnTabItemSelectListener() {

            @Override
            public void onSelected(int index, Object tag) {
                switch (index) {
                    case 0:
                        if (dailyPhotoFragment == null) {
                            dailyPhotoFragment = new DailyPhotoFragment();
                        }
                        replaceFragment(dailyPhotoFragment);
                        break;
                    case 1:
                        if (newsFragment == null) {
                            newsFragment = new NewsFragment();
                        }
                        replaceFragment(newsFragment);
                        break;
                    case 2:
                        if (meFragment == null) {
                            meFragment = new MeFragment();
                        }
                        replaceFragment(meFragment);
                        break;
                }
            }

            @Override
            public void onRepeatClick(int index, Object tag) {

            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.mainfragment, fragment);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
