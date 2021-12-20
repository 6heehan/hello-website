package com.example.demo.mail.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.mail.entity.Mail;
import com.example.demo.mail.model.MailInput;
import com.example.demo.mail.repository.MailRepository;
import com.example.demo.mail.service.MailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService{

	private final MailRepository mailRepository;
	
	@Override
	public List<Mail> list() {
		return mailRepository.findAll();
	}
	
	@Override
	public boolean register(MailInput parameter) {
		Optional<Mail> optionalMail = mailRepository.findById(parameter.getMailTemplateKey());
		if(optionalMail.isPresent()) {
			return false;
		}
		
		Mail mail = Mail.builder()
						.mailTemplateKey(parameter.getMailTemplateKey())
						.mailSubject(parameter.getMailSubject())
						.mailContent(parameter.getMailContent())
						.build();
		
		mailRepository.save(mail);
		return true;
	}
	
	@Override
	public boolean update(MailInput parameter) {
		Optional<Mail> optionalMail = mailRepository.findById(parameter.getMailTemplateKey());
		if(!optionalMail.isPresent()) {
			return false;
		}
		
		Mail mail = Mail.builder()
				.mailTemplateKey(parameter.getMailTemplateKey())
				.mailSubject(parameter.getMailSubject())
				.mailContent(parameter.getMailContent())
				.build();
		
		mailRepository.save(mail);
		return true;
	}

}
