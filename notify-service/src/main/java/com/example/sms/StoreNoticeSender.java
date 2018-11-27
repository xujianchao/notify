package com.example.sms;

import java.util.HashMap;

import java.util.Map;



/**
 * 开店成功短信发送
 *                       
 * @Filename: StoreNoticeSender.java
 * @Version: 1.0
 * @Author: tuxin
 *
 */
public class StoreNoticeSender extends AbstractSMSSender {
    public static final String TMEPLATE_CODE = "SMS_140725953"; 

    public StoreNoticeSender(String mobile, String account, String password) {
        this.mobile = mobile;
        this.templateCode = TMEPLATE_CODE;
        Map<String, String> param = new HashMap<String, String>();
        param.put("account", account);
        param.put("password", password);
        this.templateParam = param;
    }

    @Override
    protected Map<String, String> setSMSParamMap() {
        Map<String, String> param = new HashMap<String, String>();
        return param;
    }

}
