/**  
 * 
 */  

package com.zt.po;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author whl
 * @date 2019年4月16日
 * 组件类 该类主要做为权限划分用 
 */
@Entity
@Table(name="zt_menu")
@org.hibernate.annotations.Table(appliesTo = "zt_menu",comment="菜单")
public class Menu extends BasePo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/**
	 * 路径
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '路径.'" )
	private String url;
	/**
	 * 组件路径
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '组件路径.'" )
	private String path;
	/**
	 * 组件名
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '组件名.'" )
	private String component;
	/**
	 * 组件页面名
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '组件页面名.'" )
	private String name;
	/**
	 * 组件图标
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '组件图标.'" )
	private String iconCls;


	@JsonIgnore
	@ManyToMany(mappedBy="sysMenu",fetch = FetchType.EAGER)
	private List<SysRole> sysRole;
	/**
	 * 父组件Id
	 */
	@Column(columnDefinition ="bigint(20)  COMMENT '父组件Id.'" )
	private Long parentId;
	
	/**
	 * 父组件
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="pid")
	private Menu parentMenu;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="parentMenu")
	private List<Menu> children;
	
	@Column(columnDefinition="TINYINT(1) COMMENT '菜单是缓存.'")
	private boolean keepAlive;
  	@Column(columnDefinition="TINYINT(1) COMMENT '是否有资源权限.'")
	private boolean requireAuth;
	/**
	 * 1--不显示的组件
	 * 2--组件条
	 * 3--操作项（CURD）
	 */
	private Integer isbtn;
	/**
	 *  按钮文字
	 */
	private String  btnText;
  	/**
  	 *  是否可用
  	 */
  	private boolean enabled;

	public Integer getIsbtn() {
		return isbtn;
	}

	public void setIsbtn(Integer isbtn) {
		this.isbtn = isbtn;
	}

	public String getBtnText() {
		return btnText;
	}

	public void setBtnText(String btnText) {
		this.btnText = btnText;
	}

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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the component
	 */
	public Object getComponent() {
		return component;
	}
	/**
	 * @param component the component to set
	 */
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
	 * @return the iconCls
	 */
	public String getIconCls() {
		return iconCls;
	}
	/**
	 * @param iconCls the iconCls to set
	 */
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the roles
	 */
	
	/**
	 * @return the children
	 */
	public List<Menu> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	/**
	 * @return the parentMenu
	 */
	public Menu getParentMenu() {
		return parentMenu;
	}
	/**
	 * @param parentMenu the parentMenu to set
	 */
	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	/**
	 * @param component the component to set
	 */
	public void setComponent(String component) {
		this.component = component;
	}
    
	public Menu() {
		super();
		this.sysRole = new ArrayList<SysRole>();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the sysRole
	 */
	public List<SysRole> getSysRole() {
		return sysRole;
	}
	/**
	 * @param sysRole the sysRole to set
	 */
	public void setSysRole(List<SysRole> sysRole) {
		this.sysRole = sysRole;
	}
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
//	@Override
//	public String toString() {
//		return "Menu [id=" + id + ", url=" + url + ", path=" + path + ", component=" + component + ", name=" + name
//				+ ", iconCls=" + iconCls + ", sysRole=" + sysRole + ", parentId=" + parentId + ", parentMenu="
//				+ parentMenu + ", children=" + children + ", keepAlive=" + keepAlive + ", requireAuth=" + requireAuth
//				+ ", enabled=" + enabled + "]";
//	}
	
   
}
