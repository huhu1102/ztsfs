package com.zt.common;

import org.springframework.security.core.context.SecurityContextHolder;

import com.zt.po.Employee;
import com.zt.po.Users;

/**
 * Created by sang on 2017/12/30.
 */

 public class UsersUtils {
	 public static Users getCurrentHr() { 
		 Users user= (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 return user; 
 }
 }
