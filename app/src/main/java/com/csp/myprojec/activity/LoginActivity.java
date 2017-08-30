package com.csp.myprojec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.csp.myprojec.R;
import com.csp.myprojec.base.BaseActivity;
import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.custom.RetrofitFactory;
import com.csp.myprojec.custom.RxSchedulersHelper;
import com.github.lazylibrary.util.PreferencesUtils;
import com.github.lazylibrary.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by CSP on 2017/3/23.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.username)
    AutoCompleteTextView username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.submit)
    FButton submit;
    @BindView(R.id.register)
    TextView register;

    CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        String longhistory = PreferencesUtils.getString(MyApplication.getContext(), "uNameHistory", "");
        String[] hisArrays = longhistory.split(",");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, hisArrays);
        username.setAdapter(adapter);
        mCompositeSubscription = new CompositeSubscription();
    }


    public void saveUname() {
        String text = username.getText().toString();
        String longhistory = PreferencesUtils.getString(MyApplication.getContext(), "uNameHistory", "");
        if (!longhistory.contains(text + ",")) {
            StringBuilder sb = new StringBuilder(longhistory);
            sb.append(text + ",");
            PreferencesUtils.putString(MyApplication.getContext(), "uNameHistory", sb.toString());
        }
    }

    @OnClick({R.id.submit, R.id.register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                Subscription subscription = RetrofitFactory.getInstance()
                        .getUser("userlogin", username.getText().toString(), password.getText().toString())
                        .compose(RxSchedulersHelper.io_main())
                        .subscribe(userBean -> {
                            if (userBean.getCode() == 200) {
                                saveUname();
                                PreferencesUtils.putString(MyApplication.getContext(), "token",
                                        userBean.getData().get(0).getToken());
                                PreferencesUtils.putString(MyApplication.getContext(), "nickname",
                                        userBean.getData().get(0).getNickname());
                                ToastUtils.showToast(LoginActivity.this, "登录成功！");
                                onBackPressed();
                            } else {
                                ToastUtils.showToast(LoginActivity.this, "登录失败，请验证账号或密码");
                            }
                        });
                mCompositeSubscription.add(subscription);

                break;
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }
}
