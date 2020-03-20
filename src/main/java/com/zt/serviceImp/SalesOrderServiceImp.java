package com.zt.serviceImp;

import com.zt.common.Message;
import com.zt.common.MessageUtil;
import com.zt.common.Utils;
import com.zt.dao.*;
import com.zt.model.*;
import com.zt.po.*;
import com.zt.service.SalesOrderService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author wl
 * @date 2019年4月23日
 */
@Service("salesOrderService")
//@CacheConfig(cacheNames = "zt_caches_contract")
public class SalesOrderServiceImp implements SalesOrderService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SalesOrderDao salesOrderDao;
    @Autowired
    SalesOrderDetailsDao salesOrderDetailsDao;
    @Autowired
    SalesPlanDao salesPlanDao;
    @Autowired
    ClientDao clientDao;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    ProductionPlanDetailsDao productionPlanDetailsDao;
    @Autowired
    Message message;
    @Autowired
    MessageUtil messageUtil;

    /*
    新建
     */
    @Override
    @Transactional
    public ResultObject<SalesOrder> add(SalesOrderModel salesOrderModel) throws Exception {
        ResultObject<SalesOrder> ro = new ResultObject<>();
        SalesOrderModel mo = new SalesOrderModel();
        SalesOrderDetailsModel moo = new SalesOrderDetailsModel();
        SalesOrder salesOrder = mo.v2p(salesOrderModel);
        salesOrder.setCreateDate(new Date());
        long clientId = salesOrder.getCliId();
        if (clientId > 0) {
            salesOrder.setCliente(clientDao.findById(clientId));
        }
        if (salesOrder.getEmpId() > 0) {
            salesOrder.setEmployee(employeeDao.findById(salesOrder.getEmpId()).get());
        }
        salesOrder.setOrderStatus(1);
        List<SalesOrderDetails> salesOrderDetailsList = new ArrayList<>();
        JSONArray array = JSONArray.fromObject(salesOrderModel.getOrderDetails());
        List<Long> planIds = new ArrayList<>();
        if (array.size() > 0) {
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = array.getJSONObject(i);
                SalesOrderDetailsModel conDetailsModel = moo.j2m(object);
                Long productionPlanDetailsIds = Long.valueOf(conDetailsModel.getProductionPlanDetailsIds());
                planIds.add(productionPlanDetailsIds);
                SalesOrderDetails salesOrderDetails = moo.v2p(conDetailsModel);
                salesOrderDetails.setCreateDate(new Date());
                ProductionPlanDetails planDetails = productionPlanDetailsDao.findById((long) productionPlanDetailsIds);

                if (planDetails != null) {
                    salesOrderDetails.setProductionPlanDetails(planDetails);
                }
                salesOrderDetails.setSalesOrder(salesOrder);
                salesOrderDetails.setDetailsStatus(1);
                salesOrderDetailsList.add(salesOrderDetails);
            }
        }
        salesOrder.setSalesOrderDetails(salesOrderDetailsList);
        String planNo = salesOrderDao.findMaxPlanNo();
        salesOrder.setOrderNo(Utils.newPlanNo(planNo, "Z"));
        salesOrder = salesOrderDao.save(salesOrder);
        if (salesOrder != null) {
            //修改对应生产计划详情状态
            logger.info("创建成功");
            ro.setMsg("保存订单成功！");
            Integer re = productionPlanDetailsDao.changeContactState(planIds, 2);
            if (re != null && re > 0) {
                ro.setSuccess(true);
                ro.setMsg("新建成功");
            } else {
                logger.info("修改生产计划失败");
                ro.setSuccess(false);
                ro.setMsg("修改生产计划失败");
            }

        } else {
            ro.setSuccess(false);
            ro.setMsg("新建失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

    /*
     * 修改
     */
    @Override
    @Transactional
    public ResultObject<SalesOrder> update(SalesOrderModel salesOrderModel) throws Exception {
        ResultObject<SalesOrder> ro = new ResultObject<>();
        if (salesOrderModel.getId() > 0) {
            SalesOrder oldOrder = salesOrderDao.findById(salesOrderModel.getId());
            SalesOrderModel mo = new SalesOrderModel();
            SalesOrderDetailsModel sodmo = new SalesOrderDetailsModel();
            SalesOrder salesOrder = mo.v2p(salesOrderModel);
            if (salesOrder.getCliId() > 0) {
                salesOrder.setCliente(clientDao.findById(salesOrder.getCliId()).get());
            }
            if (salesOrder.getEmpId() != null && salesOrder.getEmpId() > 0) {
                salesOrder.setEmployee(employeeDao.findById(salesOrder.getEmpId()).get());
            }
            //原订单详情
            List<SalesOrderDetails> salesOrderDetailsList = oldOrder.getSalesOrderDetails();

            JSONArray array = JSONArray.fromObject(salesOrderModel.getOrderDetails());
            Integer detailsId;
            List<Long> addArrIds = new ArrayList<>();//新增的销售计划Id
            List<Long> delArrIds = new ArrayList<>();//删除的销售计划Id
            if (array.size() > 0) {
                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    SalesOrderDetailsModel sodModel = sodmo.j2m(object);
                    String tap = sodModel.getTap();
                    Long productionPlanDetailsIds = Long.parseLong(sodModel.getProductionPlanDetailsIds());
                    SalesOrderDetails salesOrderDetails = sodmo.v2p(sodModel);
                    salesOrderDetails.setSalesOrder(salesOrder);
                    salesOrderDetails.setProductionPlanDetails(productionPlanDetailsDao.findById(productionPlanDetailsIds).get());
                    switch (tap) {
                        case "new":
//							salesOrderDetails = salesOrderDetailsDao.save(salesOrderDetails);
                            addArrIds.add(productionPlanDetailsIds);
                            salesOrderDetailsList.add(salesOrderDetails);
                            break;
                        case "update":
//							salesOrderDetails = salesOrderDetailsDao.saveAndFlush(salesOrderDetails);
                            for (int c = salesOrderDetailsList.size() - 1; c >= 0; c--) {
                                SalesOrderDetails mm = salesOrderDetailsList.get(c);
                                if (mm.getProductionPlanDetails() == salesOrderDetails.getProductionPlanDetails()) {
                                    salesOrderDetailsList.remove(c);
                                    salesOrderDetailsList.add(c, salesOrderDetails);
                                }
                            }
                            break;
                        case "delete":
                            delArrIds.add(productionPlanDetailsIds);
                            for (int n = salesOrderDetailsList.size() - 1; n >= 0; n--) {
                                SalesOrderDetails mm = salesOrderDetailsList.get(n);
                                if (mm.getProductionPlanDetails() == salesOrderDetails.getProductionPlanDetails()) {
                                    salesOrderDetailsList.remove(n);
                                }
                            }
                            break;
                        default:
                            break;

                    }

                }
            }
            salesOrder.setOrderStatus(oldOrder.getOrderStatus());
            salesOrder.setSalesOrderDetails(salesOrderDetailsList);
            salesOrder.setCreateDate(oldOrder.getCreateDate());
            salesOrder.setOrderNo(oldOrder.getOrderNo());
            salesOrder = salesOrderDao.saveAndFlush(salesOrder);
            if (salesOrder != null) {
                //修改
                Integer re = null, de = null;
                ro.setSuccess(true);
                if (addArrIds.size() > 0) {

                    re = productionPlanDetailsDao.changeContactState(addArrIds, 2);
                    if (re == null && re <= 0) {
                        ro.setSuccess(false);
                    }
                }
                if (delArrIds.size() > 0) {
                    de = productionPlanDetailsDao.changeContactState(delArrIds, 1);
                    if (de == null && de <= 0) {
                        ro.setSuccess(false);
                    }
                }
                if (ro.isSuccess()) {
                    ro.setMsg("修改成功");
                } else {
                    ro.setSuccess(false);
                    ro.setMsg("操作失败");
                }
            } else {
                ro.setSuccess(false);
                ro.setMsg("修改失败");
                throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
            }
        }
        return ro;
    }


    /*
     * 分页模糊查询
     */
    @Override
//	@Cacheable(key = "'con_'+#queryName")
    public ResultPage<SalesOrder> findSearch(String clientName, String productName, String empName, String start, String end, Integer status, int page, int size) throws BusinessRuntimeException {
        ResultPage<SalesOrder> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<SalesOrder> pages = salesOrderDao.findSearch(clientName, empName, start, end, status, pageable);
        logger.info(pages.getContent().toString());
        if (pages != null) {
            ro.setData(pages.getContent());
            ro.setTotal(pages.getTotalElements());
            ro.setTotalPages(pages.getTotalPages());
            ro.setSuccess(true);
        } else {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
        }
        return ro;
    }

    /*
    根据状态查询数量
     */
    @Override
    public ResultObject<SalesOrder> findStatus() throws BusinessRuntimeException {
        ResultObject<SalesOrder> ro = new ResultObject<>();
        Set<SalesOrder> status = salesOrderDao.findStatus();
        if (status != null) {
            List<SalesOrder> list = new ArrayList<>(status);
            ro.setData(list);
            logger.info("成功");
            ro.setSuccess(true);
        } else {
            logger.info("查询失败！");
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

    /*
     * 删除
     */
    @Override
    @Transactional
//	@CacheEvict(allEntries = true)
    public ResultObject<SalesOrder> deletDep(long id) throws BusinessRuntimeException {
        ResultObject<SalesOrder> ro = new ResultObject<>();
        SalesOrder con = salesOrderDao.findById(id);
        if (con != null) {
            List<SalesOrderDetails> salesOrderDetails = con.getSalesOrderDetails();
            int size = salesOrderDetails.size();
            if(size>0){
                List<Long> ids = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    long detailsId = salesOrderDetails.get(i).getId();
                    ids.add(detailsId);
                }
                productionPlanDetailsDao.changeContactState(ids, 1);
            }
            con.setEnabled(false);
            salesOrderDao.saveAndFlush(con);
            ro.setSuccess(true);
            ro.setMsg("操作成功");
        } else {
            ro.setSuccess(false);
            ro.setMsg("操作失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }
	/*
	撤销
	 */

    @Override
    public ResultObject<SalesOrder> annul(long salesOrderId) throws BusinessRuntimeException {
        ResultObject<SalesOrder> ro = new ResultObject<>();
        SalesOrder salesOrder = salesOrderDao.findById(salesOrderId);
        int orderStatus = salesOrderDao.updateStatus(3, salesOrderId);
        if (orderStatus > 0) {
            //订单撤销成功
            List<SalesOrderDetails> salesOrderDetailsList = salesOrder.getSalesOrderDetails();
            for (int i = 0, n = salesOrderDetailsList.size(); i < n; i++) {
                SalesOrderDetails salesOrderDetails = salesOrderDetailsList.get(i);
                int orderDetailsStatus = salesOrderDetailsDao.changeStatus(3, salesOrderDetails.getId());
                //根据订单详情查找生产计划详情
                ProductionPlanDetails planDetails = productionPlanDetailsDao.findByOrderDetails(salesOrderDetails.getId());
                int updateStatus = productionPlanDetailsDao.updateStatus(4, planDetails.getId());
                if (orderDetailsStatus > 0 && updateStatus > 0) {
                    //订单详情以及生产计划详情的状态已经撤销

                    //发消息
                    int r = messageUtil.sendMsg("撤销一个订单详情", "", "SalesOrderDetails", salesOrderDetails.getId(), message.UserIds());
                    if (r > 0) {
                        logger.info("消息发送成功");
                    } else {
                        logger.info("消息发送失败");
                    }
                    int re = messageUtil.sendMsg("撤销一个生产计划", "", "ProductionPlanDetails", planDetails.getId(), message.UserIds());
                    if (re > 0) {
                        logger.info("消息发送成功");
                    } else {
                        logger.info("消息发送失败");
                    }

                    SalesPlan salesPlan = planDetails.getSalesPlan();
                    changeSales(salesPlan);

                }
            }
        }


        return ro;
    }
    /*
    根据未归档合同查询所有订单
    */
    @Override
    public ResultObject<SalesOrder> findAllOders() throws BusinessRuntimeException {
        ResultObject<SalesOrder> ro = new ResultObject<>();
        List<SalesOrder> allOders = salesOrderDao.findAllOders();
        if(allOders!=null){
            ro.setData(allOders);
            ro.setSuccess(true);
            ro.setMsg("查询成功");
        }else{
            ro.setSuccess(false);
            ro.setMsg("查询失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }

        return ro;
    }

    public void changeSales(SalesPlan salesPlan) {
        int result = 0;
        List<ProductionPlanDetails> productionPlanDetailsList = productionPlanDetailsDao.findBySalesPlanId(salesPlan.getId());
        for (int i = 0, n = productionPlanDetailsList.size(); i < n; i++) {
            ProductionPlanDetails productionPlanDetails = productionPlanDetailsList.get(i);
            if (productionPlanDetails.getStatus() == 4) {
                result++;
            } else {
                result--;
            }
        }
        if (result > 0) {
            //修改销售计划状态
            int re = salesPlanDao.updateStatus(4, salesPlan.getId());
            if (re > 0) {
                //发送消息
                int r = messageUtil.sendMsg("撤销一个销售计划", "", "SalesPlan", salesPlan.getId(), message.UserIds());
                if (r > 0) {
                    logger.info("消息发送成功");
                } else {
                    logger.info("消息发送失败");
                }
            }
        }
    }
}
