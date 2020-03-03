/**
 * 
 */
package com.zt.po;

import javax.persistence.*;


/**
 * @author yh
 * @date 2019年5月6日
   *  系统消息实体类
 * 
 */
@Entity
@Table(name="zt_sysmsg")//
@org.hibernate.annotations.Table(appliesTo = "zt_sysmsg",comment="系统消息")
public class SysMessage extends BasePo{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//消息外键
	@Column(columnDefinition="BIGINT(20)  COMMENT'消息外键'")
    private Long mid;
    
    /**
	 * 是否群发消息 0
     */
	@Column(columnDefinition="INT(5)  COMMENT'是否群发消息'")
    private Integer msgType;
    /**
      * 发消息给谁
     */
	@Column(columnDefinition="BIGINT(20)  COMMENT'发消息给谁'")
    private Long userId;
    /**
     * 消息状态 已读==2/未读==1 3撤销
     */
	@Column(columnDefinition="INT(5)  COMMENT'消息状态  已读/未读'")
    private Integer msgState;
    /**
	 * 消息内容
     * 
     */
	@ManyToOne
    private MsgContent msgContent;
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the mid
	 */
	public Long getMid() {
		return mid;
	}
	/**
	 * @param mid the mid to set
	 */
	public void setMid(Long mid) {
		this.mid = mid;
	}
	/**
	 * @return the msgType
	 */
	public Integer getMsgType() {
		return msgType;
	}
	/**
	 * @param msgType the msgType to set
	 */
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the msgState
	 */
	public Integer getMsgState() {
		return msgState;
	}
	/**
	 * @param msgState the msgState to set
	 */
	public void setMsgState(Integer msgState) {
		this.msgState = msgState;
	}
	/**
	 * @return the msgContent
	 */
	public MsgContent getMsgContent() {
		return msgContent;
	}
	/**
	 * @param msgContent the msgContent to set
	 */
	public void setMsgContent(MsgContent msgContent) {
		this.msgContent = msgContent;
	}
	
	
    
}
