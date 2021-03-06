package com.example.demo.member.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.example.demo.components.MailComponents;
import com.example.demo.member.entity.Member;
import com.example.demo.member.model.MemberInput;
import com.example.demo.member.model.ResetPasswordInput;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.service.MemberService;
import com.example.demo.member.service.exception.MemberNotEmailAuthException;

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
		String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());
		
		Member member = Member.builder()
				.userId(parameter.getUserId())
				.userName(parameter.getUserName())
				.phone(parameter.getPhone())
				.password(encPassword)
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
		String subject = "demo ????????? ????????? ??????????????????. ";
		String text ="<p>demo ????????? ????????? ??????????????????.</p><p>?????? ????????? ??????????????? ????????? ???????????????.</p>" 
				+ "<div><a href='http://localhost:8080/member/email-auth?id="+uuid+"'>?????? ??????</a></div>";
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
		
		if(member.isEmailAuthYn()) {
			return false;
		}
		
		member.setEmailAuthYn(true);
		member.setEmailAuthDt(LocalDateTime.now());
		memberRepository.save(member);
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> optionalMember = memberRepository.findById(username);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
		}
		
		Member member = optionalMember.get();
		
		if(!member.isEmailAuthYn()) {
			throw new MemberNotEmailAuthException("????????? ????????? ????????? ???????????? ????????????.");
		}
		
		List<GrantedAuthority> grantedAuthorites = new ArrayList<>();
		grantedAuthorites.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		if(member.isAdminYn()) {
			grantedAuthorites.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		return new User(member.getUserId(), member.getPassword(),grantedAuthorites);
	}

	@Override
	public boolean sendResetPassword(ResetPasswordInput parameter) {
		
		Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName());
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
		}
		
		Member member = optionalMember.get();
		
		String uuid = UUID.randomUUID().toString();
		member.setResetPasswordKey(uuid);
		member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
		memberRepository.save(member);
		
		
		String email = parameter.getUserId();
		String subject = "[demo] ???????????? ????????? ?????? ?????????. ";
		String text ="<p>demo ????????? ???????????? ????????? ???????????????.</p><p>?????? ????????? ??????????????? ??????????????? ????????? ????????????</p>" 
				+ "<div><a href='http://localhost:8080/member/reset/password?id="+uuid+"'>???????????? ????????? ??????</a></div>";
		mailComponents.sendMail(email, subject, text);
		
		return true;
	}

	@Override
	public boolean resetPassword(String uuid, String password) {
		Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
		}
		
		Member member = optionalMember.get();
		if( member.getResetPasswordLimitDt() == null) {
			throw new RuntimeException("????????? ????????? ????????????.");
		}
		if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("????????? ????????? ????????????.");
		}
		String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		member.setPassword(encPassword);
		member.setResetPasswordKey("");
		member.setResetPasswordLimitDt(null);
		memberRepository.save(member);
		return true;
	}

	@Override
	public boolean checkResetPassword(String uuid) {
		Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
		if(!optionalMember.isPresent()) {
			return false;
		}
		
		Member member = optionalMember.get();
		if( member.getResetPasswordLimitDt() == null) {
			throw new RuntimeException("????????? ????????? ????????????.");
		}
		if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("????????? ????????? ????????????.");
		}
		
		return true;
	}

}
