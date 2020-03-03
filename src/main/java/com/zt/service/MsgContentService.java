/**  
* 
*/  
 
package com.zt.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.zt.model.ResultPage;
import com.zt.po.MsgContent;
import com.zt.po.SysMessage;
import com.zt.po.Users;

/**
 * @author whl
 * @date 2019年5月20日 
 */
public interface MsgContentService {

    int sendMsg(MsgContent msg);

    int addMsg2AllHr( List<Users> hrs,  Long mid);

    ResultPage<SysMessage> getSysMsg( int start,Integer size, Long userId);

    int markRead( Long flag,  Long userId);
}
