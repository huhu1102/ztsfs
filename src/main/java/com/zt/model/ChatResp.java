/**  
* 
*/  
 
package com.zt.model;


import com.zt.po.BasePo;

/**
 * @author whl
 * @date 2019年5月20日 
 *  聊天消息返回 
 */
public class ChatResp extends BasePo{
	private static final long serialVersionUID = 1L;
	 private String msg;
     private String from;
	  public ChatResp(String msg, String from) {
        this.msg = msg;
        this.from = from;
    }
	   public ChatResp() {
	    }
	/**
	 * @return the id
	 */
//	public long getId() {
//		return id;
//	}
//	/**
//	 * @param id the id to set
//	 */
//	public void setId(long id) {
//		this.id = id;
//	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	@Override
	public String toString() {
		return "ChatResp [msg=" + msg + ", from=" + from + "]";
	}

}
