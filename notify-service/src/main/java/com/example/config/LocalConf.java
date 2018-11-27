package com.example.config;

import java.io.BufferedInputStream;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class LocalConf {

	private static Logger log = LoggerFactory.getLogger(LocalConf.class);
	private JSONObject conf;
	private static LocalConf instance = null;
	
	private LocalConf(){
		conf = new JSONObject();
		String path = this.getClass().getClassLoader().getResource("config.properties").getPath();
		log.info("配置路径.................."+path);
		JSONObject json = loadConf(path);
		EjavashopConfig.MAIL_SERVER_HOST=json.getString("MAIL_SERVER_HOST");
		EjavashopConfig.MAIL_SERVER_PORT=json.getString("MAIL_SERVER_PORT");
		EjavashopConfig.SEND_EMAIL_NAME=json.getString("SEND_EMAIL_NAME");
		EjavashopConfig.SEND_EMAIL_PASSWORD=json.getString("SEND_EMAIL_PASSWORD");
	}

	public JSONObject getConf() {
		return conf;
	}
	
	public static LocalConf getInstance(){
		if (instance == null) {
			instance = new LocalConf();
		}
		return instance;
	}

	/**
	 * 读取配置文件
	 * 
	 * @param filePath
	 * @return
	 */
	public JSONObject loadConf(String filePath) {
		log.info("开始加载配置文件:" + filePath);
		Properties props = new Properties();
		InputStream in = null;
		FileInputStream fin= null;
		try {
			
			in = new BufferedInputStream(
					ClassLoader.getSystemResourceAsStream(filePath));
			props.load(in);
			in.close();
		} catch (Exception e) {
			try{
				fin = new FileInputStream(filePath);
				in = new BufferedInputStream(fin);
				props.load(in);
				in.close();
				fin.close();
			}catch(Exception es){
				es.printStackTrace();
				System.exit(0);
			}
		}finally{
			Enumeration<?> en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String property = props.getProperty(key);
				conf.put(key, property);
			}
		}
		return conf;
	}
	
	public static void main(String[] args){
		LocalConf c = new LocalConf();
		System.out.println(c.getConf());
	}
	
}
