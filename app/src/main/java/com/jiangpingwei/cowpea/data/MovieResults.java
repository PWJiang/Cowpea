package com.jiangpingwei.cowpea.data;

/**
 * Created by jiangpingwei on 2017/3/14.
 */

public class MovieResults {
    private int id;
    private int userId;
    private String url;
    private String coverPic;
    private String screenName;
    private String caption;
    private String avatar;
    private int playsCount;
    private int commentsCount;
    private int likesCount;
    private String createdAt;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }
    public String getCoverPic() {
        return coverPic;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
    public String getScreenName() {
        return screenName;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
    public String getCaption() {
        return caption;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setPlaysCount(int playsCount) {
        this.playsCount = playsCount;
    }
    public int getPlaysCount() {
        return playsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
    public int getCommentsCount() {
        return commentsCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
    public int getLikesCount() {
        return likesCount;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getCreatedAt() {
        return createdAt;
    }
}
