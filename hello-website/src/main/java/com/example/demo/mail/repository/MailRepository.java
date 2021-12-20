package com.example.demo.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.mail.entity.Mail;

public interface MailRepository extends JpaRepository<Mail, String>{

}
