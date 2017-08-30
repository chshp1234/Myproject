package com.csp.myprojec.event;

/**
 * Created by CSP on 2017/4/3.
 */

public class CommentEvent {
    public int nid;
    public int parentid;

    public int getNid() {
        return nid;
    }

    public int getParentid() {
        return parentid;
    }

    public CommentEvent(int nid, int parentid) {
        this.nid = nid;
        this.parentid = parentid;

    }
}
