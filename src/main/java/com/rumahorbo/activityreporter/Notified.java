package com.rumahorbo.activityreporter;

public interface Notified {
    void notifyLike(User user, User userLiked);
    void notifyUpload(User user);
}
