package com.example.sms;

import java.util.HashMap;

import java.util.Map;

import com.example.config.EjavashopConfig;
import com.example.util.RandomUtil;

/**
 * 短信验证码发送服务
 *                       
 * @Filename: SMSSender.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
public class VerifyCodeSender extends AbstractSMSSender {
    public static final String TMEPLATE_CODE = "SMS_140655052"; 

    public VerifyCodeSender(String mobile) {
        this.mobile = mobile;
        this.verifyCode = RandomUtil.randomNumber(6);
        this.templateCode = TMEPLATE_CODE;
        Map<String, String> param = new HashMap<String, String>();
        param.put("code", this.verifyCode);
        this.templateParam = param;
    }

    @Override
    protected Map<String, String> setSMSParamMap() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("url", EjavashopConfig.SMS_URL);
        param.put("mobile", mobile);
        param.put("content", content);
        return param;
    }

}
