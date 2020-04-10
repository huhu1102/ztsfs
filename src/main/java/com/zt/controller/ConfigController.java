package com.zt.controller;

import com.zt.model.ResultObject;
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
@RequestMapping("/config")
public class ConfigController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UsersService usersService;
    @Autowired
    SysMsgService sysMsgService;

    /**
     * @param
     * @param
     * @return
     */
    @RequestMapping(value="/getMenu",method=RequestMethod.GET)
    public ResultObject<Users> getMenu(){
        logger.info("请求登录");
        return usersService.getMenu();
    }


}
