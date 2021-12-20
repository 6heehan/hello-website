package com.example.demo.mail.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Mail {

	@Id
	private String mailTemplateKey;
	
	private String mailSubject;
	private String mailContent;
	private String remark;
}
