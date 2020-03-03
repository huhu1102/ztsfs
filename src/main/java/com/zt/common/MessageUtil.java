/**  
* 
*/  
 
package com.zt.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zt.dao.MsgContentDao;
import com.zt.dao.SysMessageDao;
import com.zt.po.MsgContent;
import com.zt.po.SysMessage;
import com.zt.po.Users;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author whl
 * @date 2019年5月22日
 *  发小消息使用 
 */
@Component
public class MessageUtil {
	@Autowired
	MsgContentDao  msgDao; 
	@Autowired
	SysMessageDao  sysMsgDao; 
	 
	/**
	 * @param title 消息标题
	 * @param content  消息内容	
	 * @param styles  消息类型 实体类名
	 * @param styleId  消息类型Id 查找相关实体的Id
	 * @param userId  要接收消息人userID
	 * @return int  0,失败   ;1,成功
	 */
	public int sendMsg(String title,String content,String styles, long styleId, long userId) {
		 int result=0;
		 MsgContent msg=new MsgContent();
		 msg.setCreateDate(new Date());
		 msg.setMessage(content);
		 msg.setTitle(title);
		 msg.setUsername(UsersUtils.getCurrentHr().getUsername());
		 msg.setUserId(UsersUtils.getCurrentHr().getId());
		 MsgContent reMsg=msgDao.save(msg);
		 if (reMsg!=null) {
		 SysMessage  s=new SysMessage();
		 s.setMid(reMsg.getId());
		 s.setMsgState(1);
		 s.setMsgType(1);//1未读 2已读
		 s.setUserId(userId);
		 s.setMsgContent(reMsg);  
		 SysMessage sys= sysMsgDao.save(s);
		 if(null!=sys) {
			 result=1;
		 }
		 }
		return result;
	}
	public int sendMsg(String title,String content,String styles, long styleId, List<Long> userIds) {
		 int result=0;
		 MsgContent msg=new MsgContent();
		 msg.setCreateDate(new Date());
		 msg.setMessage(content);
		 msg.setTitle(title);
		 msg.setMsgStyle(styles);
		 msg.setMsgStyleId(styleId);
		 msg.setUsername(UsersUtils.getCurrentHr().getUsername());
		 msg.setUserId(UsersUtils.getCurrentHr().getId());
		 MsgContent reMsg=msgDao.save(msg);
		 if (reMsg!=null) {
			 List<SysMessage> list=new ArrayList<SysMessage>();
			 for (int i = 0,n=userIds.size(); i < n; i++) {
				 SysMessage  s=new SysMessage();
				 s.setMid(reMsg.getId());
				 s.setMsgState(1);
				 s.setMsgType(1);//1未读 2已读
				 s.setUserId(userIds.get(i));
				 s.setMsgContent(reMsg);
				 list.add(s);
			 }
			 List<SysMessage> sys=sysMsgDao.saveAll(list);
			 if(null!=sys) {
				 result=1;
			 }
		 }
		return result;
	}
	/**
	 * @param styles
	 * @param styleId
	 *     撤销消息的方法
	 * @return
	 */
	@Transactional
	public int cancelMsg(String styles, long styleId) {
		int re=0;
		Users u=UsersUtils.getCurrentHr();
		MsgContent msg=msgDao.findmsg(styles,styleId);
		if (msg!=null) {
			
		
		msg.setBackoutDate(new Date());
		msg.setBackoutName(u.getUsername());
		msg.setBackoutId(u.getId());
		msg= msgDao.saveAndFlush(msg);
	    boolean  e= sysMsgDao.updateSysMsg(msg.getId());
			if(e) {
				re=1;
			}
		}
		return re;
	}
	
}
