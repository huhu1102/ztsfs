/**  
* 
*/  
 
package com.zt.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zt.model.ResultObject;
import com.zt.po.Users;
import com.zt.service.UsersService;

/**
 * @author whl
 * @date 2019年4月17日 
 */

@Service
@Transactional
public class LoginServer implements UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
  @Autowired
  UsersService userSever;

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Users user=null;
	try {
		user=userSever.loadUserByUsername(username);
		logger.info(username+"***"+user );
	} catch (Exception e) {
		
		e.printStackTrace();  
	}
	return user;
}
  
}
