package com.example.demo.member.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.components.MailComponents;
import com.example.demo.member.entity.Member;
import com.example.demo.member.model.MemberInput;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

	private final MemberRepository memberRepository;
	private final MailComponents mailComponents;
	@Override
	public boolean register(MemberInput parameter) {
		
		Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
		if(optionalMember.isPresent()) {
			return false;
		}
		String uuid = UUID.randomUUID().toString();
		
		Member member = Member.builder()
				.userId(parameter.getUserId())
				.userName(parameter.getUserName())
				.phone(parameter.getPhone())
				.password(parameter.getPassword())
				.regDt(LocalDateTime.now())
				.emailAuthYn(false)
				.emailAuthKey(uuid)
				.build();
		/*
		Member member = new Member();
		member.setUserId(parameter.getUserId());
		member.setUserName(parameter.getUserName());
		member.setPhone(parameter.getPhone());
		member.setPassword(parameter.getPassword());
		member.setRegDt(LocalDateTime.now());
		
		member.setEmailAuthYn(false);
		member.setEmailAuthKey(uuid);
		*/
		memberRepository.save(member);
		
		String email = parameter.getUserId();
		String subject = "demo 사이트 가입을 축하드립니다. ";
		String text ="<p>demo 사이트 가입을 축하드립니다.</p><p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>" 
				+ "<div><a href='http://localhost:8080/member/email-auth?id="+uuid+"'>가입 완료</a></div>";
		mailComponents.sendMail(email, subject, text);
		return true;
	}
	
	@Override
	public boolean emailAuth(String uuid) {
		Optional<Member> optional = memberRepository.findByEmailAuthKey(uuid);
		if(!optional.isPresent()) {
			return false;
		}
		
		Member member = optional.get();
		member.setEmailAuthYn(true);
		member.setEmailAuthDt(LocalDateTime.now());
		memberRepository.save(member);
		return true;
	}

}
