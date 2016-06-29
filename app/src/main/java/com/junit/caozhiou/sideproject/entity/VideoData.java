package com.junit.caozhiou.sideproject.entity;

/**
 * 作者：lubote on 2016/6/27 16:40
 * 邮箱：nj.caozhiou@dhjt.com
 */

public class VideoData extends BaseEntity {

    private String videoTitle;//视频标题
    private String videoTime;//视频时长
    private String videoPath;//视频链接
    private boolean isCanPlay;//是否可以播放

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public boolean isCanPlay() {
        return isCanPlay;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public void setCanPlay(boolean canPlay) {
        isCanPlay = canPlay;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }
}
