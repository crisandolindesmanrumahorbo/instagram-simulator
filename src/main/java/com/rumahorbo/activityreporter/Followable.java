package com.rumahorbo.activityreporter;

public interface Followable {
    void follows(User user);
    boolean isFollow(User user);
}
