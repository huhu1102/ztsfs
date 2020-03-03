/**  
* 
*/  
 
package com.zt.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author whl
 * @date 2019年5月20日 
   * 消息内容	
 */
 @Entity
 @Table(name="zt_msgcontent")
 @org.hibernate.annotations.Table(appliesTo = "zt_msgcontent",comment="消息内容")
public class MsgContent extends BasePo{
	   
	private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		/**
		 * 消息内容	
		 */
		@Column(columnDefinition ="varchar(255)  COMMENT '消息内容.'" )
	    private String message;
	    /**
	          * 消息标题
	     */
		@Column(columnDefinition ="varchar(255)  COMMENT '标题.'" )
	    private String title;
		/**
         * 发送消息人
         */
		@Column(columnDefinition ="varchar(255)  COMMENT '发送消息人.'" )
	    private String username;
		
		/**
         * 发送消息人id
         */
		@Column(columnDefinition ="bigint(20)  COMMENT '发送消息人id.'" )
	    private Long userId;
		
		/**
         * 撤销人id
         */
		@Column(columnDefinition ="bigint(20)  COMMENT '撤销人id.'" )
	    private Long backoutId;
		
		/**
         * 撤销人名字
         */
		@Column(columnDefinition ="varchar(255)  COMMENT '发送消息人.'" )
	    private String backoutName;
		  /**
	     * 撤销时间
	     */
		@Column(columnDefinition ="varchar(255)  COMMENT '撤销时间.'" )
	    private Date backoutDate;
		
		
		/**
         * 类型id
         * 消息类型  示是做什么操作发送的消息的Id
         * eg. MarketingPlan  对应MarketingPlan实体的ID
         */
		@Column(columnDefinition ="bigint(20)  COMMENT '类型id.'" )
	    private Long msgStyleId;
		
		/**
         * 消息类型  表示是做什么操作发送的消息
         *  eg. MarketingPlan  对应MarketingPlan实体
         */
		@Column(columnDefinition ="varchar(255)  COMMENT '消息类型.'" )
	    private String msgStyle; 
	     /**
	     * 消息发布时间
	     */
		@Column(columnDefinition ="varchar(255)  COMMENT '消息发布时间.'" )
	    private Date createDate;
		/**
		 * @return the id
		 */
		public Long getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(Long id) {
			this.id = id;
		}
		
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		/**
		 * @return the createDate
		 */
		public Date getCreateDate() {
			return createDate;
		}
		/**
		 * @param createDate the createDate to set
		 */
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
		/**
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}
		/**
		 * @param username the username to set
		 */
		public void setUsername(String username) {
			this.username = username;
		}

	
	
		public String getBackoutName() {
			return backoutName;
		}
		public void setBackoutName(String backoutName) {
			this.backoutName = backoutName;
		}
		public Date getBackoutDate() {
			return backoutDate;
		}
		public void setBackoutDate(Date backoutDate) {
			this.backoutDate = backoutDate;
		}




		public String getMsgStyle() {
			return msgStyle;
		}
		public void setMsgStyle(String msgStyle) {
			this.msgStyle = msgStyle;
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
		 * @return the backoutId
		 */
		public Long getBackoutId() {
			return backoutId;
		}
		/**
		 * @param backoutId the backoutId to set
		 */
		public void setBackoutId(Long backoutId) {
			this.backoutId = backoutId;
		}
		/**
		 * @return the msgStyleId
		 */
		public Long getMsgStyleId() {
			return msgStyleId;
		}
		/**
		 * @param msgStyleId the msgStyleId to set
		 */
		public void setMsgStyleId(Long msgStyleId) {
			this.msgStyleId = msgStyleId;
		}
	    
}
