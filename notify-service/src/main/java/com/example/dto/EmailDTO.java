package com.example.dto;

public class EmailDTO {
	private String email;//邮箱地址
	private String subject;//邮箱主题
	private String body;//邮箱主体内容
	public EmailDTO(String email, String subject, String body) {
		super();
		this.email = email;
		this.subject = subject;
		this.body = body;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
