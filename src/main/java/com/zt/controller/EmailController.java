package com.zt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zt.service.EmailService;

/**
 * @author wl
 * @date 2019年5月20日 
 */
@RestController
@RequestMapping("/email")
public class EmailController {
	@Autowired
	private EmailService emailService;
	
	@RequestMapping("/send")
	public void sendSimpleMail(String to,String subject,String content) {
		emailService.sendSimplemail(to, subject, content);
	}
}
