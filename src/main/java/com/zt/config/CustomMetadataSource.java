package com.zt.config;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.zt.po.Menu;
import com.zt.po.SysRole;
import com.zt.service.MenuService;
import com.zt.vo.MenuViewModel;


/**
 * Created by sang on 2017/12/28.
 */
@Component
public class CustomMetadataSource implements FilterInvocationSecurityMetadataSource {
   @Autowired
    MenuService menuService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
//        List<Menu> list=menuService.getAllMenu();
        List<Menu> allMenu = menuService.getAllMenuData();
        for (Menu menu : allMenu) {
            if (antPathMatcher.match(menu.getUrl(), requestUrl)&&menu.getSysRole().size()>0) {
                logger.info(requestUrl);
                logger.info(menu.getUrl());
                List<SysRole> roles = menu.getSysRole();
                int size = roles.size();
                 String[] values = new String[size];
                                for (int i = 0; i < size; i++) {
                    values[i] = roles.get(i).getName();
                    logger.info(roles.get(i).getName());
                }
              return SecurityConfig.createList(values);
            }
        }
        //没有匹配上的资源，都是登录访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
