/**
 * 
 */
package com.zt.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yh
 * @date 2019年5月7日
 */
//计量单位
@Entity
@Table(name="zt_sysunit")//
@org.hibernate.annotations.Table(appliesTo = "zt_sysunit",comment="计量单位")

public class SysUnit extends BasePo{
	private static final long serialVersionUID = 1L;
	//单位id
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		
	//规格
		@Column(columnDefinition ="varchar(255)  COMMENT '名称.'" )
		private String  name;
		
		@Column(columnDefinition ="varchar(255)  COMMENT '编码.'" )
		private String  code;
		
		@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
		@Temporal(TemporalType.DATE)
		private Date createDate;	
		private boolean enabled;
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
		 * @return the serialversionuid
		 */
		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the code
		 */
		public String getCode() {
			return code;
		}

		/**
		 * @param code the code to set
		 */
		public void setCode(String code) {
			this.code = code;
		}

		@Override
		public String toString() {
			return "SysUnit [id=" + id + ", name=" + name + ", code=" + code + "]";
		}

		/**
		 * @return the enabled
		 */
		public boolean isEnabled() {
			return enabled;
		}

		/**
		 * @param enabled the enabled to set
		 */
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
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

	    
		
		
}
