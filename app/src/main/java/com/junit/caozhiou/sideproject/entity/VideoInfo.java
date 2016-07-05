package com.junit.caozhiou.sideproject.entity;

/**
 * 作者：lubote on 2016/7/1 16:31
 * 邮箱：nj.caozhiou@dhjt.com
 */

public class VideoInfo {
    private String url;
    private String videoName;
    private int videoImage;

    public VideoInfo(String url, String name, int path) {
        this.videoName = name;
        this.videoImage = path;
        this.url = url;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(int videoImage) {
        this.videoImage = videoImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}