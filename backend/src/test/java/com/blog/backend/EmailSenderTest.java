package com.blog.backend;

import com.blog.backend.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailSenderTest {

    @Autowired
    private EmailService emailService;

    @Test
    void emailSendTest() {
        System.out.println("Sending Email...");
//        emailService.sendEmailTest("kravi26cse@gmail.com", "Testing", "This is for testing purpose.");
//        emailService.sendEmailDaily();
    }
}
