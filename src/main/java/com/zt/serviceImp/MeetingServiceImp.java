/**
 * 
 */
package com.zt.serviceImp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.zt.dao.MeetingDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Meeting;
import com.zt.service.MeetingService;

/**
 * @author wl
 * @date 2019年4月15日 客户信息业务层的实现类
 */
@Service("meetingService")
@CacheConfig(cacheNames = "zt_caches_meeting")
public class MeetingServiceImp implements MeetingService {

	@Autowired
	private MeetingDao meetingDao;

	/*
	 * 根据主题查询会议记录
	 * 
	 * @Override public ResultPage<Meeting> findBySubject(String subject) {
	 * ResultPage<Meeting> mtrg = new ResultPage<>(); Page<Meeting> pm =
	 * meetingDao.findBySubject(subject); mtrg.setData(pm.getContent()); return
	 * mtrg; }
	 */
	/*
	 * 分页模糊条件查询
	 */
	@Override
	@Cacheable(key="'mee_'+#name+'_'+#pageable")
	public ResultPage<Meeting> findSearch(String queryName, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<Meeting> ro=new ResultPage<Meeting>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<Meeting> pages = meetingDao.findSearch(queryName,pageable);
		
		 if (pages!=null) {
	        	int totals=(int) pages.getTotalElements();
	        	 ro.setData(pages.getContent());
	    	     ro.setTotal(totals);
	    	     ro.setTotalPages(pages.getTotalPages());
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		return ro;
	}
	/*
	 * 修改会议记录
	 */
	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<Meeting> saveMeeting(Meeting meeting) {
		ResultObject<Meeting> ro=new ResultObject<>();
		if(Long.valueOf(meeting.getId())==null||meeting.getId()==0) {
			meeting.setCreateDate(new Date());
		}	
		meeting.setEnabled(true);
		Meeting pro = meetingDao.saveAndFlush(meeting);
		if (pro!=null) {
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		}
		return ro;
	}
	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<Meeting> deleteMee(long id) throws BusinessRuntimeException {
		ResultObject<Meeting> ro=new ResultObject<>(); 
		Meeting pos=meetingDao.findById(id);
		 if (pos!=null) {
			pos.setEnabled(false);
			meetingDao.saveAndFlush(pos);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}

}
