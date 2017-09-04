package com.csp.myprojec.modular.comment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.telephony.SubscriptionManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.csp.myprojec.R;
import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.bean.StateBean;
import com.csp.myprojec.custom.RetrofitFactory;
import com.csp.myprojec.custom.RxSchedulersHelper;
import com.csp.myprojec.custom.onItemChangeListener;
import com.csp.myprojec.event.CommentEvent;
import com.csp.myprojec.event.DataChangeEvent;
import com.csp.myprojec.modular.news.NewsListFragment;
import com.github.lazylibrary.util.DateUtil;
import com.github.lazylibrary.util.StringUtils;
import com.github.lazylibrary.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

import static com.github.lazylibrary.util.DateUtil.FORMAT_YMDHM;

/**
 * Created by CSP on 2016/12/27.
 */

public class CommentWriteFragment extends AppCompatDialogFragment {

    @BindView(R.id.comment_content)
    EditText commentContent;
    @BindView(R.id.comment_send)
    TextView commentSend;
    Unbinder unbinder;
    Window window;
    WindowManager.LayoutParams params;
    //
    CompositeSubscription mCompositeSubscription;
    private int nid;
    private int parentid;
    @Override
    public void onStart() {
        super.onStart();
        window = getDialog().getWindow();
        params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    }

    //显示关闭输入法
/*    protected void showSoftInput(){
                InputMethodManager imm;
        imm = (InputMethodManager) MyApplication.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean boolen=imm.showSoftInput(commentContent, InputMethodManager.SHOW_FORCED);
        Log.i("show",boolen+"");
        imm.toggleSoftInput(InputMethodManager.RESULT_SHOWN,
                InputMethodManager.RESULT_HIDDEN);
    }

        protected void hideSoftInput() {
        InputMethodManager manager = (InputMethodManager) MyApplication.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean boolen =manager.hideSoftInputFromWindow(window.getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        Log.i("hide",boolen+"");
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nid = getArguments().getInt("nid");
        parentid = getArguments().getInt("parentid");
        Log.i("nid", nid + "");
        Log.i("parentid", parentid + "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.comment_write_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        mCompositeSubscription = new CompositeSubscription();
//        EventBus.getDefault().register(this);
        return view;

    }

    public static CommentWriteFragment newInstance(int nid, int parentid) {
        Bundle args = new Bundle();
        CommentWriteFragment fragment = new CommentWriteFragment();
        args.putInt("nid", nid);
        args.putInt("parentid", parentid);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("CommentWriteFragment", "onDestroyView");
        mCompositeSubscription.unsubscribe();
        //        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("CommentWriteFragment", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("CommentWriteFragment", "onDetach");
    }

    @OnClick(R.id.comment_send)
    public void onClick() {
        String content = commentContent.getText().toString();
        Map<String, String> options = new HashMap<>();
        if (!StringUtils.isBlank(content)) {
            options.put("action", "docomment");
            options.put("nid", nid + "");
            options.put("token", MyApplication.getToken());
            options.put("content", StringUtils.replaceBlanktihuan(content));
            options.put("parentid", parentid + "");
            options.put("commenttime", DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHM));
            mCompositeSubscription.add(RetrofitFactory.getInstance().doComment(options)
                    .compose(RxSchedulersHelper.io_main())
                    .subscribe(new Subscriber<StateBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("e",e.toString());
                            e.printStackTrace();
                            ToastUtils.showToast(MyApplication.getContext(),"评论失败,请检查网络！");
                        }

                        @Override
                        public void onNext(StateBean stateBean) {
                            if (stateBean.getCode()==200){
                                ToastUtils.showToast(MyApplication.getContext(),"评论成功");
//                                if (getTag().equals("news_comment")){
//                                    Log.i("tag",getTag());
                                    EventBus.getDefault().post(new DataChangeEvent());
//                                }
                                dismiss();
                            }else {
                                ToastUtils.showToast(MyApplication.getContext(),"评论失败");
                            }
                        }
                    }));
        }else {
            ToastUtils.showToast(MyApplication.getContext(),"请输入评论内容");
        }
    }

//    @Subscribe(threadMode =ThreadMode.MAIN)
//    public void reciveMessge(CommentEvent commentEvent){
//        Log.i("nid","新闻ID："+commentEvent.getNid()+"");
//    }
}
