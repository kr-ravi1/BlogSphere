package com.blog.backend.services.implementations;

import com.blog.backend.models.User;
import com.blog.backend.services.SubscriberService;
import com.blog.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriberServiceImpl implements SubscriberService {

    @Autowired
    private UserService userService;

    @Override
    public String[] getAllSubscribers() {
        return userService.getAllSubscribers();
    }

    @Override
    public void addSubscriber(User user) {
        user.setSubscribed(true);
        userService.save(user);
    }

    @Override
    public void removeSubscriber(User user) {
        user.setSubscribed(false);
        userService.save(user);
    }

    @Override
    public User getSubscriberByEmail(String email) {
        return userService.getUserByEmail(email).orElseThrow();
    }

}
