package kg.attractor.jobsearch.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendMail(String toEmail, String link) throws MessagingException, UnsupportedEncodingException;
}
