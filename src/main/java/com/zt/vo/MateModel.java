/**  
* 
*/  
 
package com.zt.vo;

import com.zt.po.BasePo;

/**
 * @author whl
 * @date 2019年5月14日 
 */
public class MateModel  extends   BasePo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean keepAlive;
	private boolean requireAuth;
	/**
	 * @return the keepAlive
	 */
	public boolean isKeepAlive() {
		return keepAlive;
	}
	/**
	 * @param keepAlive the keepAlive to set
	 */
	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}
	/**
	 * @return the requireAuth
	 */
	public boolean isRequireAuth() {
		return requireAuth;
	}
	/**
	 * @param requireAuth the requireAuth to set
	 */
	public void setRequireAuth(boolean requireAuth) {
		this.requireAuth = requireAuth;
	}
	
}
