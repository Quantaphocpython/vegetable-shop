package org.ecommerce.spring.boot.vegetable.project.utility;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.ecommerce.spring.boot.vegetable.project.dto.LMessageDTO;
import org.ecommerce.spring.boot.vegetable.project.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Component
public class SendMail {

    @Autowired
    private  JavaMailSender mailSender;

    public void sendPasswordResetVerificationEmail(User user,String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Request Verification";
        String senderName = "Users Verification Service";
        String mailContent = "<p> Hi, "+ user.getFirstName()+ ", </p>"+
                "<p><b>You recently requested to reset your password,</b>"+"" +
                "Please, follow the link below to complete the action.</p>"+
                "<a href=\"" +url+ "\">Reset password</a>"+
                "<p> Users Registration Portal Service";
        emailMessage(subject, senderName, mailContent, user);
    }

    public void sendVerificationEmail(User user,String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Verify your account";
        String senderName = "Users Verification Service";
        String mailContent = "<p> Hi, "+ user.getFirstName()+ ", </p>"+
                "<p><b>CLick follow link to verify your account,</b>"+"" +
                "<a href=\"" +url+ "\">Verify account</a>"+
                "<p> Users Registration Portal Service";
        emailMessage(subject, senderName, mailContent, user);
    }

    public void sendLeaveMessage(LMessageDTO lMessage) throws MessagingException, UnsupportedEncodingException {
        String subject = "Leave Message";
        String senderName = "Message from user " + lMessage.getEmail();
        String mailContent = "<p>" + lMessage.getDescription() + "</p>";
        emailMessage(subject, senderName, mailContent, User.builder()
                .email("quan02042004@gmail.com")
                .build());
    }

    private void emailMessage(String subject, String senderName,
                                     String mailContent, User theUser)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("hoangquanph0204@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
