package com.example.demo.member.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.member.entity.Member;
import com.example.demo.member.model.MemberInput;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberService memberService;
	
	@GetMapping("/member/register")
	public String register() {
		System.out.println("request Get !!!!!!!!!!!!!!");
		return "member/register";
	}
	
	@PostMapping("/member/register")
	public String registerSubmit(Model model, HttpServletResponse response, HttpServletRequest request,
			MemberInput parameter) {
		boolean result = memberService.register(parameter);
		model.addAttribute("result", result);
		return "member/register_complete";
	}
	
	@GetMapping("/member/email-auth")
	public String emailAuth(Model model, HttpServletRequest request) {
		String uuid = request.getParameter("id");
		System.out.println(uuid);
		
		boolean result = memberService.emailAuth(uuid);
		model.addAttribute("result", result);
		return "member/email-auth";
	}
}
