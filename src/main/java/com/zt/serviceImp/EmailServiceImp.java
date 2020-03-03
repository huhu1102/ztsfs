package com.zt.serviceImp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.service.EmailService;

/**
 * @author wl
 * @date 2019年5月20日 
 */
@Service("emailService")
public class EmailServiceImp implements EmailService{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Value("${spring.mail.username}")
	//使用@Value注解注入application.properties中指定的用户名
	private String from;
	@Autowired
	//该接口是springboot整合了mail提供的,用来发送文件的
	private JavaMailSender mailSender;
	@Override
	public void sendSimplemail(String to, String subject, String content) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);//收件人
		message.setSubject(subject);//主题
		message.setText(content);//内容
		message.setFrom(from);//发件人
		mailSender.send(message);
		if(mailSender==null) {
			throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
		}
	}

}
