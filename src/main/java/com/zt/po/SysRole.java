/**  
* 
*/  
 
package com.zt.po;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * @author whl
 * @date 2019年4月17日 
    *  系统管理权限  设置超级管理员权限用
 */
@Entity
@Table(name="zt_sysrole")
@org.hibernate.annotations.Table(appliesTo = "zt_sysrole",comment="系统管理权限 ")
public class SysRole extends BasePo {
	 private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    @Column(columnDefinition ="varchar(100) COMMENT '角色名称'" )
	    private String name;//ROLE_ 开始
	    @Column(columnDefinition ="varchar(255) COMMENT '中文名称'" )
	    private String roleName;//

		@ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
		@JoinTable(name = "menu_sysrole", joinColumns = {
				@JoinColumn(name = "sysroleId", referencedColumnName = "ID")}, inverseJoinColumns = {
				@JoinColumn(name = "menuId", referencedColumnName = "ID")})
		private List<Menu> sysMenu;
	    @JsonIgnore
	    @ManyToMany(mappedBy="roles",fetch = FetchType.LAZY)
	    private List<Users> users;
	    
	  //是否可用
	    @JsonIgnore
		@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
		private boolean enabled;
	    
		/**
		 * @return the id
		 */
		public Integer getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
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
		 * @return the sysMenu
		 */
		public List<Menu> getSysMenu() {
			return sysMenu;
		}
		/**
		 * @param sysMenu the sysMenu to set
		 */
		public void setSysMenu(List<Menu> sysMenu) {
			this.sysMenu = sysMenu;
		}
		/**
		 * @return the users
		 */
		public List<Users> getUsers() {
			return users;
		}
		/**
		 * @param users the users to set
		 */
		public void setUsers(List<Users> users) {
			this.users = users;
		}
		/**
		 * @param id
		 * @param name
		 * @param sysMenu
		 * @param users
		 */
		public SysRole(Integer id, String name,String roleName, List<Menu> sysMenu, List<Users> users) {
			super();
			this.id = id;
			this.name = name;
			this.sysMenu = sysMenu;
			this.users = users;
			this.roleName=roleName;
		}
		/**
		 * 
		 */
		public SysRole() {
			super();
			// TODO Auto-generated constructor stub
		}
		/**
		 * @return the roleName
		 */
		public String getRoleName() {
			return roleName;
		}
		/**
		 * @param roleName the roleName to set
		 */
		public void setRoleName(String roleName) {
			this.roleName = roleName;
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
		@Override
		public String toString() {
			return "SysRole [id=" + id + ", name=" + name + ", roleName=" + roleName + ",   enabled=" + enabled + "]";
		}
		
	
}
