package com.csp.myprojec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;

import com.csp.myprojec.R;
import com.csp.myprojec.base.BaseActivity;
import com.csp.myprojec.modular.comment.CommentActivity;
import com.csp.myprojec.modular.comment.CommentWriteFragment;
import com.tamic.jswebview.view.ProgressBarWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by CSP on 2016/12/25.
 */

public class WebActivity extends BaseSwipeBackActivity {

    @BindView(R.id.web)
    ProgressBarWebView web;
    Unbinder unbinder;
    private int nid;
    private String context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        unbinder = ButterKnife.bind(this);
        init();
    }

    public void init() {
        context = getIntent().getStringExtra("content");
        nid = getIntent().getIntExtra("nid", 0);
        web.getWebView().setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web.getWebView().setVerticalScrollBarEnabled(true);
        WebSettings webSettings = web.getWebView().getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(30);
//        webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);

        String css = "<style type=\"text/css\"> img {" +
                "width:100%;" +
                "height:auto;" +
                "}" +
                "body {" +
                "margin-right:15px;" +
                "margin-left:15px;" +
                "margin-top:15px;" +
                "font-size:45px;" +
                "}" +
                "span[style] {" +
                "margin-right:15px;" +
                "margin-left:15px;" +
                "margin-top:15px;" +
                "font-size:30px !important;" +
                "}" +
                "</style>";
        String html = "<html><header>" + css + "</header><body>" + context + "</body></html>";
        web.getWebView().loadData(html, "text/html; charset=UTF-8", null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @OnClick({R.id.comment, R.id.comment_show})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment:
                CommentWriteFragment commentWriteFragment = CommentWriteFragment.newInstance(nid, 0);
                commentWriteFragment.show(getSupportFragmentManager(), "user_comment");
                break;
            case R.id.comment_show:
                Intent intent = new Intent(WebActivity.this, CommentActivity.class);
                intent.putExtra("nid", nid);
                intent.putExtra("parentid", 0);
                startActivity(intent);
                break;
        }
    }
}
