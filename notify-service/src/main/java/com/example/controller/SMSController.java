package com.example.controller;

import java.util.HashMap;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.dto.SMSDTO;
import com.example.sms.ISenderService;
import com.example.util.ServiceResult;

@RestController
@RequestMapping("/sms")
public class SMSController {
	private static Logger log = LoggerFactory.getLogger(SMSController.class);
	@Resource
	private ISenderService senderService;

	/**
	 * 发送短信验证码
	 * 
	 * @param mobile
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping("/verifyCode")
	public JSONObject sendVerifyCode(@RequestParam String mobile, @RequestParam String verifyCode) {
		JSONObject json = new JSONObject();
		ServiceResult<Object> result = senderService.sendVerifyCode("13810061048");

		return null;
	}

	/**
	 * 发送模版短信
	 * 
	 * @param dto 短信模版发送短信入参
	 * @return
	 */
	@RequestMapping(value = "/tpSms", method = RequestMethod.POST)
	public JSONObject sendTemplateMsg(@RequestBody SMSDTO dto) {
		log.info("[mobile]:"+dto.getMobile() + "---[templateCode]:" + dto.getTemplateCode()+"---[param]:"+dto.getParam());
		JSONObject json = new JSONObject();
		ServiceResult<Object> result = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", dto.getMobile());
		map.put("templateCode", dto.getTemplateCode());
		map.put("param", dto.getParam());
		try {
			result = senderService.sendSMSWidthCallable(map);
		} catch (Exception e) {
			json.put("status", 503);
			json.put("message", "发送异常:" + e.getMessage());
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		if (null != result) {
			log.info("[code]:"+result.getCode() + "[message]:" + result.getMessage());
			if (result.getSuccess()) {
				json.put("status", 200);
				json.put("message", null);
			} else {
				json.put("status", 503);
				json.put("message", "错误代码:" + result.getCode() + "----错误信息:" + result.getMessage());
			}
		}

		return json;
	}

}
