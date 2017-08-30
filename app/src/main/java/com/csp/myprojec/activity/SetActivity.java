package com.csp.myprojec.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;

import com.csp.myprojec.R;
import com.csp.myprojec.base.BaseActivity;
import com.csp.myprojec.base.MyApplication;
import com.github.lazylibrary.util.PreferencesUtils;
import com.github.lazylibrary.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by CSP on 2017/4/21.
 */

public class SetActivity extends BaseActivity {
    @BindView(R.id.local)
    AppCompatRadioButton local;
    @BindView(R.id.server)
    AppCompatRadioButton server;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.set_confirm)
    public void onClick() {
        if (local.isChecked()){//192.168.199.171
            PreferencesUtils.putString(MyApplication.getContext(),"ip_config","http://192.168.43.206/csp/");
            ToastUtils.showToast(MyApplication.getContext(),"切换IP成功！");
            finish();
        }else if (server.isChecked()){
            PreferencesUtils.putString(MyApplication.getContext(),"ip_config","http://123.57.13.94/csp/");
            ToastUtils.showToast(MyApplication.getContext(),"切换IP成功！");
            finish();
        }else {
            ToastUtils.showToast(MyApplication.getContext(),"请选择IP！");
        }
    }
}
