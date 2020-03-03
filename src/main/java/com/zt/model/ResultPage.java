package com.zt.model;
/**
 * 
 */

import com.zt.po.BasePo;

import java.util.List;

/**
 * @author w
 * @param <T>
 *
 */
public class ResultPage<T> extends BasePo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long  page;//页码
	private long  pageSize;//页数
	private long  start;//开始
	private long  totalPages;//总页数
	private long  total;//总条数
	
	private boolean success;
	private List<T> data;
	public long  getPage() {
		return page;
	}
	public void setPage(long  page) {
		this.page = page;
	}
	public long  getPageSize() {
		return pageSize;
	}
	public void setPageSize(long  pageSize) {
		this.pageSize = pageSize;
	}
	public long  getStart() {
		return start;
	}
	public void setStart(long  start) {
		this.start = start;
	}
	public long  getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(long  totalPages) {
		this.totalPages = totalPages;
	}
	public long  getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
}
