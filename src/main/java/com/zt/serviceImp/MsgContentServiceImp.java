/**  
* 
*/  
 
package com.zt.serviceImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zt.common.UsersUtils;
import com.zt.dao.MsgContentDao;
import com.zt.dao.SysMessageDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultPage;
import com.zt.po.MsgContent;
import com.zt.po.SysMessage;
import com.zt.po.Users;
import com.zt.service.MsgContentService;

/**
 * @author whl
 * @date 2019年5月20日 
 */
@Service
@Transactional
public class MsgContentServiceImp  implements MsgContentService{
    
	@Autowired
	MsgContentDao  msgDao; 
	@Autowired
	SysMessageDao  sysMsgDao; 
	//消息產生1
	@Override
	public int sendMsg(MsgContent msg) {
		int re=0;
		msg.setCreateDate(new Date());
		msg.setUserId(UsersUtils.getCurrentHr().getId());//发消息人Id
		msg.setUsername(UsersUtils.getCurrentHr().getUsername());//发消息人
		MsgContent  nmsg=msgDao.saveAndFlush(msg);
		if (nmsg!=null) {
			re=1;
		}
		return re;
	}
    /**
            * 給所有人發消息 
     */
	@Override
	public int addMsg2AllHr(List<Users> users, Long mid) {
		int re=0;
		MsgContent m=msgDao.findById((long)mid);
		List<SysMessage> mslist=new ArrayList<SysMessage>();
		 for (int i = 0,n=users.size(); i <n; i++) {
			 Users u=users.get(i);
			 if (null!=u) {
				 SysMessage  s=new SysMessage();
				 s.setMid(mid);
				 s.setMsgState(1);//1未读 2已读
				 s.setMsgType(1);
				 s.setUserId(u.getId());
				 s.setMsgContent(m);
				mslist.add(s);
			}
		} 
		 if (mslist.size()>0) {
		 List<SysMessage>  sys = sysMsgDao.saveAll(mslist);
		 if (sys!=null) {
			 re=sys.size();
		}else {
			throw  new  BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		}
		return re;
	}
   /**
              *   个人消息分页查询
    */
	@Override
	public ResultPage<SysMessage> getSysMsg(int page, Integer size, Long userId) {
		ResultPage<SysMessage> ro=new ResultPage<SysMessage>();
		Pageable pageable = PageRequest.of(page,size);
		Page<SysMessage> pages = sysMsgDao.findbySyspages(userId,pageable);
		 if (pages!=null) {
	        	int totals=(int) pages.getTotalElements();
	        	
	        	
	        	 ro.setData(pages.getContent());
	    	     ro.setTotal(totals);
	    	     ro.setTotalPages(pages.getTotalPages());
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		
		return ro;
	}
    /**
               * 标记是否已读
     */
	@Transactional
	@Override
	public int markRead(Long flag, Long userId) {
		int re=0;
		SysMessage s= sysMsgDao.findChecked(flag,userId);
		if (flag!=-1) {
			s.setMsgState(2);
			SysMessage sys=	sysMsgDao.saveAndFlush(s);
			if (null!=sys) {
				re=1;
			}
		}else {
          int slist=sysMsgDao.findbyuserId(userId);
          if (slist>=0) {
			re=1;
		}
		}
		return re;
	}

}
