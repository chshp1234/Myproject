package com.csp.myprojec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;

import com.csp.myprojec.R;
import com.csp.myprojec.base.BaseActivity;
import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.bean.UserBean;
import com.csp.myprojec.custom.RetrofitFactory;
import com.csp.myprojec.custom.RxSchedulersHelper;
import com.github.lazylibrary.util.PreferencesUtils;
import com.github.lazylibrary.util.StringUtils;
import com.github.lazylibrary.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by CSP on 2017/3/28.
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.username)
    TextInputLayout username;
    @BindView(R.id.password)
    TextInputLayout password;
    @BindView(R.id.password_confirm)
    TextInputLayout passwordConfirm;
    @BindView(R.id.submit)
    FButton submit;

    CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mCompositeSubscription = new CompositeSubscription();
    }

    public boolean validate() {
        if (StringUtils.isBlank(username.getEditText().getText().toString())
                || StringUtils.isBlank(password.getEditText().getText().toString())) {
            ToastUtils.showToast(RegisterActivity.this, "用户名或密码不能为空");
            return false;
        } else if (!(password.getEditText().getText().toString().equals(passwordConfirm.getEditText().getText().toString()))) {
            passwordConfirm.setErrorEnabled(true);
            passwordConfirm.setError("两次输入的密码不相同");
            return false;
        } else {
            passwordConfirm.setErrorEnabled(false);
            return true;
        }
    }

    @OnClick(R.id.submit)
    public void onClick() {
        if (validate()) {
            mCompositeSubscription.add(RetrofitFactory.getInstance().getUser("userregister",
                    StringUtils.nullStrToEmpty(username.getEditText().getText().toString()),
                    password.getEditText().getText().toString())
                    .compose(RxSchedulersHelper.io_main())
                    .subscribe(new Subscriber<UserBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showToast(RegisterActivity.this, "注册失败，请检查网络");
                        }

                        @Override
                        public void onNext(UserBean userBean) {
                            if (userBean.getCode() == 200) {
                                username.setErrorEnabled(false);
                                PreferencesUtils.putString(MyApplication.getContext(), "token",
                                        userBean.getData().get(0).getToken());
                                PreferencesUtils.putString(MyApplication.getContext(), "nickname",
                                        userBean.getData().get(0).getNickname());
                                ToastUtils.showToast(RegisterActivity.this, "注册成功！");
                                onBackPressed();

                            } else if (userBean.getCode() == 404) {
                                username.setErrorEnabled(true);
                                username.setError("用户名已存在！");
                            }
                        }
                    }));

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
