package com.blog.backend.services;

import org.springframework.beans.factory.annotation.Value;

public interface EmailService {

    void sendEmailTest(String to, String subject, String message);

    void sendEmail(String[] to, String subject, String htmlContent);

    void sendEmailDaily();
}
