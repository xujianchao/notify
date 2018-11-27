package com.example.sms.impl;
import java.util.HashMap;

import java.util.Map;
import java.util.logging.LogManager;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.common.ConstantsEJS;
import com.example.execption.BusinessException;
import com.example.sms.ISenderService;
import com.example.sms.SMSSendModel;
import com.example.util.ServiceResult;

/**
 * 短信发送服务
 *                       
 * @Filename: SMSSender.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihailong@javamalls.com
 *
 */
@Service(value = "senderService")
public class SMSSenderServiceImpl implements ISenderService {
    private static Logger log = LoggerFactory.getLogger(SMSSenderServiceImpl.class);
    @Resource(name = "SMSSendModel")
    private SMSSendModel  SMSSendModel;

    @Override
    public ServiceResult<Boolean> sendSMS(Map<String, Object> map) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            SMSSendModel.sendSMS(map);
            result.setResult(Boolean.TRUE);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                result.setSuccess(false);
                result.setMessage(e.getMessage());
            } else {
                e.printStackTrace();
                log.error("短信发送出现未知异常：" + e);
                result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
                result.setMessage("短信发送出现未知异常");
            }
        }
        return result;
    }

    @Override
    public ServiceResult<Object> sendSMSWidthCallable(Map<String, Object> map) {
        ServiceResult<Object> result = new ServiceResult<Object>();
        try {
            result.setResult(SMSSendModel.sendSMSWidthCallable(map));
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                result.setSuccess(false);
                result.setMessage(e.getMessage());
            } else {
                e.printStackTrace();
                log.error("短信发送出现未知异常：" + e);
                result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
                result.setMessage("短信发送出现未知异常");
            }
        }
        return result;
    }

    @Override
    public ServiceResult<Object> sendVerifyCode(String mobile) {
        ServiceResult<Object> result = new ServiceResult<Object>();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("mobile", mobile);
            result.setResult(SMSSendModel.sendVerifyCode(param));
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                result.setSuccess(false);
                result.setMessage(e.getMessage());
                log.debug(e.getMessage());
            } else {
                e.printStackTrace();
                log.error("短信发送出现未知异常：" + e);
                result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
                result.setMessage("短信发送出现未知异常");
            }
        }
        return result;
    }
    
	@Override
	public ServiceResult<Object> sendStoreNotice(Map<String, Object> map) {
		return send(map, SMSSendModel.STORE_NOTICE);
	}
	
	private ServiceResult<Object> send(Map<String, Object> map, int type){
		ServiceResult<Object> result = new ServiceResult<Object>();
        try {
            result.setResult(SMSSendModel.sendSMSWidthCallable(map, type));
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                result.setSuccess(false);
                result.setMessage(e.getMessage());
            } else {
                e.printStackTrace();
                log.error("短信发送出现未知异常：" + e);
                result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
                result.setMessage("短信发送出现未知异常");
            }
        }
        return result;
	}
}
