/**
 * 
 */
package com.zt.serviceImp;

import com.zt.common.PageUtil;
import com.zt.dao.EqmtDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Eqmt;
import com.zt.service.EqmteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yh
 * @date 2019年4月18日
 */
@Service("eqmteService")
@CacheConfig(cacheNames = "zt_caches_eqmte")
public class EqmteServiceImp implements EqmteService {
	@Autowired
	private EqmtDao emddao; 
	
	
	//修改设备维护表方法
	@Override
	@CachePut(key="'eqm_'+#machineName")
	public boolean updateByrepairman(String repairResult, String repairman) {
		boolean flag=false;
			int resut=0;
		try {
			resut=emddao.updateByrepairman(repairResult, repairman);
			if(resut!=0) {
				flag=true;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}


	//查询设备维护表方法
	@Override
	@Cacheable(key="'eqm_'+#machineName")
	public List<Eqmt> updateBymachineName(String machineName) {
		List<Eqmt> mpmt=null;
		
		try {
			mpmt=emddao.updateBymachineName(machineName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mpmt;
	}

	//分页查询
	@Override
	@Cacheable(key="'eqm_'+#machineName")
	public ResultPage<Eqmt> findbyPage(Integer page, Integer size, String sort) {
		ResultPage<Eqmt> rp=new ResultPage<>();
		Direction sortt=null;
		if (sort.equals("asdfa")&&sort!=null) {
		 	sortt=   Sort.Direction.DESC;
		}else {
			sortt=   Sort.Direction.ASC;
		}
        Pageable pageable = PageRequest.of(page,size,sortt,"createDate");
	    Page<Eqmt> pages=emddao.findAll(pageable);
        int totals=   (int) pages.getTotalElements();
        List<Eqmt> rolelist= pages.getContent();
	     rp.setData(rolelist);
	     rp.setTotal(totals);
	     rp.setSuccess(true);
		return rp;
	}

	//模糊分页查询
	@Override
	@Cacheable(key="'eqm_'+#machineName")
	public Page<Eqmt> findSearch(String deviceName, Pageable pageable) {
		Page<Eqmt> emtcpage = emddao.findSearch(deviceName, pageable);
        return emtcpage;
	}


	@Override
	@CachePut(key="'eqm_'+#machineName")
	public boolean update(Eqmt eqmt) {
		boolean flag = false;
		try {
			eqmt	= emddao.saveAndFlush(eqmt);
			if (null!=eqmt) {
				 flag = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


	@Override
	@Cacheable(key="'eqmttt_'+#Status")	
	public ResultPage<Eqmt> findEqmt(String repairman, int page, int size) throws BusinessRuntimeException {
		ResultPage<Eqmt> ro=new ResultPage<Eqmt>();
		PageRequest pageable = new PageRequest(page-1,size);

		List<Eqmt> pagelist=emddao.findAllByPage(repairman, (page-1)*size, size);
		int count=emddao.countAllData(repairman);
		 if (pagelist!=null) {

	        	 ro.setData(pagelist);
	    	     ro.setTotal(count);
	    	     ro.setTotalPages(PageUtil.getTotalPages(count, size));
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw  new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		
		return ro;
	}


	@Override
	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<Eqmt> addEqmt(Eqmt eqpmt) throws BusinessRuntimeException {
		ResultObject<Eqmt> ro=new ResultObject<>();
		if(Long.valueOf(eqpmt.getId())==null||eqpmt.getId()==0) {
			eqpmt.setCreateDate(new Date());
			
		}				
		eqpmt.setEnabled(true);
		Eqmt ars=emddao.saveAndFlush(eqpmt);
		 if(ars!=null) {
			   ro.setSuccess(true);
				ro.setMsg("操作成功");;
		   }else {
			   ro.setSuccess(false);
			   ro.setMsg("操作失败");
			throw   new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
		
		return ro;
	}


	@Override
	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<Eqmt> deletEqmt(long id) throws BusinessRuntimeException {
		ResultObject<Eqmt> ro=new ResultObject<>(); 
		Eqmt acris=emddao.findById(id);
		 if (acris!=null) {
			 acris.setEnabled(false);
			 emddao.saveAndFlush(acris);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		throw	new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}
	
}
