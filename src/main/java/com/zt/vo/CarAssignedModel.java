/**  
* 
*/  
 
package com.zt.vo;

import com.zt.po.BasePo;

/**
 * @author whl
 * @date 2019年5月24日
 *  车辆分配model 
 */
public class CarAssignedModel  extends   BasePo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//用车申请id
	private long  requestId;
	//车牌号
	private String carNo;
	//备注
	private String note;
	/**
	 * @return the requestId
	 */
	public long getRequestId() {
		return requestId;
	}
	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}
	/**
	 * @return the carNo
	 */
	public String getCarNo() {
		return carNo;
	}
	/**
	 * @param carNo the carNo to set
	 */
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	

}
