package com.rumahorbo.activityreporter;

import java.util.ArrayList;
import java.util.List;

public class User implements Followable, Likeable, Uploadable, Notified {
    private final String name;
    private final List<User> followers;
    private final List<String> notifications;

    public User(String name) {
        this.name = name;
        this.followers = new ArrayList<>();
        this.notifications = new ArrayList<>();
        followers.add(this);
    }

    @Override
    public void follows(User user) {
        user.followers.add(this);
    }

    @Override
    public void likePhoto(User userLiked) {
        notifyFollowers(userLiked);
        notifyUserLiked(userLiked);
    }

    private void notifyUserLiked(User userLiked) {
        userLiked.notifyLike(this, userLiked);
    }

    private void notifyFollowers(User userLiked) {
        for (Notified follower : followers) {
            follower.notifyLike(this, userLiked);
        }
    }

    @Override
    public void notifyLike(User actionUser, User userLiked) {
        if (this.equals(userLiked) && this.equals(actionUser)) {
            return;
        }
        String actionName = getSubjectNotification(actionUser);
        String objectName = getObjectNotification(userLiked);
        String notificationLike = actionName + " liked " + objectName + " photo";
        if (!this.notifications.contains(notificationLike)) {
            this.notifications.add(notificationLike);
        }
    }

    @Override
    public void notifyUpload(User user) {
        String name = getSubjectNotification(user);
        String notificationUpload = name + " uploaded photo";
        this.notifications.add(notificationUpload);
    }

    private String getSubjectNotification(User user) {
        if (user.name.equals(this.name)) {
            return "You";
        }
        return user.name;
    }

    private String getObjectNotification(User user) {
        if (user.name.equals(this.name)) {
            return "your";
        }
        return user.name + "'s";
    }

    @Override
    public void uploadPhoto() {
        notifyFollowers();
    }

    private void notifyFollowers() {
        for (Notified notifiable : followers) {
            notifiable.notifyUpload(this);
        }
    }

    public String activityLogString() {
        StringBuilder stringNotifications = new StringBuilder();
        for (String notification : notifications) {
            stringNotifications.append(notification).append("\n");
        }
        return stringNotifications.toString();
    }

    public List<String> activityLog() {
        return notifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return name.equals(user.name);
    }

    @Override
    public boolean isFollow(User user) {
        return user.followers.contains(this);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
