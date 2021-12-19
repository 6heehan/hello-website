package com.example.demo.member.service;

import com.example.demo.member.model.MemberInput;

public interface MemberService {

	boolean register(MemberInput parameter);
	
	/**
	 * uuid에 해당하는 계정을 활성화
	 * @param uuid
	 * @return
	 */
	boolean emailAuth(String uuid);
}
