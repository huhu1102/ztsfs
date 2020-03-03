package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultPage;
import com.zt.po.MidProgressRecieving;

/**
 * @author whl
 * @date 2019年7月5日 
 */
public interface MidProgressRecievingService {
	/*
	 * 分页查询
	 */
	ResultPage< MidProgressRecieving> findbyPage(String clientName,String productName,String empName,String start,String end,int page, int size) throws BusinessRuntimeException;
}
