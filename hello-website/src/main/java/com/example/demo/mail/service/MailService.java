package com.example.demo.mail.service;

import java.util.List;

import com.example.demo.mail.entity.Mail;
import com.example.demo.mail.model.MailInput;

public interface MailService {

	/**
	 * 메일 템플릿 관련 목록 리턴
	 */
	List<Mail> list();
	
	/**
	 * 템플릿 등록을 위한 메소드
	 */
	boolean register(MailInput parameter);
	
	boolean update(MailInput parameter);
}
