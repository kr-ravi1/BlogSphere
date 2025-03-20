package com.blog.backend.services;

import com.blog.backend.models.User;

public interface SubscriberService {

    String[] getAllSubscribers();

    void addSubscriber(User user);

    void removeSubscriber(User user);

    User getSubscriberByEmail(String email);

}
