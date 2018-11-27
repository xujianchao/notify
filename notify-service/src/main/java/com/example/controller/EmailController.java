package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.dto.EmailDTO;
import com.example.email.SendMail;

@RestController
public class EmailController {
	private static Logger log = LoggerFactory.getLogger(EmailController.class);

	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public JSONObject sendEmail(@RequestBody EmailDTO dto) {
		JSONObject json = new JSONObject();
		json.put("status", "503");
		json.put("message", null);
		if(null== dto.getEmail() ||"".equals(dto.getEmail())){
			json.put("message", "邮件地址不能为空!");
			return json;
		}
		if(null== dto.getSubject() ||"".equals(dto.getSubject())){
			json.put("message", "邮件主题不能为空!");
			return json;
		}
		if(null== dto.getBody() ||"".equals(dto.getBody())){
			json.put("message", "邮件主体内容不能为空!");
			return json;
		}
		SendMail sendMail = new SendMail();
		try {
			boolean sendFlag = sendMail.sendEmail(dto.getEmail(), dto.getSubject(), dto.getBody());
			if(sendFlag) {
				json.put("status", 200);
			}else {
				json.put("status", 503);
			}
		} catch (Exception e) {
			json.put("message", "邮件发送异常:"+e.getMessage());
		}
		return json;
	}

}
