/**  
* 
*/  
 
package com.zt.service;

import java.util.List;
import java.util.Set;

import com.zt.model.BusinessRuntimeException;
import com.zt.po.Menu;
import com.zt.vo.MenuViewModel;


/**
 * @author whl
 * @date 2019年4月17日 
 */
public interface MenuService {
  public List<Menu> getAllMenu();

/**
 * @return
 */
public List<Menu> getAllMenuData()throws BusinessRuntimeException;
public Set<Menu> getAllMenuDataes()throws BusinessRuntimeException;
}
