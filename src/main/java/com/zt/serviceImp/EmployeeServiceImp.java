/**
 * 
 */
package com.zt.serviceImp;

import com.zt.dao.DepartmentDao;
import com.zt.dao.EmployeeDao;
import com.zt.dao.PositionDao;
import com.zt.dao.UsersDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Department;
import com.zt.po.Employee;
import com.zt.po.Position;
import com.zt.po.Users;
import com.zt.service.EmployeeService;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

/**
* @author wl
* @version 2019年4月13日 
* 员工信息业务层的实现类
*/
@Service("employeeService")
@CacheConfig(cacheNames = "zt_caches_employee")
public class EmployeeServiceImp implements EmployeeService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private DepartmentDao departDao;
	@Autowired
	private PositionDao postDao;
	@Autowired
	private UsersDao usersDao;
//	@Autowired
//    private BCryptPasswordEncoder  passwordEncoder;
	/*
	 * 员工注册
	 */
	/**
	@Override
	public Employee addEmpolyee(Employee employee) {
		if (employee.getName() != null && employee.getPassword() != null&& employee.getAddress() !=null
				&&employee.getPhone() !=null){
			employee.setPassword(passwordEncoder.encode(employee.getPassword()));
			employee.setName(employee.getName());
			employee.setAddress(employee.getAddress());
			employee.setPhone(employee.getPhone());
			employeeDao.save(employee);
        }
        else
        	employee = null;
        return employee;
	}*/
	
	/*
	 * 修改员工信息
	 */
	/*
	 * @Override public boolean update(Employee employee) { boolean flag = false;
	 * try { employee = employeeDao.saveAndFlush(employee); if (null!=employee) {
	 * flag = true; }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return flag; }
	 */
	/*
	 * 分页无条件查询
	 */
	@Override
//	@Cacheable(key="'emp_'+#page+'_'+#size")
	public ResultPage<Employee> findbyPage(Integer page,Integer size)  {
		logger.info("我执行了"+page);
		ResultPage<Employee> rp=new ResultPage<>();
        Pageable pageable =  PageRequest.of(page-1, size);//new PageRequest(page,size,sortt,"createDate");
	    Page<Employee> pages=employeeDao.findAll(pageable);
        if (pages!=null&&pages.getContent()!=null) {
        	 rp.setData(pages.getContent());
    	     rp.setTotal(pages.getTotalElements());
    	     rp.setTotalPages(pages.getTotalPages());
    	     rp.setSuccess(true);
		} else {
			rp.setSuccess(false);
		 throw	 new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
		}
		return rp;
	}
	/**
	 * 分页模糊条件查询
	 */
	@Override
//	@Cacheable(key="'emp_'+#page+'_'+#size+'_'+#queryName")
	public ResultPage<Employee> findSearch(String queryName, int page, int size,String phone,String addr,String dept) throws BusinessRuntimeException{
		ResultPage<Employee> ro=new ResultPage<>();

		PageRequest pageable =PageRequest.of(page-1,size);
		Page<Employee> pages = employeeDao.findSearch(queryName,phone,addr,dept,pageable);
		
		 if (pages!=null&&pages.getContent()!=null) {
	        	 ro.setData(pages.getContent());
	    	     ro.setTotal(pages.getTotalElements());
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw  new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		
		return ro;
	}
	@Override
//	@Cacheable(key="'emp_'+#page+'_'+#size")
	public Employee loadUserByUsername(String username) throws Exception {
		Employee  emp= employeeDao.findEmployee(username);
		
		return emp;
	}
	 private  List<Long> strToArr(String ids){
         String[] depArr= ids.split(",");
         long  depIds[]= (long[]) ConvertUtils.convert(depArr,long.class);
         List<Long> slist=new ArrayList<>(depIds.length);
         for (int i=depIds.length-1;i>=0;i--) {
             slist.add(depIds[i]);
         }
         return slist;
     }
     private String arrToStr(List<Department> list){
	    StringBuilder  sb= new StringBuilder();
         for (int i = list.size()-1; i>=0 ; i--) {
             sb.append(list.get(i).getName());
             if(i!=1||i!=list.size()-1){
                 sb.append(",");
             }
         }
         return  sb.toString();
     }
    private String arrToPosStr(List<Position> polist){
        StringBuilder  sbpo= new StringBuilder();
        for (int i = polist.size()-1; i>=0 ; i--) {
            sbpo.append(polist.get(i).getName());
            if(i!=1||i!=polist.size()-1){
                sbpo.append(",");
         }
        }
        return  sbpo.toString();
    }
	/*
	 * 保存更新
	 */
	@Override
	@CacheEvict(allEntries=true)
	public ResultObject<Employee> saveEmpoyee(String deptIds, String postIds, Employee employee) {
		ResultObject<Employee> ro=new ResultObject<>();

		if (!((deptIds.trim().equals("")))){
          List<Long> deptList= strToArr(deptIds);
            List<Department> deptsList=departDao.findAllbyIds(deptList);
            employee.setDept(deptsList);
            employee.setDepartmentName(arrToStr(deptsList));
			employee.setDepartmentIds(postIds);
		}
		if (!(postIds.trim().equals(""))){
            List<Long> posList=strToArr(postIds);
            List<Position> poList=postDao.findbyIds(posList);
            employee.setPost(poList);
            employee.setPositionName(arrToPosStr(poList));
            employee.setPositionIds(postIds);
		}

        if(Long.valueOf(employee.getId()).equals("null")) {
			employee.setCreateDate(new Date());
		}
		employee.setEnabled(true);
//		Position position= postDao.findById(employee.getPositionId());
//		employee.setPositionName(position.getName());

		Employee emp=employeeDao.saveAndFlush(employee);
		 if(emp!=null&&emp.getId()>0) {
			   ro.setSuccess(true);
			   logger.info("保存成功");
				ro.setMsg("操作成功");

		   }else {
			   ro.setSuccess(false);
			   ro.setMsg("操作失败");
			throw   new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
		
		return ro;
	}
    @Override
    @Transactional
    public ResultObject<Employee> updateData(String deptIds, String postIds, Employee employee) throws BusinessRuntimeException {
        ResultObject<Employee> ro=new ResultObject<>();
        Employee  oldEmp=employeeDao.findById(employee.getId());
        if(!deptIds.trim().equals("")){
            //移除之前的关系
            int asd=employeeDao.deleteDepart(oldEmp.getId());
            //添加新的关系；
            List<Long> deptList= strToArr(deptIds);
            List<Department> deptsList=departDao.findAllbyIds(deptList);
            employee.setDept(deptsList);
            employee.setDepartmentName(arrToStr(deptsList));
            employee.setDepartmentIds(deptIds);
        }
        if (!(postIds.trim().equals(""))){
            //移除之前的关系
            int asd=employeeDao.deletePost(oldEmp.getId());
            //添加新的关系
            List<Long> posList=strToArr(postIds);
            List<Position> poList=postDao.findbyIds(posList);
            employee.setPost(poList);
            employee.setPositionName(arrToPosStr(poList));
            employee.setPositionIds(postIds);
        }
        employee.setEnabled(true);
        Employee emp=employeeDao.saveAndFlush(employee);
        if(emp!=null&&emp.getId()>0) {
            ro.setSuccess(true);
            logger.info("保存成功");
            ro.setMsg("成功!");

        }else {
            ro.setSuccess(false);
            ro.setMsg("操作失败");
            throw   new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }

        return ro;

    }
	/*
	 * 删除员工信息,软删除
	 */
	@Override
	@CacheEvict(allEntries=true)
	public ResultObject<Employee> deletEmp(long id) {
		ResultObject<Employee> ro=new ResultObject<>(); 
		Employee emp=employeeDao.findById(id);
		 if (emp!=null) {
			emp.setEnabled(false);
			employeeDao.saveAndFlush(emp);

//			//需要找到对应的用户并进行删除，无果没找到不用管
//			 Users byEmpId = usersDao.findByEmpId(id);
//			 if(byEmpId!=null){
//			 	usersDao.updateUser(byEmpId.getId());
//			 }
			 ro.setSuccess(true);
			ro.setMsg("成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		throw	new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}
	@Override
	public ResultObject<Object> getBaseData() {
		ResultObject<Object> ro=new ResultObject<>();
		Map<String, Object> map=new HashMap<>();
		List<Department> departmentlist=  departDao.findAllDep();
		List<Position> postlist = postDao.findallpost();
		 Set<Object> deptlist=departDao.findAlldeps();
		if (departmentlist==null) {
			ro.setSuccess(false);
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}else {
			map.put("department", departmentlist);
			map.put("position", postlist);
			map.put("deptlist", deptlist);
			ro.setRoot(map);
			ro.setSuccess(true);
		}
		
			return ro;
	}
	@Override
	public ResultObject<Object> uniqeTel(String telphone)  {
		ResultObject<Object>ro =new ResultObject<>();


		List<Employee> elist=employeeDao.getTel(telphone);
		if(elist!=null) {
			if (elist.size()>0) {
				ro.setSuccess(false);
				ro.setMsg("号码已存在");
			}else {
				ro.setSuccess(true);
			}
		}else {
			ro.setSuccess(false);
			ro.setMsg("未知错误");
			throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
		}   
		return ro;
	}
	@Override
	public ResultObject<Object> currentNo() throws BusinessRuntimeException {
		ResultObject<Object>ro=new ResultObject<Object>();
		String workId="0001";
		Long maxId= employeeDao.findCurrentNo();
		if(maxId!=null)  {
			maxId+=1;
			workId=String.format("%05d", maxId);
		}
		    ro.setMsg(workId);
		return ro;
	}


}
