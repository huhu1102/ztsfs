package com.zt.serviceImp;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.zt.dao.RoleseDao;
import com.zt.model.ResultPage;
import com.zt.po.Rolese;
import com.zt.service.RoleseService;

@Service("roleService")
@CacheConfig(cacheNames = "zt_caches_role")
public class RoleseServiceImp implements RoleseService{

	@Autowired
	 RoleseDao  roleDao;
	
	
	@Override
	@Cacheable(key="'rol_'+#page+'_'+#size")
	public ResultPage<Rolese> findbyPage(Integer page,Integer size ,String sort) {
		ResultPage<Rolese> rp=new ResultPage<>();
		Direction sortt=null;
		if (sort.equals("asdfa")&&sort!=null) {
		 	sortt=   Sort.Direction.DESC;
		}else {
			sortt=   Sort.Direction.ASC;
		}
        Pageable pageable = PageRequest.of(page,size,sortt,"createDate");
	    Page<Rolese> pages=roleDao.findAll(pageable);
        int totals=   (int) pages.getTotalElements();
        List<Rolese>rolelist= pages.getContent();
	     rp.setData(rolelist);
	     rp.setTotal(totals);
	     rp.setSuccess(true);
		return rp;
	}
  
	  
	 
}
