package com.zt.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Inform;
import com.zt.po.Meeting;

/**
 * @author wl
 * @date 2019年4月15日
 */
public interface MeetingService {
	/*
	 * 根据主题查询会议记录
	 * 
	 * ResultPage<Meeting> findBySubject(String subject);
	 */

	/*
	 * 分页模糊条件查询
	 */
	public ResultPage<Meeting> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;
	/*
	 * 修改会议记录
	 */
	public ResultObject<Meeting> saveMeeting(Meeting meeting)throws BusinessRuntimeException;
	/*
	 * 删除
	 */
	public ResultObject<Meeting> deleteMee(long id)throws BusinessRuntimeException;
}
