/**  
* 
*/  
 
package com.zt.vo;


import com.zt.po.BasePo;

import java.util.List;


/**
 * @author whl
 * @date 2019年5月14日 
 *  menumodel
 */
public class MenuModel  extends   BasePo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String path;
	private String component;
	private String name;
	private String iconCls;
	private List<MenuModel>children;
	private MateModel mate;
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
	public String getComponent() {
		return component;
	}
	/**
	 * @param component the component to set
	 */
	public void setComponent(String component) {
		this.component = component;
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
	 * @return the children
	 */
	public List<MenuModel> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<MenuModel> children) {
		this.children = children;
	}
	/**
	 * @return the mate
	 */
	public MateModel getMate() {
		return mate;
	}
	/**
	 * @param mate the mate to set
	 */
	public void setMate(MateModel mate) {
		this.mate = mate;
	}

	
}
