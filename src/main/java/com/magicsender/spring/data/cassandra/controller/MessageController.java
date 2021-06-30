package com.magicsender.spring.data.cassandra.controller;

import com.datastax.driver.core.utils.UUIDs;
import com.magicsender.spring.data.cassandra.config.EmailCfg;
import com.magicsender.spring.data.cassandra.model.Message;
import com.magicsender.spring.data.cassandra.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    EmailCfg emailCfg;

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return new ResponseEntity<>(messageRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/messages/{email}")
    public ResponseEntity<List<Message>> getAllMessagesByEmail(@PathVariable("email") String email) {
        return new ResponseEntity<>(messageRepository.findAllByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/message")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) throws InterruptedException {
        Message _message = messageRepository.save(new Message(UUIDs.timeBased(), message.getEmail(), message.getTitle(), message.getContent(), message.getMagicnumber()));
        return new ResponseEntity<>(_message, HttpStatus.CREATED);
    }

    @DeleteMapping("/send")
    public ResponseEntity<HttpStatus> sendAllMassagesWithMagicNumber(@RequestParam("magicnumber") Integer magicnumber) {
        List<Message> messagesWithMagicNum = messageRepository.findByMagicnumber(magicnumber);
        // Create a mail sender
        JavaMailSenderImpl mailSender = getJavaMailSender();
        for (Message message : messagesWithMagicNum) {
            // Create an email instance
            SimpleMailMessage mailMessage = getSimpleMailMessage(message);
            // Send mail
            mailSender.send(mailMessage);
            messageRepository.deleteById(message.getId());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private JavaMailSenderImpl getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailCfg.getHost());
        mailSender.setPort(this.emailCfg.getPort());
        mailSender.setUsername(this.emailCfg.getUsername());
        mailSender.setPassword(this.emailCfg.getPassword());
        return mailSender;
    }

    private SimpleMailMessage getSimpleMailMessage(Message message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("Adam");
        mailMessage.setTo(message.getEmail());
        mailMessage.setSubject(message.getTitle());
        mailMessage.setText(message.getContent());
        return mailMessage;
    }
}


