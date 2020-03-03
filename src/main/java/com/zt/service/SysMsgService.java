/**  
* 
*/  
 
package com.zt.service;



import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultPage;
import com.zt.po.MsgContent;
import com.zt.po.SysMessage;

/**
 * @author whl
 * @date 2019年5月20日 
 */
public interface SysMsgService {

	public boolean sendMsg(MsgContent msg)throws BusinessRuntimeException;
	public ResultPage<SysMessage> getSysMsgByPage()throws BusinessRuntimeException;
	public boolean markRead(Long flag, Long userId)throws BusinessRuntimeException;
	
}
