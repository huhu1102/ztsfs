/**
 * 
 */
package com.zt.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author yh
 * @date 2019年5月20日
 */
@Controller
public class WebSocketController {
	@Autowired
	//消息模板
	private SimpMessagingTemplate  stp;
	
	
}
