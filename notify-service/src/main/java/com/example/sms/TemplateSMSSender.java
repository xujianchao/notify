package com.example.sms;

import java.util.HashMap;

import java.util.Map;

import com.example.config.EjavashopConfig;


/**
 * 模板短信发送
 *                       
 * @Filename: TemplateSMSSender.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
public class TemplateSMSSender extends AbstractSMSSender {
    public TemplateSMSSender(String mobile, String content) {
        this.mobile = mobile;
        this.content = content;
    }
    
    public TemplateSMSSender(String mobile,String templateCode,Map<String,String> param) {
        this.mobile = mobile;
        this.templateCode=templateCode;
        this.templateParam=param;
    }

    @Override
    protected Map<String, String> setSMSParamMap() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("url", EjavashopConfig.SMS_URL);
        param.put("mobile", this.mobile);
        param.put("content", this.content);
        return param;
    }

}
