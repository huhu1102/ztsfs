/**
*
*/

package com.zt.serviceImp;


import java.util.*;

import com.zt.dao.MenuDao;
import com.zt.po.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zt.dao.SysRoleseDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Product;
import com.zt.po.ReturnVisit;
import com.zt.po.SysRole;
import com.zt.service.SysRoleService;
import org.springframework.util.CollectionUtils;

/**
 * @author whl
 * @date 2019年5月20日
 */
@Service(value="sysrole")
@Transactional
@CacheConfig(cacheNames = "zt_caches_sysroles")
public class SysRoleServiceImp  implements SysRoleService{
	@Autowired
	SysRoleseDao  sysRoleDao;
    @Autowired
	MenuDao  menuDao;
	@Override
	public ResultPage<SysRole> findbypages() throws BusinessRuntimeException {
		ResultPage<SysRole> ro=new ResultPage<>();
		List<SysRole> pages = sysRoleDao.findbypages();
		 if (pages!=null&&pages.size()>0) {
	        	int totals=pages.size();
	        	 ro.setData(pages);
	    	     ro.setTotal(totals);
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		return ro;
	}

	@Override
	public ResultObject<SysRole> save(SysRole sysRole) throws BusinessRuntimeException {
		ResultObject<SysRole> ro=new ResultObject<>();
		if(sysRole.getId()==null) {
		}
		sysRole.setSysMenu(null);
		sysRole.setUsers(null);
		sysRole.setEnabled(true);
		sysRole= sysRoleDao.saveAndFlush(sysRole);
		 if(sysRole!=null&&sysRole.getId()!=0) {
			   ro.setSuccess(true);
			   ro.setMsg("操作成功");
		   }else {
			   ro.setSuccess(false);
			   ro.setMsg("操作失败");
			   throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }

		return ro;
	}
	@Override
	public ResultObject<SysRole> delete(Integer id) throws BusinessRuntimeException {
		ResultObject<SysRole> ro=new ResultObject<SysRole>();
		SysRole role= sysRoleDao.findByRoleId(id);
		if (role!=null&&role.getId()!=0) {
			role.setEnabled(false);
			role=sysRoleDao.saveAndFlush(role);
			if (role!=null) {
				ro.setSuccess(true);
				ro.setMsg("操作成功！");



			}else {
				ro.setSuccess(false);
				ro.setMsg("操作失败");
			}
		}else {
			ro.setSuccess(false);
			throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
		}


		return ro;
	}

	@Override
	public ResultObject<Menu> findMenus(long roleId) throws BusinessRuntimeException {
		ResultObject<Menu> ro=new ResultObject<>();

		Set<Menu> menus=menuDao.findAllobyrole();
		List<Menu> list= new ArrayList<>(menus);
        Set<Long> menuIds=menuDao.findAllmenus(roleId);
		Map<String,Set<Long>>map=new HashMap<>();
		map.put("menus",menuIds);
		if(!menus.isEmpty()){
			ro.setSuccess(true);
			ro.setRoot(map);
			ro.setData(list);
		}else{
			ro.setSuccess(false);
			throw new  BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
		}
		return ro;
	}

	/**
	 *  权限组更新
	 * @param rid
	 * @param mids
	 * @return
	 */

	@Override
	@Transactional
	public ResultObject<Menu> updateMenuRole(Integer rid, Long[] mids) {
		ResultObject ro=new ResultObject();
		List<Long>  arrayList = new ArrayList<Long>(Arrays.asList(mids));
		//判断参数是否空数组，如果是空数组给集合中添加一个0，以解决HQL IN方法报错。
		if(CollectionUtils.isEmpty(arrayList))
			arrayList.add(0l);
		menuDao.deleteMids(rid);

        SysRole sysRole=sysRoleDao.findByRoleId(rid);
        List<Menu> menuList=menuDao.findAllByMiId(arrayList);
		sysRole.setSysMenu(menuList);
		sysRole=sysRoleDao.saveAndFlush(sysRole);
         if (sysRole!=null){
         	ro.setSuccess(true);
         	ro.setMsg("更新成功");
		 }  else{
         	ro.setSuccess(false);
         	ro.setMsg("更新失败！请联系管理员！");
		 }
		return ro;
	}
}
