/**
 * 
 */
package com.zt.serviceImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.common.MessageUtil;
import com.zt.common.PageUtil;
import com.zt.common.UsersUtils;
import com.zt.constant.DepmentAndPosCode;
import com.zt.dao.BiddingNoticeDao;
import com.zt.dao.DepartmentDao;
import com.zt.dao.EmployeeDao;
import com.zt.dao.MsgContentDao;
import com.zt.dao.PositionDao;
import com.zt.dao.UsersDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.BiddingNotice;
import com.zt.service.BiddingNoticeService;

/**
 * @author yh
 * @date 2019年5月10日
 */
@Service("tenderNoticeService")
@CacheConfig(cacheNames = "zt_caches_bidding_announcement")
public class BiddingNoticeServiceImpl implements BiddingNoticeService {
	@Autowired
	private BiddingNoticeDao bido;
	@Autowired
	 MessageUtil msgutil;
	@Autowired
	MsgContentDao  msgctd;
	@Autowired
	DepartmentDao depDao;
	@Autowired
	PositionDao posDao;
	@Autowired
	EmployeeDao empDao;
	@Autowired
	UsersDao usersDao;
	
	@Override
	@Cacheable(key="'tendernotice_'+#Status")	
	public ResultPage<BiddingNotice> findTenderNotice(String tdInstitution, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<BiddingNotice> ro=new ResultPage<>();
		Pageable pageable = PageRequest.of(page-1,size);
//		Page<VehicleInfo> pages =vid.findSearch(queryName, pageable);
		List<BiddingNotice> pagelist=bido.findAllByPage(tdInstitution, (page-1)*size, size);
		int count=bido.countAllData(tdInstitution);
		 if (pagelist!=null) {
//	        	int totals=(int) pages.getTotalElements();
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
	public ResultObject<BiddingNotice> addTenderNotice(BiddingNotice big) throws BusinessRuntimeException {
		ResultObject<BiddingNotice> ro=new ResultObject<>();
		if(Long.valueOf(big.getId())==null||big.getId()==0) {
			big.setCreateDate(new Date());
			
		}				
		big.setEnabled(true);;
		BiddingNotice ars=bido.saveAndFlush(big);
		 if(ars!=null) {
			   ro.setSuccess(true);
				ro.setMsg("操作成功");;
				String title="招标公告";
				String content="";
				msgutil.sendMsg(title, content, "big", big.getId(), receiveIds());
		   }else {
			   ro.setSuccess(false);
			   ro.setMsg("操作失败");
			throw   new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
		
		return ro;
	}

	@Override
	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<BiddingNotice> deletTenderNotice(long id) throws BusinessRuntimeException {
		ResultObject<BiddingNotice> ro=new ResultObject<>(); 
		BiddingNotice acris=bido.findById(id);
		 if (acris!=null) {
			 acris.setEnabled(false);
			 bido.saveAndFlush(acris);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		throw	new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}

	public List<Long> receiveIds(){
		 //查询所有userIds
        Set<Long> ids = usersDao.findIds();
        List<Long> idList = new ArrayList<>(ids);
		 return idList;
	}
	

}
