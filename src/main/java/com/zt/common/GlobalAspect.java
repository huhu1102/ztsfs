/**
 * 
 */
package com.zt.common;

/**
 * @author yh
 * @date 2019年4月26日
 */

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint; 
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before; 
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder; 
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class GlobalAspect {
	private final static Logger logger = LoggerFactory.getLogger(GlobalAspect.class);
	
	//这个切点的表达式需要根据自己的项目来写
		@Pointcut("execution(public * com.zt.controller..*(..))")
		public void log() {
			
		}
}
