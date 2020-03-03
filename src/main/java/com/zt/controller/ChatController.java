package com.zt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zt.common.UsersUtils;
import com.zt.model.RespBean;
import com.zt.model.ResultPage;
import com.zt.po.MsgContent;
import com.zt.po.SysMessage;
import com.zt.po.Users;
import com.zt.service.SysMsgService;
import com.zt.service.UsersService;

import java.util.List;

/**
 * 处理通知消息的Controller
 * 登录即可访问
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UsersService usersService;
    @Autowired
    SysMsgService sysMsgService;

    @RequestMapping(value = "/hrs", method = RequestMethod.GET)
    public List<Users> getAllHr() {
        return usersService.getAllUsersExceptAdmin(UsersUtils.getCurrentHr().getId());
    }

    @RequestMapping(value = "/nf", method = RequestMethod.POST)
    public RespBean sendNf(MsgContent msg) {
        if (sysMsgService.sendMsg(msg)) {
            return RespBean.ok("发送成功!");
        }
        return RespBean.error("发送失败!");
    }

    @RequestMapping(value="/sysmsgs",method=RequestMethod.GET)
    public ResultPage<SysMessage> getSysMsg() {
    	logger.info(UsersUtils.getCurrentHr()+"");
        return sysMsgService.getSysMsgByPage();
    }

    @RequestMapping(value = "/markread", method = RequestMethod.POST)
    public RespBean markRead(Long flag) {
    	long userId=UsersUtils.getCurrentHr().getId();
        if (sysMsgService.markRead(flag,userId)) {
            if (flag == -1) {
                return RespBean.ok("");
            } else {
                return RespBean.ok("");
            }
        } else {
            if (flag == -1) {
                return RespBean.error("multiple");
            } else {
                return RespBean.error("single");
            }
        }
    }
}
