package com.csp.myprojec;

import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.custom.RetrofitFactory;
import com.csp.myprojec.custom.RxSchedulersHelper;

import org.junit.Test;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testApi() {

        RetrofitFactory.getInstance().getNews("getcollection", 1, null, MyApplication.getToken())
                .subscribe(newsBean -> assertEquals(200, newsBean.getCode()));

    }
}