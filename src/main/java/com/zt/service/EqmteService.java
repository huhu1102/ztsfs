/**
 * 
 */
package com.zt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Bidding;
import com.zt.po.Employee;
import com.zt.po.Eqmt;

/**
 * @author yh
 * @date 2019年4月18日
 */
public interface EqmteService {
	public List<Eqmt> updateBymachineName(String machineName);
	public boolean updateByrepairman(String repairResult,String repairman);
	
	/*
     * 分页查询无条件
     */
    ResultPage<Eqmt> findbyPage(Integer page,Integer size,String sort);
    /*
     * 分页模糊条件查询
     */
    Page<Eqmt> findSearch(String deviceName,Pageable pageable);
    
   
   public boolean update(Eqmt eqmt);
   
   //新增的方法
   public ResultPage<Eqmt> findEqmt(String repairman, int page, int size) throws BusinessRuntimeException;
   
   
   public  ResultObject<Eqmt> addEqmt(Eqmt eqpmt)throws BusinessRuntimeException;
	/**
	 * @param id
	 * @return
	 */
	public ResultObject<Eqmt> deletEqmt(long id)throws BusinessRuntimeException;
 
	
}
