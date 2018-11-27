package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.config.LocalConf;

@SpringBootApplication
public class NotifyServiceApplication {

	public static void main(String[] args) {
		LocalConf.getInstance();//初始化配置
		
		SpringApplication.run(NotifyServiceApplication.class, args);
	}
}
