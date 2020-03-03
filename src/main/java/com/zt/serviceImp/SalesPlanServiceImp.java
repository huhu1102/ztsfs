    package com.zt.serviceImp;

    import com.zt.common.Message;
    import com.zt.common.MessageUtil;
    import com.zt.common.UsersUtils;
    import com.zt.common.Utils;
    import com.zt.dao.*;
    import com.zt.model.BusinessRuntimeException;
    import com.zt.model.ResultCode;
    import com.zt.model.ResultObject;
    import com.zt.model.ResultPage;
    import com.zt.po.*;
    import com.zt.service.SalesPlanService;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.*;

    @Service("sales")
public class SalesPlanServiceImp implements SalesPlanService {
        @Autowired
        SalesPlanDao salesPlanDao;
        @Autowired
        EmployeeDao employeeDao;
        @Autowired
        ProductDao productDao;
        @Autowired
        ClientDao clientDao;
        @Autowired
        Message message;
        @Autowired
        MessageUtil msgUtil;
        @Autowired
        SpecificationDao specsDao;
        @Autowired
        ColorDao colorDao;
        @Autowired
        ProductionPlanDetailsDao productionPlanDetailsDao;
        @Autowired
        ProductManageDao productManageDao;
        @Autowired
        SalesOrderDetailsDao salesOrderDetailsDao;
        @Autowired
        SalesOrderDao salesOrderDao;
        @Autowired
        MessageUtil messageUtil;

        private Logger logger = LoggerFactory.getLogger(this.getClass());

        /*
        查询
         */
        @Override
        public ResultPage<Object> find(String clientName, String productName, String empName, String start, String end, Integer status, int page, int size) throws BusinessRuntimeException {
            ResultPage<Object> ro = new ResultPage<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Map<String, String> stringStringMap = Utils.updateTime(start, end);
            start = stringStringMap.get("0");
            end = stringStringMap.get("1");
            Page<Object> pages = salesPlanDao.find(clientName, productName, empName, start, end, status, pageable);
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
    查询销售计划状态
     */

        @Override
        public ResultObject<SalesPlan> findStatus() throws BusinessRuntimeException {
            ResultObject<SalesPlan> ro = new ResultObject<>();
            Set<SalesPlan> status = salesPlanDao.findStatus();
            if (status != null) {
                List<SalesPlan> list = new ArrayList<>(status);
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
            查询未发货完成的销售计划
             */
        @Override
        public ResultPage<SalesPlan> findForShipping(String clientName, String productName, String empName, String start, String end, Integer status, int page, int size) {
            ResultPage<SalesPlan> ro = new ResultPage<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<SalesPlan> pages = salesPlanDao.findForShipping(clientName, productName, empName, start, end, status, pageable);
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
        根据销售计划id查询用户
         */
        @Override
        public ResultObject<Product> findClient(long salesPlanId) throws BusinessRuntimeException {
            ResultObject<Product> ro = new ResultObject<>();
            Product product = productDao.findClient(salesPlanId);
            Map<String, Product> map = new HashMap<>();
            if (product != null) {
                map.put("product", product);
                ro.setRoot(map);
                ro.setSuccess(true);
            } else {
                ro.setMsg("查询失败");
                ro.setSuccess(false);
            }
            return ro;
        }

        /*
            新建
             */
        @Override
        @Transactional
        public ResultObject<SalesPlan> add(SalesPlan salesPlan) throws BusinessRuntimeException {
            ResultObject<SalesPlan> ro = new ResultObject<>();

            salesPlan.setEnabled(true);
            long empId = UsersUtils.getCurrentHr().getEmpId();
            if (salesPlan.getSpecsId() != null && salesPlan.getSpecsId() != 0) {
                salesPlan.setSpecification(specsDao.findById((long) salesPlan.getSpecsId()));
            }
            if (salesPlan.getColorId() != null && salesPlan.getColorId() != 0) {
                salesPlan.setColor(colorDao.findById((long) salesPlan.getColorId()));
            }
            salesPlan.setEditerId(empId);
            salesPlan.setEditer(employeeDao.findById(empId));
            long proId = salesPlan.getProId();
            Product product = productDao.findById(proId);
            salesPlan.setProduct(product);
            salesPlan.setProductName(product.getProducteName());
            salesPlan.setProductNo(product.getProType());
            salesPlan.setEndQuantity(0);
            salesPlan.setNextQuantity(0);
            salesPlan.setShippingQuantity(0);
            salesPlan.setExpectLevel(4);
            salesPlan.setResendNo(0);
            salesPlan.setCreateDate(new Date());
            salesPlan.setUpsDate(new Date());
            long clientId = salesPlan.getClientId();
            Client client = clientDao.findById(clientId);
            salesPlan.setClient(client);
            salesPlan.setClientId(salesPlan.getClientId());
            salesPlan.setClientName(client.getName());
            salesPlan.setStatus(1);
            salesPlan.setPlanStatus(1);
            salesPlan.setManageStatus(1);
            salesPlan.setShippingStatus(1);
            salesPlan.setAbbr(client.getAbbreviation());

            String planNo = salesPlanDao.findMaxPlanNo();
            salesPlan.setSerialNumber(Utils.newPlanNo(planNo, "S"));
            salesPlan.setUpsDate(new Date());
            salesPlan = salesPlanDao.save(salesPlan);
            if (salesPlan != null) {
                ro.setSuccess(true);
                ro.setMsg("操作成功");
                //查询客户和产品的关系表中是否有该数据
                int ssp = salesPlanDao.selectClientPro(clientId, proId);
                if (ssp > 0) {
                    //表示已经有这个关系了不需要操作
                    logger.info("关系表中已经有该关系了");
                } else {
                    //关系表中添加数据
                    int pro = salesPlanDao.addClientPro(clientId, proId);
                    if (pro > 0) {
                        logger.info("关系添加成功");
                        List<String> longList = salesPlanDao.findProducts(clientId);
                        StringBuilder sb = new StringBuilder();
                        if (longList.size() > 0) {
                            for (int i = 0, n = longList.size(); i < n; i++) {
                                String name = longList.get(i);
                                sb.append(i + 1).append(":").append(name).append("\t");
                            }
                            int str = clientDao.updateStr(sb.toString(), clientId);
                            if (str > 0) {
                                ro.setSuccess(true);
                                ro.setMsg("修改成功");
                            } else {
                                ro.setSuccess(false);
                                ro.setMsg("修改失败");
                            }
                        }
                    }
                }
                //发送消息
                String userName = UsersUtils.getCurrentHr().getEmpName();
                StringBuilder title = new StringBuilder();
                title.append(userName);
                title.append("创建了一个新的销售计划");

                StringBuilder content = new StringBuilder();
                content.append("客户名称")
                        .append(salesPlan.getClientName())
                        .append("产品名称")
                        .append(salesPlan.getProductName())
                        .append("数量")
                        .append(salesPlan.getQuantity());
                int re = msgUtil.sendMsg(title.toString(), content.toString(), "SalesPlan", salesPlan.getId(), message.UserIds());
                if (re > 0) {
                    ro.setSuccess(true);
                    ro.setMsg("发送消息成功");
                } else {
                    ro.setSuccess(false);
                    ro.setMsg("发送消息失败");
                }

            } else {
                ro.setSuccess(false);
                ro.setMsg("操作失败");
            }
            return ro;
        }

        /*
        修改
         */
        @Override
        @Transactional
        public ResultObject<SalesPlan> update(SalesPlan salesPlan) throws BusinessRuntimeException {
            ResultObject<SalesPlan> ro = new ResultObject<>();
            if (salesPlan.getId() != 0) {
                SalesPlan oldsale = salesPlanDao.findById(salesPlan.getId());
                if (oldsale.getCreateDate() != null) {
                    salesPlan.setCreateDate(oldsale.getCreateDate());
                }
                if (oldsale.getStatus() != null) {
                    salesPlan.setStatus(oldsale.getStatus());
                }
                if (oldsale.getPlanStatus() != null) {
                    salesPlan.setPlanStatus(oldsale.getPlanStatus());
                }
                if (oldsale.getManageStatus() != null) {
                    salesPlan.setManageStatus(oldsale.getManageStatus());
                }
                salesPlan.setSerialNumber(oldsale.getSerialNumber());
                if (oldsale.getShippingStr() != null) {
                    salesPlan.setShippingStr(oldsale.getShippingStr());
                }
                if (oldsale.getResendStr() != null) {
                    salesPlan.setResendStr(oldsale.getResendStr());
                }
            }

            salesPlan.setSpecification(specsDao.findById((long) salesPlan.getSpecsId()));
            salesPlan.setColor(colorDao.findById((long) salesPlan.getColorId()));
            salesPlan.setEnabled(true);
            salesPlan.setUpsDate(new Date());
//        long empId = UsersUtils.getCurrentHr().getEmpId();
//        salesPlan.setEditerId(empId);
            salesPlan.setEditer(employeeDao.findById((long) salesPlan.getEditerId()));
            Product product = productDao.findById(salesPlan.getProId());
            salesPlan.setProduct(product);
            salesPlan.setProductName(product.getProducteName());
            salesPlan.setProductNo(product.getProType());
            Client client = clientDao.findById(salesPlan.getClientId());
            salesPlan.setClient(client);
            salesPlan.setClientId(salesPlan.getClientId());
            salesPlan.setClientName(client.getName());
            salesPlan.setUpsDate(new Date());
            salesPlan = salesPlanDao.saveAndFlush(salesPlan);
            if (salesPlan != null) {
                ro.setSuccess(true);
                ro.setMsg("操作成功");
                //查询客户和产品的关系表中是否有该数据
                int ssp = salesPlanDao.selectClientPro(salesPlan.getClientId(), salesPlan.getProId());
                if (ssp > 0) {
                    //表示已经有这个关系了不需要操作
                    logger.info("关系表中已经有该关系了");
                } else {
                    //关系表中添加数据
                    long clientId = salesPlan.getClientId();
                    long proId = salesPlan.getProId();
                    int pro = salesPlanDao.addClientPro(clientId, proId);
                    if (pro > 0) {
                        logger.info("关系添加成功");
                        List<String> longList = salesPlanDao.findProducts(salesPlan.getClientId());
                        StringBuilder sb = new StringBuilder();
                        if (longList.size() > 0) {
                            for (int i = 0, n = longList.size(); i < n; i++) {
                                String name = longList.get(i);
                                sb.append(i + 1).append(":").append(name).append("\t");
                            }
                            int str = clientDao.updateStr(sb.toString(), salesPlan.getClientId());
                            if (str > 0) {
                                ro.setSuccess(true);
                                ro.setMsg("修改成功");
                            } else {
                                ro.setSuccess(false);
                                ro.setMsg("修改失败");
                            }
                        }
                    }
                }
                //发送消息
                String userName = UsersUtils.getCurrentHr().getEmpName();
                StringBuilder title = new StringBuilder();
                title.append(userName);
                title.append("修改了一个新的销售计划");
                StringBuilder content = new StringBuilder();
                content.append("客户名称")
                        .append(salesPlan.getClientName())
                        .append("产品名称")
                        .append(salesPlan.getProductName())
                        .append("数量:")
                        .append(salesPlan.getQuantity());
                int re = msgUtil.sendMsg(title.toString(), content.toString(), "SalesPlan", salesPlan.getId(), message.UserIds());
                if (re > 0) {
                    ro.setSuccess(true);
                    ro.setMsg("发送消息成功");
                } else {
                    ro.setSuccess(false);
                    ro.setMsg("发送消息失败");
                }

            } else {
                ro.setSuccess(false);
                ro.setMsg("操作失败");
            }
            return ro;
        }

        /*
        删除销售计划
         */
        @Override
        @Transactional
        public ResultObject<SalesPlan> delete(long id) throws BusinessRuntimeException {
            ResultObject<SalesPlan> ro = new ResultObject<>();
            SalesPlan salesPlan = salesPlanDao.findById(id);
            if (salesPlan != null) {
                salesPlan.setEnabled(false);
                salesPlan.setUpsDate(new Date());
                salesPlanDao.saveAndFlush(salesPlan);
                //修改生产详情
                //首先查看是否有符合的生产计划详情
                if (productionPlanDetailsDao.updateEnabled(id) > 0) {
                    ro.setMsg("生产计划详情删除成功");
                    ro.setSuccess(true);
                }
                ro.setSuccess(true);
                ro.setMsg("销售计划删除成功");
                //发送消息
                int e = setMessage(salesPlan.getId(), productManageDao.findBySalesPlanId(salesPlan.getId()).getId(), "删除一个销售计划", "SalesPlan");
                if (e > 0) {
                    ro.setSuccess(true);
                    ro.setMsg("发送消息成功");
                } else {
                    ro.setSuccess(false);
                    ro.setMsg("发送消息失败");
                }

            } else {
                ro.setSuccess(false);
                ro.setMsg("销售计划删除失败");
                throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
            }
            return ro;
        }

        /*
        撤销  需要重写
        销售计划与销售订单没有直接关系
         */
        @Override
        @Transactional
        public ResultObject<SalesPlan> annul(long salesPlanId) throws BusinessRuntimeException {
            ResultObject<SalesPlan> ro = new ResultObject<>();
            if (salesPlanDao.updateStatus(4, salesPlanId) > 0) {
                ro.setSuccess(true);
                ro.setMsg("销售计划撤销成功");
                //发消息
                int re = setMessage(salesPlanId, salesPlanId, "撤销一个销售计划", "SalesPlan");
                if (re > 0) {
                    ro.setSuccess(true);
                    ro.setMsg("发送消息成功");
                } else {
                    ro.setSuccess(false);
                    ro.setMsg("发送消息失败");
                }
                //修改销售计划中的所有其他状态为撤销
                if (salesPlanDao.annul(salesPlanId) > 0) {
                    ro.setSuccess(true);
                }
                if (productionPlanDetailsDao.updateStatus(4, salesPlanId) > 0) {
                    ro.setSuccess(true);
                    ro.setMsg("生产计划详情撤销成功");
                    //发消息
                    List<ProductionPlanDetails> productionPlanDetailsList = productionPlanDetailsDao.findBySalesPlanId(salesPlanId);
                    ProductionPlanDetails productionPlanDetails = new ProductionPlanDetails();
                    for (int a = 0, m = productionPlanDetailsList.size(); a < m; a++) {
                        productionPlanDetails = productionPlanDetailsList.get(a);
                        int r = setMessage(salesPlanId, productionPlanDetails.getId(), "撤销一个生产计划详情", "ProductionPlanDetails");
                        if (r > 0) {
                            ro.setSuccess(true);
                            ro.setMsg("发送消息成功");
                        } else {
                            ro.setSuccess(false);
                            ro.setMsg("发送消息失败");
                        }
                    }
                    //这里需要加上订单撤销逻辑(订单撤销的话需要将对应的所有的生产计划详情进行撤销,逻辑需要重新考虑)
                    //需要先查找有没有对应的订单
                    List<SalesOrderDetails> salesOrderDetailsList = salesOrderDetailsDao.findBySales(salesPlanId);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0, n = salesOrderDetailsList.size(); i < n; i++) {
                        SalesOrderDetails orderDetails = salesOrderDetailsList.get(i);
                        long detailsId = orderDetails.getId();
                        ProductionPlanDetails planDetails = productionPlanDetailsDao.findByOrderDetails(detailsId);
                        planDetails.getSalesPlan().getProductName();//产品名称
                        planDetails.getActualQuantity();//该生产计划的数量
                        sb.append("订单:")
                                .append( //订单编号
                                        orderDetails.getSalesOrder().getOrderNo())
                                .append(",产品:")
                                .append(planDetails.getSalesPlan().getProductName())//产品名称
                                .append(",数量:")
                                .append(planDetails.getActualQuantity())//该生产计划的数量
                                .append(";");
                        //修改订单详情的状态
                        int changeStatus = salesOrderDetailsDao.changeStatus(3, detailsId);
                        if (changeStatus > 0) {
                            //修改订单状态
                            changeOrder(orderDetails.getSalesOrder());

                            //发送消息
                            int r = messageUtil.sendMsg("撤销一个订单详情", sb.toString(), "SalesOrderDetails", orderDetails.getId(), message.UserIds());
                            if (r > 0) {
                                ro.setSuccess(true);
                                logger.info("订单详情撤销成功");
                            } else {
                                ro.setSuccess(false);
                                logger.info("订单详情撤销失败");
                            }

                        }

                    }

                    if (productManageDao.updateStatus(4, salesPlanId) > 0) {
                        ro.setSuccess(true);
                        ro.setMsg("生产管理撤销成功");
                        //发送消息
                        int e = setMessage(salesPlanId, productManageDao.findBySalesPlanId(salesPlanId).getId(), "撤销一个生产管理", "ProductManage");
                        if (e > 0) {
                            ro.setSuccess(true);
                            ro.setMsg("发送消息成功");
                        } else {
                            ro.setSuccess(false);
                            ro.setMsg("发送消息失败");
                        }
                    } else {
                        ro.setSuccess(false);
                        ro.setMsg("生产管理撤销成功");
                    }

                } else {
                    ro.setSuccess(false);
                }
            } else {
                ro.setSuccess(false);
                ro.setMsg("销售计划撤销失败");
                throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
            }

            return ro;
        }

        private int setMessage(long salesPlanId, long id, String con, String className) {
            //发送消息
            String userName = UsersUtils.getCurrentHr().getEmpName();
            StringBuilder title = new StringBuilder();
            title.append(userName);
            title.append(con);

            StringBuilder content = new StringBuilder();
            SalesPlan salesPlan = salesPlanDao.findById(salesPlanId);
            content.append("客户名称")
                    .append(salesPlan.getClientName())
                    .append("产品名称")
                    .append(salesPlan.getProductName())
                    .append("数量")
                    .append(salesPlan.getQuantity());
            int re = msgUtil.sendMsg(title.toString(), content.toString(), className, id, message.UserIds());

            return re;
        }

        @Override
        public ResultPage<Object> findForPlan(String clientName, String productName, String empName, String start, String end, Integer planStatus, int page, int size) {
            {
                ResultPage<Object> ro = new ResultPage<>();
                Pageable pageable = PageRequest.of(page - 1, size);
                Page<Object> pages = salesPlanDao.findForPlan(clientName, productName, empName, start, end, planStatus, pageable);
                if (pages != null && pages.getSize() >= 0) {
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
        }

        @Override
        public ResultPage<Object> findForNoPlan(String clientName, String productName, String empName, String start, String end, Integer status, int page, int size) {
            ResultPage<Object> ro = new ResultPage<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Object> pages = salesPlanDao.findForNoPlan(clientName, productName, empName, start, end, pageable);
            if (pages != null && pages.getSize() >= 0) {
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

        /**
         * 批量修改重要程度
         *
         * @param ids
         * @param important
         * @return
         */
        @Override
        @Transactional
        public ResultObject<SalesPlan> batchEdit(String ids, int important) {
            ResultObject<SalesPlan> ro = new ResultObject<>();
            String[] idarr = ids.split(",");
            List<Long> idsArr = new ArrayList<>();
            List<String> resultList = new ArrayList<>(idarr.length);
            Collections.addAll(resultList, idarr);
            for (String s : resultList) {
                idsArr.add(Long.parseLong(s));
            }
            int result = salesPlanDao.changeLever(idsArr, important);
            if (result > 0) {
                ro.setSuccess(true);
                ro.setMsg("修改成功");
            } else {
                ro.setSuccess(false);
            }
            return ro;
        }

        public void changeOrder(SalesOrder salesOrder) {
            int result = 0;
            List<SalesOrderDetails> salesOrderDetailsList = salesOrder.getSalesOrderDetails();
            for (int i = 0, n = salesOrderDetailsList.size(); i < n; i++) {
                SalesOrderDetails orderDetails = salesOrderDetailsList.get(i);
                if (orderDetails.getDetailsStatus() == 4) {
                    result++;
                } else {
                    result--;
                }
            }
            if (result > 0) {
                //修改订单状态
                int i = salesOrderDao.updateStatus(3, salesOrder.getId());
                if (i > 0) {
                    //发送消息
                    int r = messageUtil.sendMsg("撤销一个订单", "", "SalesOrder", salesOrder.getId(), message.UserIds());
                    if (r > 0) {
                        logger.info("消息发送成功");
                    } else {
                        logger.info("消息发送失败");
                    }
                }
            }
        }

    }
