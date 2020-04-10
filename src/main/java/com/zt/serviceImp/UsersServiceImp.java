/**  
* 
*/  
 
package com.zt.serviceImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


import com.zt.po.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.zt.common.UsersUtils;
import com.zt.dao.EmployeeDao;
import com.zt.dao.MenuDao;
import com.zt.dao.SysRoleseDao;
import com.zt.dao.UsersDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ProgressModel;
//import com.zt.model.MenuModel;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Employee;
import com.zt.po.SysRole;
import com.zt.po.Users;
import com.zt.service.UsersService;
import com.zt.vo.MateModel;
import com.zt.vo.MenuModel;
import com.zt.vo.MenuViewModel;
import com.zt.vo.UsersModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author whl
 * @date 2019年5月5日
 *  
 */
@Service("userService")
@CacheConfig(cacheNames = "zt_caches_users")
public class UsersServiceImp implements UsersService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());  
	@Autowired(required=true)
	UsersDao userdao; 
	@Autowired
	MenuDao menudao; 
	@Autowired
	SysRoleseDao roleDao; 
	@Autowired
	EmployeeDao empdao; 
	private BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();	
	/*
	 * 登录
	 */
	@Override
	public ResultObject<Users> Login(String username, String password) {
		
		ResultObject<Users> ro=new ResultObject<Users>();
		Map<String, Users> map=new HashMap<String, Users>();
		Users  emp=  userdao.findUserByname(username);
		
		if (emp==null) {
			ro.setSuccess(false);
			ro.setMsg("用户名错误！");
			
			throw new BusinessRuntimeException(ResultCode.USERNAME_ERROR);
		}else if(emp.getPassword().equals(password)) {
			map.put("user", emp);
			ro.setRoot(map);
			 ro.setSuccess(true);
			ro.setMsg("登录成功");;
		}else {
			ro.setSuccess(false);
			ro.setMsg("密码错误！");;
			throw new BusinessRuntimeException(ResultCode.USERNAME_ERROR);
		}
		 return ro;
	}
	@Override
	@Cacheable(key ="#root.methodName")
	public ResultObject<Users> getMenu() throws BusinessRuntimeException{
	ResultObject<Users> ro=new ResultObject<Users>();		
		Map<String, Object> map=new HashMap<String,Object>();
			long userId=UsersUtils.getCurrentHr().getId();
			//角色id
		Set<Menu> list=menudao.findAllmenu(userId);
		if(list!=null) {
			if(list.isEmpty()){
				ro.setMsg("暂无功能！");
			}else{
				ro.setMsg("查询成功");
			}
			map.put("menuData", list);
			ro.setRoot(map);	
			ro.setSuccess(true);
		}else {
			ro.setSuccess(false);
			ro.setMsg("查询失败");
			throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
		}
		return ro;
	}
	@Override
//	 @Cacheable(key ="#root.methodName")
	public Users loadUserByUsername(String username) throws Exception {
		logger.info("username"+username);
	Users user=	userdao.findUserByname(username);

	    List<SysRole> rlist=user.getRoles();
	    System.out.println(rlist.toString());
		return user;
	}
	@Override
	public List<Users> getAllUsersExceptAdmin(long userId) throws BusinessRuntimeException {
		List<Users> list=userdao.getAllUsersExcept(userId)						;
		if (list!=null) {}
		    else {
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		return list;
	}
	@Override
	public ResultPage<Users> findbypages(String queryName, int page, int size) throws BusinessRuntimeException {
		ResultPage<Users> ro=new ResultPage<Users>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<Users> pages = userdao.findbypages(queryName,pageable);
//		List<Users>list=pages.getContent();
//		
//		for (Users users :list) {
//			users.setPassword(encoder.de);
//		}
		 if (pages!=null) {
	        	int totals=(int) pages.getTotalElements();
	        	 ro.setData(pages.getContent());
	    	     ro.setTotal(totals);
	    	     ro.setTotalPages(pages.getTotalPages());
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		return ro;
	
	}
	@Override
	public ResultObject<Users> save(UsersModel user) throws BusinessRuntimeException {
		ResultObject<Users> ro=new ResultObject<>();
		logger.info(user.toString());
		List<SysRole> rolelist=getRolelist(user);
		Users  u=new Users();
		if(Long.valueOf(user.getId())==null) {
		}
		u.setEmpId(user.getEmpId());
		u.setEmpName(user.getEmpName());
		u.setEnabled(true);
        u.setPassword(encoder.encode(user.getPassword().trim()));    
        u.setPhone(user.getPhone());
        u.setRoles(rolelist);
        if (user.getUserface()!=null) {
        	u.setUserface(user.getUserface());
		}else {
			u.setUserface("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514093920321&di=913e88c23f382933ef430024afd9128a&imgtype=0&src=http%3A%2F%2Fp.3761.com%2Fpic%2F9771429316733.jpg");
		}
        u.setUsername(user.getUsername());
		u= userdao.saveAndFlush(u);
		 if(u!=null) {
			 List<Users> userlist=new ArrayList<Users>();
			 for (SysRole sysRole : rolelist) {
				 sysRole.setUsers(userlist);
			 }
			 rolelist=roleDao.saveAll(rolelist);
			 if (rolelist!=null) {
				 ro.setSuccess(true);
				 ro.setMsg("操作成功");
			   }else {
				   ro.setSuccess(false);
				   ro.setMsg("角色添加失败");
			   }
		   }else {
			   ro.setSuccess(false);
			   ro.setMsg("操作失败");
			   throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
		
		return ro;
	}
	
	/**
	 *  更新方法
	 */
	@Override
	public ResultObject<Users> update(UsersModel user) throws BusinessRuntimeException {
		ResultObject<Users> ro=new ResultObject<>();
		logger.info(user.toString());
		List<Integer> newrolelist=user.getRoles();
		Users  u=userdao.findById(user.getId());
		 if (u!=null) {
//			 Users u=us.get();
			 //
			 List<SysRole> oldrolelist=u.getRoles();
			 Set<SysRole> oldlist=new HashSet<>(oldrolelist);
			 List<Integer>ood=new ArrayList<Integer>();
//			 for (int i = 0,k=oldrolelist.size(); i <k; i++) {
//				 SysRole sys= oldrolelist.get(i);
//				 ood.add(sys.getId());
//			}
		     for (SysRole sys : oldlist) {
		    	 ood.add(sys.getId());
			}
			 List<Integer> copyarr=new ArrayList<Integer>();
			 copyarr.addAll(ood);
			  ood.removeAll(newrolelist);
			  if (ood.size()>0) {
				//移除  u-r 关系
				  List<SysRole> oodlist=  roleDao.findbyIds(ood);
				  for (SysRole sysRole : oodlist) {
					  oldlist.remove(sysRole);
				}
			}
			  //维护新/旧 关系
//			  获取无重复数组
			  newrolelist.removeAll(copyarr);
			  if (newrolelist.size()>0) {
				  List<SysRole> rolist=roleDao.findbyIds(newrolelist);
				  oldlist.addAll(rolist);
			}
			  List<SysRole> restlist=new ArrayList<SysRole>(oldlist);
			 u.setRoles(restlist);
			 u.setEmpId(user.getEmpId());
			 u.setEmpName(user.getEmpName().trim());
			 u.setEnabled(true);
			 u.setPassword(encoder.encode(user.getPassword().trim()));    
			 u.setPhone(user.getPhone());
			 u.setUserface(user.getUserface());
			 u.setUsername(user.getUsername());
			 u= userdao.saveAndFlush(u);
			 if(u!=null) {
//				 for (int i = 0,n=rolelist.size(); i < n; i++) {
//					 SysRole sysRole=rolelist.get(i);
//					 boolean finduser=false;
//					 List<Users>ulist=sysRole.getUsers();
//					 
//					 for (int j = 0,m=ulist.size(); j < m; j++) {
//						Users use=ulist.get(j);
//						if (use) {
//							
//						}
//					}
//				}
				 
				 ro.setSuccess(true);
				 ro.setMsg("操作成功");
			 }else {
				 ro.setSuccess(false);
				 ro.setMsg("操作失败");
				 throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
			 }
		}else {
			ro.setSuccess(false);
			ro.setMsg("更新失败");
		} 
		return ro;
	}
	/**
	 * @param user
	 * @return
	 */
	private List<SysRole> getRolelist(UsersModel user) {
		List<SysRole> list=roleDao.findbyIds(user.getRoles());
		   return list;
	}
	@Override
	@Transactional
	public ResultObject<Users> delete(long id) throws BusinessRuntimeException {
		ResultObject<Users> ro=new ResultObject<Users>();
		boolean u= userdao.updateUser(id);
		if (u) {
//			//查找对应的员工信息，也删除
//			long empId = userdao.findById(id).getEmpId();
//			if (empId>0) {
//				empdao.updateEnabled(empId);
//
//			}
			ro.setSuccess(true);
			ro.setMsg("操作成功！");

		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
			throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
		}
		return ro;
	}
	@Override
	public ResultObject<Users> getBaseData() throws BusinessRuntimeException {
		ResultObject<Users> ro=new ResultObject<Users>();
		Map<String, Object>  map=new  HashMap<>();
		   List<SysRole> list= roleDao.findAll();
		   List<Employee> elist=empdao.findAll();
		  if (list!=null) {
			  map.put("roles", list);
			  map.put("emps", elist);
			  ro.setRoot(map);
			  ro.setSuccess(true);
		}else {
			ro.setSuccess(false);
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}		
		return ro;
	}
	
}
