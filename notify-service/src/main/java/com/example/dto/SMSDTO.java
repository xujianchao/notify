package com.example.dto;

import java.util.Map;


public class SMSDTO {
	private String mobile;//电话
	private String templateCode;//短信模版
	private Map<String, String> param;//短信模版参数
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public SMSDTO(String mobile, String templateCode, Map<String, String> param) {
		super();
		this.mobile = mobile;
		this.templateCode = templateCode;
		this.param = param;
	}
	public Map<String, String> getParam() {
		return param;
	}
	public void setParam(Map<String, String> param) {
		this.param = param;
	}



}
