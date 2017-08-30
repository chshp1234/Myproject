package com.csp.myprojec.custom;

/**
 * Created by CSP on 2017/3/13.
 */

public interface DataCall<T> {
    void onSuccess(T data);
    void onFailure();
}
