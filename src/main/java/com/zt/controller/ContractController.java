package com.zt.controller;

import com.zt.model.ContractModel;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Contract;
import com.zt.service.ContractService;
import com.zt.vo.ContractVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    ContractService contractService;
    /*
     * 分页模糊条件查询
     */
    @RequestMapping(value = "/findbypage",	 method = RequestMethod.GET)
    public ResultPage<Contract> findbypage(String contractName,String clientName,String empName,String createDateStart,String createDateEnd,String startDateStart,String startDateEnd,String endDateStart,String endDateEnd,String signDateStart,
                                           String signDateEnd ,Integer status,int page, int size) {
        return contractService.findSearch(contractName,clientName,empName,createDateStart,createDateEnd,startDateStart,startDateEnd,endDateStart,endDateEnd,signDateStart, signDateEnd ,status, page,size);
    }

    /**
     * 分页模糊条件查询(新)
     * @param contractName
     * @param clientName
     * @param empName
     * @param createDateStart
     * @param createDateEnd
     * @param startDateStart
     * @param startDateEnd
     * @param endDateStart
     * @param endDateEnd
     * @param signDateStart
     * @param signDateEnd
     * @param status
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/listByPage",method = RequestMethod.GET)
    public ResultPage<ContractVO> listByPage(String contractName, String clientName, String empName, String createDateStart, String createDateEnd, String startDateStart, String startDateEnd, String endDateStart, String endDateEnd, String signDateStart,
                                             String signDateEnd , Integer status, int page, int size) {
        return contractService.listByPage(contractName,clientName,empName,createDateStart,createDateEnd,startDateStart,startDateEnd,endDateStart,endDateEnd,signDateStart, signDateEnd ,status, page,size);
    }

    /*
     * 新增
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public ResultObject<Contract> addProduct(ContractModel contractModel) throws Exception{
        return contractService.add(contractModel);
    }

    /*
     * 修改
     */
    @RequestMapping(value="/update",method= RequestMethod.POST)
    public ResultObject<Contract> save(ContractModel contractModel){
        return contractService.update(contractModel);
    }

    /*
     * 删除
     */
    @RequestMapping(value="/delete",method=RequestMethod.GET)
    public ResultObject<Contract> deletEmp(long id){
        return contractService.delete(id);
    }


    /**
     * @param
     * @return
     *  查询钱前台页面在员工修改页面需要的数据
     */
    @RequestMapping(value="/basicdata",method=RequestMethod.GET)
    public ResultObject<Object> basicdata(){
        return contractService.getBaseData();
    }
    /**

     */
    @RequestMapping(value="/findByClientId",method=RequestMethod.GET)
    public ResultObject<Object> findByClientId(long clientId){
        return contractService.findByClientId(clientId);
    }

}
