package com.zt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Inform;
import com.zt.po.Meeting;
import com.zt.po.Position;
import com.zt.service.MeetingService;

/**
 * @author wl
 * @date 2019年4月16日
 */
@RestController
@RequestMapping("/meeting")
public class MeetingController {
	@Autowired
	private MeetingService meetingService;

	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value = "/findbypage", method = RequestMethod.GET)
	public ResultPage<Meeting> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return meetingService.findSearch(queryName, page,size);
	}
	/*
	 * 修改会议记录
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<Meeting> saveEmp(Meeting meeting){
			return meetingService.saveMeeting(meeting);  
	  }	
	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<Meeting> deletEmp(long id){
	     return meetingService.deleteMee(id);
	  }
}
