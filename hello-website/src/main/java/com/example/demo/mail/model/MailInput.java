package com.example.demo.mail.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MailInput {

	private String mailTemplateKey;
	private String mailSubject;
	private String mailContent;
	private String remark;
}
