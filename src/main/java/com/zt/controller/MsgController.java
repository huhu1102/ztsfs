/**  
* 
*/  
 
package com.zt.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.zt.model.ChatResp;

/**
 * @author whl
 * @date 2019年5月20日 
 */
@Controller
public class MsgController   {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	 @Autowired
	 SimpMessagingTemplate messagingTemplate;
	 @MessageMapping("/ws/chat")
	  public void handleChat(Principal principal, String msg) {
		 logger.info(msg+"打发打发阿萨德");
	        String destUser = msg.substring(msg.lastIndexOf(";") + 1, msg.length());
	        
	        String message = msg.substring(0, msg.lastIndexOf(";"));
	        messagingTemplate.convertAndSendToUser(destUser, "/queue/chat", new ChatResp(message, principal.getName()));
	    }
	 @MessageMapping("/ws/nf")
	    @SendTo("/topic/nf")
	    public String handleNF() {
	        return "系统消息";
	    }
	
	
}
