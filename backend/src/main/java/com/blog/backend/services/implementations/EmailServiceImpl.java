package com.blog.backend.services.implementations;

import com.blog.backend.dto.BlogDTO;
import com.blog.backend.services.BlogService;
import com.blog.backend.services.EmailService;
import com.blog.backend.services.SubscriberService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

    @Value("&{EMAIL_USERNAME}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private BlogService blogService;

    private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void sendEmailTest(String to, String subject, String message) {
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setTo(to);
//        simpleMailMessage.setFrom(from);
//        simpleMailMessage.setSubject(subject);
//        simpleMailMessage.setText(message);
//        mailSender.send(simpleMailMessage);
//        logger.info("Email has been sent.");

        MimeMessage mailMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage,true, "UTF-8");
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(message);
            mailSender.send(mailMessage);
            logger.info("Mail has been sent.");

        }catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void sendEmail(String[] to, String subject, String htmlContent) {
        MimeMessage mailMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage,true, "UTF-8");
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(mailMessage);
            logger.info("Mail has been sent.");

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    @Scheduled(cron = "0 0 9 * * ?")  // Runs every day at 9 AM
    public void sendEmailDaily() {
        try {
            String[] to = subscriberService.getAllSubscribers();
//            String[] to = {"kravi26cse@gmail.com", "ravikr12082018@gmail.com"};
            BlogDTO blog = blogService.getRandomBlog();
            String html = """
                    <H1>%s</H1>
                    <p>%s</p>
                    """.formatted(blog.getTitle(), blog.getContent());
            sendEmail(to, blog.getTitle(), html);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
