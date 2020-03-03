/**
 * 
 */
package com.zt.controller;







import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;



import com.zt.service.VechclerequestDetailsService;



/**
 * @author yh
 * @date 2019年5月21日
 */
@RestController
@RequestMapping(value="/vechclerequestdetails")
public class VechclerequestDetailsController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private VechclerequestDetailsService  vqds;
	//此方法要写
	//此方法要写
//		@RequestMapping(value="/findbyDate",method=RequestMethod.GET)
//		
//		public List<VechclerequestDetails> findNotUse(List<Date> datelist) {
//			
//			return  vqds.findNotUse(datelist);
//}
}
