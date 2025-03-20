package com.blog.backend.controllers;

import com.blog.backend.models.User;
import com.blog.backend.responses.Response;
import com.blog.backend.services.SubscriberService;
import com.blog.backend.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriber")
@CrossOrigin
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(SubscriberController.class);

    @PostMapping("/add/{email}")
    public ResponseEntity<Response> addSubscriber(@PathVariable String email) {
        Response response = new Response();
        logger.info("Fetched Email: " + email);
        try{
            User user = subscriberService.getSubscriberByEmail(email);
            if(user.isSubscribed()) {
                response.setMessage("Already Subscribed");
                response.setSubscribed(user.isSubscribed());
            }
            else {
                subscriberService.addSubscriber(user);
                response.setMessage("Subscribed Successfully");
                response.setSubscribed(!user.isSubscribed());
            }
        }
        catch (Exception e) {
            response.setMessage("Some thing went wrong" + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/remove/{email}")
    public ResponseEntity<Response> removeSubscriber(@PathVariable String email) {
        Response response = new Response();
        try{
            User user = subscriberService.getSubscriberByEmail(email);
            if(user.isSubscribed()) {
                subscriberService.removeSubscriber(user);
                response.setMessage("Unsubscribed Successfully");
                response.setSubscribed(!user.isSubscribed());
            }
            else {
                response.setMessage("Already Unsubscribed");
                response.setSubscribed(user.isSubscribed());
            }
        }
        catch (Exception e) {
            response.setMessage("Some thing went wrong");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/status/{email}")
    public ResponseEntity<Response> getSubscriberStatus(@PathVariable String email) {
        Response response = new Response();
        try{
            User user = subscriberService.getSubscriberByEmail(email);
            response.setSubscribed(user.isSubscribed());
        }
        catch (Exception e) {
            response.setMessage("Some thing went wrong");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
