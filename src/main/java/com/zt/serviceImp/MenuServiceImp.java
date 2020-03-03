/**  
* 
*/  
 
package com.zt.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sound.midi.MidiDevice.Info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zt.common.UsersUtils;
import com.zt.dao.MenuDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.po.Menu;
import com.zt.service.MenuService;
import com.zt.vo.MenuModel;
import com.zt.vo.MenuViewModel;

/**
 * @author whl
 * @date 2019年4月17日 
 */
@Service("menuService")
@CacheConfig(cacheNames = "zt_caches_menu")
public class MenuServiceImp  implements MenuService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	 MenuDao menudao;
	 
	@Override
	public List<Menu> getAllMenu() {
		
		List<Menu> list=menudao.findAll();
		
		return list;
	}

	@Override
//	 @Cacheable(key = "#root.methodName")
	public List<Menu> getAllMenuData() throws BusinessRuntimeException {
		List<Menu> objlist=menudao.findAuthors();
//          List<MenuViewModel> list=new ArrayList<MenuViewModel>();
//         for (Object o : objlist) {
//        	 Object[] rowArray = (Object[]) o;
//        	 MenuViewModel view= new MenuViewModel();
//			 view.setId(Long.parseLong(String.valueOf(rowArray[0])));
//				 view.setComponent(String.valueOf(rowArray[1]));
//				 view.setIconCls(String.valueOf(rowArray[2]));
//				 view.setKeepAlive(Boolean.parseBoolean(rowArray[3].toString()) );
//				 view.setName(String.valueOf(rowArray[4]));
//				 view.setPath(String.valueOf(rowArray[5]));
//            	 view.setRequireAuth(Boolean.parseBoolean(String.valueOf(rowArray[6])) );
//				 view.setUrl(String.valueOf(rowArray[7]));
//				 view.setRoleName(String.valueOf(rowArray[10]));
//             list.add(view);
//
//		}
		return objlist;
	}
	 @Override
//	 @Cacheable(key = "#root.methodName")
	public Set<Menu> getAllMenuDataes() throws BusinessRuntimeException {
//	 Long	testUserId=1L;
		Set<Menu> objlist=menudao.findAllobyr(UsersUtils.getCurrentHr().getId());
		logger.info(objlist.toString());
		return objlist;
	}

}
