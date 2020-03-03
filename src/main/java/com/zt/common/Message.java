package com.zt.common;

import com.zt.constant.DepmentAndPosCode;
import com.zt.dao.DepartmentDao;
import com.zt.dao.EmployeeDao;
import com.zt.dao.UsersDao;
import com.zt.po.Department;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Message {
    @Autowired
    EmployeeDao empDao;
    @Autowired
    DepartmentDao depDao;
    @Autowired
    UsersDao usersDao;
    /*
     * 给谁发信息的id
     */
    public List<Long> UserIds(){
        long empId = UsersUtils.getCurrentHr().getEmpId();
        String depIds=empDao.findById(empId).getDepartmentIds();
         List<Long> deplist= strToArr(depIds);
         List<Department> depList= depDao.findAllbyIds(deplist);
        List<String> depNubmers=new ArrayList<>();
        for (int i = depList.size()-1; i>=0 ; i--) {
            depNubmers.add(depList.get(i).getDeNumber());
        }

//        long depId = empDao.findById(empId).getDepartmentId();
        //		 long posId = empDao.findById(empId).getPositionId();
        //获取部门的编号
//        String deNumber = depDao.findById(depId).getDeNumber();
        //获取职位编号
        List<Long> ids=new ArrayList<>();
        for (String num:depNubmers) {


        Set<Long> usersIds = usersDao.findUserIdByDepPro(num, DepmentAndPosCode.DIRECTOR,DepmentAndPosCode.VICE_DIRECTOR);
        Set<Long> usersIdAdminstration = usersDao.findUserIdByDepPro(DepmentAndPosCode.ADMINISTRATION_DEPARTMENT, DepmentAndPosCode.DIRECTOR,DepmentAndPosCode.VICE_DIRECTOR);
        usersIds.addAll(usersIdAdminstration);
        //获取userId
         ids = new ArrayList<>(usersIds);
        }
        return ids;
    }
    public   List<Long> strToArr(String ids){
        String[] depArr= ids.split(",");
        long  depIds[]= (long[]) ConvertUtils.convert(depArr,long.class);
        List<Long> slist=new ArrayList<>(depIds.length);
        for (int i=depIds.length-1;i>=0;i--) {
            slist.add(depIds[i]);
        }
        return slist;
    }

}
