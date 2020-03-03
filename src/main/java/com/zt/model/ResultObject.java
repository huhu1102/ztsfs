package com.zt.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ResultObject<T> implements Serializable {
	/**
	 * 前台后台方便操作  封装数据的类
	 */
	private static final long serialVersionUID = 1L;
	private boolean success;
	private List<T> data;
	private long count;
	private String msg;
	private T t;
	private Map<String,?> root;
	public ResultObject() {

	}

	public ResultObject(boolean succ, List<T> data) {
		this.success = succ;
		this.data = data;
	}

	public ResultObject(boolean succ, List<T> data, long count) {
		this.success = succ;
		this.data = data;
		this.count = count;
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

	public void setData(List<T> list) {
		this.data = list;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the root
	 */
	public Map<String, ?> getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(Map<String, ?> root) {
		this.root = root;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}



}
