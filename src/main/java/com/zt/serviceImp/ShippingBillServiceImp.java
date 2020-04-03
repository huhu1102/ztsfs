package com.zt.serviceImp;

import com.zt.common.Message;
import com.zt.common.MessageUtil;
import com.zt.common.NumberToCN;
import com.zt.common.UsersUtils;
import com.zt.dao.*;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ProductionPlanDetails;
import com.zt.po.SalesPlan;
import com.zt.po.ShippingBill;
import com.zt.service.ShippingBillService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wl
 * @date 2019年6月11日
 */
@Service("shippingBillService")
public class ShippingBillServiceImp implements ShippingBillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ShippingBillDao shippingBillDao;
    @Autowired
    MessageUtil msgUtil;
    @Autowired
    DepartmentDao depDao;
    @Autowired
    PositionDao posDao;
    @Autowired
    EmployeeDao empDao;
    @Autowired
    UsersDao usersDao;
    @Autowired
    ClientDao clientDao;
    @Autowired
    SalesPlanDao salesPlanDao;
    @Autowired
    ProductionPlanDetailsDao productionPlanDetailsDao;
    @Autowired
    ShippingRequestDetailsDao shippingRequestDetailsDao;
    @Autowired
    ProductManageDao productManageDao;
    @Autowired
    Message message;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy年MM月dd日");

    /*
     * 分页查询
     */
    @Override
    public ResultPage<ShippingBill> findSearch(String queryName, int page, int size) throws BusinessRuntimeException {
        ResultPage<ShippingBill> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);

        queryName = null;
        Page<ShippingBill> pages = shippingBillDao.findSearch(queryName, pageable);

        if (pages != null) {
            int totals = (int) pages.getTotalElements();
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
    public ResultPage<ShippingBill> findForManage(long productManageId, int page, int size) {
        ResultPage<ShippingBill> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ShippingBill> forManage = shippingBillDao.findForManage(productManageId, pageable);
        if (forManage != null) {
            ro.setData(forManage.getContent());
            ro.setTotal(forManage.getTotalElements());
            ro.setTotalPages(forManage.getTotalPages());
            ro.setSuccess(true);
        } else {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
        }
        return ro;
    }

    @Override
    public ResultPage<ShippingBill> findForPlan(long productPlanId, int page, int size) {
        ResultPage<ShippingBill> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ShippingBill> forManage = shippingBillDao.findForPlan(productPlanId, pageable);
        if (forManage != null) {
            ro.setData(forManage.getContent());
            ro.setTotal(forManage.getTotalElements());
            ro.setTotalPages(forManage.getTotalPages());
            ro.setSuccess(true);
        } else {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
        }
        return ro;
    }

    /**
     * 新建与更新
     * 0在创建 时更改 销售计划中的shippingQuantity  数量
     */
    @Override
    @Transactional
    public ResultObject<ShippingBill> savebill(ShippingBill shippingBill) throws BusinessRuntimeException {
        ResultObject<ShippingBill> ro = new ResultObject<>();
        shippingBill.setCreateDate(new Date());
        shippingBill.setEnabled(true);
        if (shippingBill.getClientId() != 0) {
            shippingBill.setClient(clientDao.findById(shippingBill.getClientId()));
        }
        //代收款
        if (shippingBill.getCollFreight() != null) {
            String money = NumberToCN.number2CNMontrayUnit(shippingBill.getCollFreight());
            shippingBill.setCollFreightBig(money);
        }
        // 1.运输中2.已到达 3.已签收
        shippingBill.setShipingStatus(1);
        shippingBill = shippingBillDao.saveAndFlush(shippingBill);
//		修改销售计划中发货量
        if (null != shippingBill&& shippingBillDao.changeMidTable(shippingBill.getId(),shippingBill.getShippingRequestDetailsId())>0

                && shippingBill.getPlanNumber() != 0) {
            if (changSaleMsg(shippingBill,0F) > 0&&changProductMsg(shippingBill, 0F)>0) {
                ro.setSuccess(true);
                ro.setMsg("修改成功");
                //发送消息给相关部门
                ro = sendMsgToUser(shippingBill);
            } else {
                ro.setSuccess(false);
                ro.setMsg("修改失败");
            }
        }
        return ro;
    }

    @Override
    public ResultObject<ShippingBill> billAdd(ShippingBill shippingBill) {
        ResultObject<ShippingBill> ro = new ResultObject<>();
        shippingBill.setCreateDate(new Date());
        shippingBill.setEnabled(true);
        if (shippingBill.getClientId() != 0) {
            shippingBill.setClient(clientDao.findById(shippingBill.getClientId()));
        }
        //代收款
        if (shippingBill.getCollFreight() != null) {
            String money = NumberToCN.number2CNMontrayUnit(shippingBill.getCollFreight());
            shippingBill.setCollFreightBig(money);
        }
        // 1.运输中2.已到达 3.已签收
        shippingBill.setShipingStatus(1);
        shippingBill = shippingBillDao.saveAndFlush(shippingBill);
//		修改销售计划中发货量
        if (shippingBill!=null) {
                ro.setSuccess(true);
                ro.setMsg("修改成功");
                //发送消息给相关部门
                ro = sendMsgToUser(shippingBill);
            } else {
                ro.setSuccess(false);
                ro.setMsg("修改失败");
            }
        return ro;

    }

    /**
     * 修改销售计划
     * @param shippingBill 发货确认
     * @param oldCount
     * @return
     */
    private int changSaleMsg(ShippingBill shippingBill, Float oldCount) {
        Float sendCount = shippingBill.getPlanNumber()-oldCount;//该次发货总数量
        long managerId = shippingBill.getShippingRequestDetailsId();//生产管理Id
        //由生产管理Id 更改销售计划中发货数量
        StringBuilder sb = new StringBuilder();
        List<ShippingBill> billList = shippingBillDao.findByManagerId(managerId);
        for (int i = 0, n = billList.size(); i < n; i++) {
            ShippingBill shippingBills = billList.get(i);
            if (null != shippingBills) {
                sb.append(simpleDateFormat.format(shippingBills.getCreateDate()))
                        .append("数量:")
                        .append(shippingBills.getPlanNumber())
                        .append(";");
            }
        }
        //修改销售计划中的发货记录
       return shippingBillDao.changeSale(sendCount, sb.toString(), managerId);
    }
    /**
     * 修改生产计划中的数量
     * @param shippingBill 发货确认
     * @param oldCount
     * @return
     */
    private int changProductMsg(ShippingBill shippingBill, Float oldCount) {
        Float sendCount = shippingBill.getPlanNumber()-oldCount;//该次发货总数量
        long managerId = shippingBill.getShippingRequestDetailsId();//生产管理Id
        //由生产管理Id 更改销售计划中发货数量
        StringBuilder sb = new StringBuilder();
        List<ShippingBill> billList = shippingBillDao.findByManagerId(managerId);
        for (int i = 0, n = billList.size(); i < n; i++) {
            ShippingBill shippingBills = billList.get(i);
            if (null != shippingBills) {
                sb.append(simpleDateFormat.format(shippingBills.getCreateDate()))
                        .append("数量:")
                        .append(shippingBills.getPlanNumber())
                        .append(";");
            }
        }
        //修改生产计划中的发货记录
        return shippingBillDao.changeProduction(sendCount, sb.toString(), managerId);
    }



    /**
     * //发消息
     * 1.首先编写标题
     * 获取当前用户的名字
     * 2.编写消息内容
     *
     * @param shippingBill
     * @return
     */
    private ResultObject<ShippingBill> sendMsgToUser(ShippingBill shippingBill) {
        ResultObject<ShippingBill> ro = new ResultObject<>();
        String userName = UsersUtils.getCurrentHr().getEmpName();
        StringBuilder title = new StringBuilder();
        title.append(userName);
        title.append("新建了一个确认发货单");
        StringBuilder sbb = new StringBuilder();
        sbb.append("客户:")
                .append(shippingBill.getClient().getName())
                .append(",的货:")
                .append(shippingBill.getProDetails())
                .append(",通过")
                .append(shippingBill.getClient().getName())
                .append("物流部已发货,共")
                .append(shippingBill.getBoxs())
                .append("箱,收货人:")
                .append(shippingBill.getConsignee())
                .append(",收货人联系方式:")
                .append(shippingBill.getConsigneePhone());
        int re = msgUtil.sendMsg(title.toString(), sbb.toString(), "DispatchBill", shippingBill.getId(), message.UserIds());
        if (re > 0) {
            ro.setSuccess(true);
            ro.setMsg("发送消息成功");
        } else {
            ro.setSuccess(false);
            ro.setMsg("发送消息失败");
        }
        return ro;
    }
    /*
    修改
     */
    @Override
    @Transactional
    public ResultObject<ShippingBill> update(ShippingBill shippingBill) throws BusinessRuntimeException {
        ResultObject<ShippingBill> ro = new ResultObject<>();
        if(shippingBill.getId()>0){
            ShippingBill oldbill=shippingBillDao.findById(shippingBill.getId());
            float oldplanNumber=oldbill.getPlanNumber();//原发货数量
            shippingBill.setShipingStatus(oldbill.getShipingStatus());
            if (shippingBill.getClientId() != 0) {
                shippingBill.setClient(clientDao.findById(shippingBill.getClientId()));
            }
            if (shippingBill.getCollFreight() != null) {
                String s = NumberToCN.number2CNMontrayUnit(shippingBill.getCollFreight());
                shippingBill.setCollFreightBig(s);
            }
            if(oldbill.getCreateDate()!=null){
                shippingBill.setCreateDate(oldbill.getCreateDate());
            }else{
                shippingBill.setCreateDate(new Date());
            }
            shippingBill.setShippingRequestDetailsId(oldbill.getShippingRequestDetailsId());
            shippingBill.setManageList(oldbill.getManageList());
            shippingBill.setEnabled(true);
            /** 1.运输中2.已到达 3.已签收 */
            shippingBill = shippingBillDao.saveAndFlush(shippingBill);
           //修改销售计划中发货量
            if (shippingBill.getPlanNumber() != 0&&shippingBill.getShippingRequestDetailsId() != 0) {
                if(changSaleMsg(shippingBill,oldplanNumber)>0&&changProductMsg(shippingBill, oldplanNumber)>0){
                    ro = sendMsgToUser(shippingBill);
                }else{
                    ro.setMsg("修改销售计划中发货信息失败");
                    ro.setSuccess(false);
                }
            }

        }else{
            ro.setSuccess(false);
            ro.setMsg("操作失误啦！请重试~~");
        }

        return ro;
    }

    /*
     * 删除
     */
    @Override
    public ResultObject<ShippingBill> deletCli(long id) throws BusinessRuntimeException {
        ResultObject<ShippingBill> ro = new ResultObject<>();
        ShippingBill shippingBill = shippingBillDao.findById(id);
        if (shippingBill != null) {
            shippingBill.setEnabled(false);
            shippingBillDao.saveAndFlush(shippingBill);
            //修改生产计划的数据
            int productMsg = changProductMsg(shippingBill, shippingBill.getPlanNumber());
            int saleMsg = changSaleMsg(shippingBill, shippingBill.getPlanNumber());
            if(productMsg>0&&saleMsg>0){
                ro.setSuccess(true);
                ro.setMsg("操作成功");
            }
        } else {
            ro.setSuccess(false);
            ro.setMsg("操作失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

}
