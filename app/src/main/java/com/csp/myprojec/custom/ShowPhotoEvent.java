package com.csp.myprojec.custom;

/**
 * Created by CSP on 2017/3/22.
 */

public class ShowPhotoEvent {
    private String thumb;

    public String getThumb() {
        return thumb;
    }

    public ShowPhotoEvent(String thumb) {
        this.thumb = thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
