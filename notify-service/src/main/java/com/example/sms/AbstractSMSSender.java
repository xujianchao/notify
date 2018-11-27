package com.example.sms;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.example.bean.VerifyCodeResult;
import com.example.execption.BusinessException;
import com.google.gson.Gson;

/**
 * 短信发送
 *                       
 * @Filename: AbstractSMSSender.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
public abstract class AbstractSMSSender {
    /**
     * 短信请求url
     */
    protected static final String URL      = "url";
    /**
     * 授权key
     */
    protected static final String ACCOUNT  = "AA00269";
    /**
     * 密钥
     */
    protected static final String PASSWORD = "AA0026974";

    protected static final String MOBILE   = "mobile";
    
    /**
     * 阿里云短信 ACCESSKEY_ID
     */
    protected static final String ACCESSKEY_ID = "LTAIkBMrOB1l6X1P";
    
    /**
     * 阿里云短信 ACCESSKEY_SECRET
     */
    protected static final String ACCESSKEY_SECRET = "39F4Bcx0GHAVKDakfc9DmKZztDevOm";
    
    /**
     * 阿里云短信签名
     */
    protected  static final String SIGN_NAME= "更生行";

    /**
     * 全部被叫号码<br>
     * 短信发送的目的号码.多个号码之间用半角逗号隔开 
     */
    protected String              mobile   = "";
    /**
     * 短信内容
     */
    protected String              content  = "";

    /**
     * 验证码
     */
    protected String              verifyCode;
    
    /**
     * 阿里云
     * 模板号码
     */
    protected String			  templateCode;
    
    /**
     * 阿里云
     * 模板参数
     */
    protected Map<String, String> templateParam;
    

    /**
     * 能用发送短信方法
     * @return
     */
    public VerifyCodeResult sendSMS() {
    	try {
			return sendSMSOfAliyun();
		} catch (ClientException e) {
			throw new BusinessException(e.getMessage());
		}

    }

    /**
     * 移除非内容参数
     * @param param
     */
    private void removeAuthParam(Map<String, String> param) {
        param.remove(URL);
    }

    /**
     * 短信发送需要的参数,对应短信模板的替换变量,短信发送子类需实现此方法
     * @return
     */
    protected abstract Map<String, String> setSMSParamMap();
    
    private VerifyCodeResult sendSMSOfAliyun() throws ClientException{
    	//设置超时时间-可自行调整
    	System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
    	System.setProperty("sun.net.client.defaultReadTimeout", "10000");
    	//初始化ascClient需要的几个参数
    	final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
    	final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
    	//替换成你的AK
    	final String accessKeyId = ACCESSKEY_ID;//你的accessKeyId,参考本文档步骤2
    	final String accessKeySecret = ACCESSKEY_SECRET;//你的accessKeySecret，参考本文档步骤2
    	//初始化ascClient,暂时不支持多region（请勿修改）
    	IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
    	accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
    	IAcsClient acsClient = new DefaultAcsClient(profile);
    	 //组装请求对象
    	 SendSmsRequest request = new SendSmsRequest();
    	 //使用post提交
    	 request.setMethod(MethodType.POST);
    	 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
    	 request.setPhoneNumbers(this.mobile);
    	 //必填:短信签名-可在短信控制台中找到
    	 request.setSignName(SIGN_NAME);
    	 //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
//    	 request.setTemplateCode("SMS_140585089");
    	 request.setTemplateCode(this.templateCode);
    	 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
    	 //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
//    	 request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"88568\"}");
    	 request.setTemplateParam(new Gson().toJson(templateParam));
    	 //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
    	 //request.setSmsUpExtendCode("90997");
    	 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
    	 request.setOutId("yourOutId");
    	//请求失败这里会抛ClientException异常
    	SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
    	if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
    		VerifyCodeResult vcr = new VerifyCodeResult();
    		vcr.setReturnstatus(sendSmsResponse.getCode());
    		vcr.setSuccessCounts("1");
    		vcr.setMessage(sendSmsResponse.getMessage());
    		if (this.verifyCode != null) {
                vcr.setVerifyCode(this.verifyCode);
            }
    		return vcr;
    	} else {
    		throw new BusinessException(sendSmsResponse.getMessage());
    	}
    }
    
}
