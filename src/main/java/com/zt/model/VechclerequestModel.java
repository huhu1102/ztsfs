/**
 * 
 */
package com.zt.model;

import java.util.Date;

/**
 * @author yh
 * @date 2019年5月22日
 */
	//车辆申请
	public class VechclerequestModel {
		//
		
		private  Date  stat;
		private  Date  end;
		private long requestId;
		private String carNuber;
		
		private String  empName;

		/**
		 * @return the requesttime
		 */
		
		/**
		 * @return the requestId
		 */
		public long getRequestId() {
			return requestId;
		}

		/**
		 * @return the stat
		 */
		public Date getStat() {
			return stat;
		}

		/**
		 * @param stat the stat to set
		 */
		public void setStat(Date stat) {
			this.stat = stat;
		}

		/**
		 * @return the end
		 */
		public Date getEnd() {
			return end;
		}

		/**
		 * @param end the end to set
		 */
		public void setEnd(Date end) {
			this.end = end;
		}

		/**
		 * @param requestId the requestId to set
		 */
		public void setRequestId(long requestId) {
			this.requestId = requestId;
		}

		/**
		 * @return the empName
		 */
		public String getEmpName() {
			return empName;
		}

		/**
		 * @param empName the empName to set
		 */
		public void setEmpName(String empName) {
			this.empName = empName;
		}

		/**
		 * @return the carNuber
		 */
		public String getCarNuber() {
			return carNuber;
		}

		/**
		 * @param carNuber the carNuber to set
		 */
		public void setCarNuber(String carNuber) {
			this.carNuber = carNuber;
		}
		
	}
