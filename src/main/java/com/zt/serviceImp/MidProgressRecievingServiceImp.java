package com.zt.serviceImp;

import com.zt.common.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.dao.MidProgressRecievingDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultPage;
import com.zt.po.MidProgressRecieving;
import com.zt.service.MidProgressRecievingService;

import java.util.Map;

/**
 * @author whl
 * @date 2019年7月5日 
 */
@Service("midprogressService")
public class MidProgressRecievingServiceImp implements MidProgressRecievingService {
  @Autowired
  MidProgressRecievingDao   midRecievingDao;
	@Override
	public ResultPage<MidProgressRecieving> findbyPage(String clientName,String productName,String empName,String start,String end,int page, int size)
			throws BusinessRuntimeException {
		ResultPage<MidProgressRecieving> ro=new ResultPage<MidProgressRecieving>();
		Pageable pageable = PageRequest.of(page-1,size);

		Map<String, String> stringStringMap = Utils.updateTime(start,end);
		start = stringStringMap.get("0");
		end = stringStringMap.get("1");
		Page<MidProgressRecieving> pages = midRecievingDao.findbyPage(clientName,productName,empName,start,end,pageable);
		 if (pages!=null) {
	        	 ro.setData(pages.getContent());
	    	     ro.setTotal( pages.getTotalElements());
	    	     ro.setTotalPages(pages.getTotalPages());
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		
		return ro;
		
	}

}
