/**
 * 
 */
package com.zt.po;

/**
 * @author yh
 * @date 2019年5月6日
 */
//服务器发往客户端消息实体
public class Response {
	public void setResponseMessage(String responseMessage)
	{ this.responseMessage = responseMessage; }
	private String responseMessage; 
	public Response(String responseMessage)
	{ this.responseMessage = responseMessage; } 
	public String getResponseMessage() 
	{ return responseMessage; }
	
}
