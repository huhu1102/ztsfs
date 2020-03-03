package com.zt.service;

import com.zt.model.ResultPage;
import com.zt.po.Rolese;
/**
 * @author whl
 * @date 2019年4月12日 
 */
public interface RoleseService {

  public  ResultPage<Rolese> findbyPage(Integer page,Integer size,String sort);
}
