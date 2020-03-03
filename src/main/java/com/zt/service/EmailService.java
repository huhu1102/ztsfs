package com.zt.service;

import com.zt.model.BusinessRuntimeException;

/**
 * @author wl
 * @date 2019年5月20日 
 */
public interface EmailService {
	/**
	 * 
	 * @param to 收件人
	 * @param subject 主题
	 * @param content 内容
	 */
	void sendSimplemail(String to,String subject,String content)throws BusinessRuntimeException;
}
