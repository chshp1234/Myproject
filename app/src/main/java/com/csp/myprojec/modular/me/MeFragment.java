package com.csp.myprojec.modular.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csp.myprojec.AboutUsActivity;
import com.csp.myprojec.R;
import com.csp.myprojec.activity.LoginActivity;
import com.csp.myprojec.activity.MainActivity;
import com.csp.myprojec.activity.SetActivity;
import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.modular.collection.CollectionActivity;
import com.github.lazylibrary.util.PreferencesUtils;
import com.github.lazylibrary.util.StringUtils;
import com.github.lazylibrary.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by CSP on 2017/3/12.
 */

public class MeFragment extends Fragment {

    @BindView(R.id.userhead)
    ImageView userhead;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.collection)
    LinearLayout collection;
    @BindView(R.id.history)
    LinearLayout history;
    @BindView(R.id.mycomment)
    LinearLayout mycomment;
    @BindView(R.id.personal)
    LinearLayout personal;
    @BindView(R.id.setup)
    LinearLayout setup;
    @BindView(R.id.feedback)
    LinearLayout feedback;
    @BindView(R.id.about)
    LinearLayout about;
    @BindView(R.id.login)
    Button login;
    Unbinder unbinder;

    private String mNickname;
    private String token;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.me_fragment, null);
        unbinder = ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
        mNickname = PreferencesUtils.getString(MyApplication.getContext(), "nickname", "");
        token = MyApplication.getToken();
        if (StringUtils.isBlank(token)) {
            nickname.setText("");
            login.setText("登录/注册");
        } else {
            nickname.setText(mNickname);
            login.setText("退出登录");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.collection, R.id.history,
            R.id.mycomment, R.id.personal, R.id.setup,
            R.id.feedback, R.id.about, R.id.login})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.collection:
                intent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.history:
                ToastUtils.showToast(MyApplication.getContext(), "后续开发...！");
                break;
            case R.id.mycomment:
                ToastUtils.showToast(MyApplication.getContext(), "后续开发...！");
                break;
            case R.id.personal:
                ToastUtils.showToast(MyApplication.getContext(), "个人信息");
                break;
            case R.id.setup:
                intent=new Intent(getActivity(), SetActivity.class);
                startActivity(intent);
                break;
            case R.id.feedback:
                ToastUtils.showToast(MyApplication.getContext(), "反馈建议");
                break;
            case R.id.about:
                intent=new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.login:
                if (!StringUtils.isBlank(token)) {
                    ToastUtils.showToast(getActivity(), "账号已退出");
                    intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    PreferencesUtils.putString(MyApplication.getContext(), "token", "");
                    PreferencesUtils.putString(MyApplication.getContext(), "nickname", "");
                    getActivity().overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                break;
        }
    }
}
