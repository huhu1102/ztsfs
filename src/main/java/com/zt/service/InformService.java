package com.zt.service;



import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Inform;

/**
 * @author wl
 * @date 2019年4月16日
 */
public interface InformService {
	/*
	 * 分页模糊条件查询
	 */
	public ResultPage<Inform> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;
	/*
	 * 修改通告信息
	 */
	public ResultObject<Inform> saveInform(Inform inform)throws BusinessRuntimeException;
	/*
	 * 删除
	 */
	public ResultObject<Inform> deletIn(long id)throws BusinessRuntimeException;
}
