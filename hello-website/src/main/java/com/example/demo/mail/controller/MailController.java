package com.example.demo.mail.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.mail.entity.Mail;
import com.example.demo.mail.model.MailInput;
import com.example.demo.mail.repository.MailRepository;
import com.example.demo.mail.service.MailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MailController {

	private final MailService mailService;
	private final MailRepository mailRepository;
	
	@GetMapping("/admin/mail/template")
	public String emailTemplate(Model model) {
		List<Mail> mails = mailService.list();
		model.addAttribute("list", mails);
		
		return "admin/mail/template";
	}
	
	@GetMapping("/admin/mail/create")
	public String mailTemplateCreate(Model model) {
		
		return "admin/mail/create";
	}
	
	@PostMapping("/admin/mail/create")
	public String mailTemplateCreateSubmit(Model model, MailInput parameter) {
		//System.out.println("post submit");
		//System.out.println(parameter.toString());
		
		boolean result = mailService.register(parameter);
		model.addAttribute("result",result);
		
		return "admin/mail/create_complete";
	}
	
	@GetMapping("/admin/mail/edit")
	public String mailTemplateEdit(Model model, HttpServletRequest request) {
		String mailTemplateKey = request.getParameter("id");
		//System.out.println(mailTemplateKey);
		
		Optional<Mail> mailOptional= mailRepository.findById(mailTemplateKey);
		Mail mail = mailOptional.get();
		model.addAttribute("mail",mail);
		return "admin/mail/edit";
	}
	
	@PostMapping("/admin/mail/edit")
	public String mailTemplateEditSubmit(Model model, MailInput parameter) {
		boolean result = mailService.update(parameter);
		model.addAttribute("result", result);
		System.out.println(result);
		return "admin/mail/edit_complete";
	}
}
