package com.example.demo.member.service.exception;

public class MemberNotEmailAuthException extends RuntimeException{

	public MemberNotEmailAuthException(String error) {
		super(error);
	}
}
