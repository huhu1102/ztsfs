/**
 * 
 */
package com.zt.config;

import org.hibernate.validator.HibernateValidator; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator; 
import javax.validation.ValidatorFactory;

//import lombok.extern.slf4j.Slf4j;

/**
 * @author yh
 * @date 2019年4月26日
 */

//@Slf4j
@Configuration
public class ValidatorConfiguration {
	 @Bean 
	 public Validator getValidatorFactory(){
		 ValidatorFactory validatorFactory = Validation
				 .byProvider(HibernateValidator.class)
				 .configure()
				 .failFast(true)
				 .buildValidatorFactory();
		 return validatorFactory.getValidator();	
	 }
	
}