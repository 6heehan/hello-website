package com.example.demo.member.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.member.model.MemberInput;
import com.example.demo.member.model.ResetPasswordInput;

public interface MemberService extends UserDetailsService{

	boolean register(MemberInput parameter);
	
	/**
	 * uuid에 해당하는 계정을 활성화
	 */
	boolean emailAuth(String uuid);

	/**
	 * 입력한 이메일로 비밀번호 초기화 정보를 전송
	 */
	boolean sendResetPassword(ResetPasswordInput parameter);

	/**
	 * 입력받은 uuid에 대해서 password를 초기화함
	 */
	boolean resetPassword(String id, String password);

	/**
	 * 입력받은 uuid 값이 유효한지 확인
	 */
	boolean checkResetPassword(String uuid);
}
