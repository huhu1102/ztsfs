/**  
* 
*/  
 
package com.zt.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zt.common.PageUtil;
import com.zt.common.UsersUtils;
import com.zt.dao.SysMessageDao;
import com.zt.dao.UsersDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultPage;
import com.zt.po.MsgContent;
import com.zt.po.SysMessage;
import com.zt.po.Users;
import com.zt.service.MsgContentService;
import com.zt.service.SysMsgService;

/**
 * @author whl
 * @date 2019年5月20日 
 */
@Service
@Transactional
public class SysMsgServiceImp implements SysMsgService{

    @Autowired
    MsgContentService msgContentService;
    @Autowired
    UsersDao userDao;
    @Autowired
	SysMessageDao  sysMsgDao; 
    @Override
//    @PreAuthorize("hasRole('ROLE_admin')")//只有管理员可以发送系统消息
    public boolean sendMsg(MsgContent msg) {
        int result = msgContentService.sendMsg(msg);
        List<Users> allHr = userDao.findAll();
        int result2 = msgContentService.addMsg2AllHr(allHr, msg.getId());
        return result2==allHr.size();
    }
    @Override
    public ResultPage<SysMessage> getSysMsgByPage()throws BusinessRuntimeException {
		ResultPage<SysMessage> ro=new ResultPage<SysMessage>();
//		PageRequest pageable = new PageRequest(page-1,size);
		long userId=UsersUtils.getCurrentHr().getId();
		List<SysMessage> pagelist=new ArrayList<SysMessage>();
	       pagelist=sysMsgDao.findAllByPage(userId);
		int count=sysMsgDao.countAllData(userId); 
//		Page<SysMessage> pages = sysMsgDao.findbySyspages(UsersUtils.getCurrentHr().getId(),pageable);
		 if (pagelist!=null) {
			 ro.setData(pagelist);
    	     ro.setTotal(count);
//    	     ro.setTotalPages(PageUtil.g/etTotalPages(count, size));
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		return ro;
    }
    @Override
    public boolean markRead(Long flag,Long userId) {
        if (flag != -1) {//全部
            return msgContentService.markRead(flag,userId)==1;
        }
        msgContentService.markRead(flag,userId);
        return true;
    }



	
}
