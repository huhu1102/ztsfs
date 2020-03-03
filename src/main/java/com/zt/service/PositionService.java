package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Position;

/**
 * @author wl
 * @date 2019年4月26日 
 */
public interface PositionService {
	/*
	 * 分页模糊条件查询
	 */
	public ResultPage<Position> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;
	/*
	 * 职位信息的修改
	 */
	public  ResultObject<Position> savePosition(Position position)throws BusinessRuntimeException;
	/*
	 * 职位信息的删除
	 */
	public ResultObject<Position> deletDep(long id)throws BusinessRuntimeException;
}
