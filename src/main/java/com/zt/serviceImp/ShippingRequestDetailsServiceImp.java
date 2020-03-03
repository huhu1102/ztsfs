package com.zt.serviceImp;

import java.text.SimpleDateFormat;
import java.util.*;

import com.zt.common.Message;
import com.zt.common.MessageUtil;
import com.zt.common.UsersUtils;
import com.zt.dao.ProductionPlanDao;
import com.zt.dao.ProductionPlanDetailsDao;
import com.zt.dao.SalesPlanDao;
import com.zt.model.ResultPage;
import com.zt.po.ProductionPlanDetails;
import com.zt.po.SalesPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.dao.ShippingRequestDetailsDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.po.ShippingRequestDetails;
import com.zt.service.ShippingRequestDetailsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wl
 * @date 2019年4月28日
 */
@Service("ShippingRequestDetailsService")
@CacheConfig(cacheNames = "zt_caches_ShippingRequest")
public class ShippingRequestDetailsServiceImp implements ShippingRequestDetailsService {
    @Autowired
    ShippingRequestDetailsDao shippingRequestDetailsDao;
    @Autowired
    SalesPlanDao salesPlanDao;
    @Autowired
    Message message;
    @Autowired
    MessageUtil msgUtil;
   @Autowired
    ProductionPlanDao  productionPlanDao;
   @Autowired
    ProductionPlanDetailsDao  productionPlanDetailsDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
    /*
     * 信息的修改
     */
    @Override
    @Transactional
    public ResultObject<ShippingRequestDetails> update(ShippingRequestDetails dere, String type) throws BusinessRuntimeException {
        ResultObject<ShippingRequestDetails> ro = new ResultObject<>();
        if (Long.valueOf(dere.getId()) == null) {
            dere.setCreateDate(new Date());
        }
        ShippingRequestDetails  srd=  shippingRequestDetailsDao.findById(dere.getId());
        dere.setEnabled(true);
        if(srd.getCreateDate()!=null){
        dere.setCreateDate(srd.getCreateDate());
        }else{
        dere.setCreateDate(new Date());
        }
        //发货请求描述文字(需要重新进行赋值)

        List<ShippingRequestDetails> detailsList = shippingRequestDetailsDao.findbySalesId(dere.getSalePlanId());
        StringBuilder sb = new StringBuilder();
        //发货请求次数
        if (detailsList != null && detailsList.size() > 0) {
            for (int i = 0,n=detailsList.size(); i < n ; i++) {
                ShippingRequestDetails dt = detailsList.get(i);
                sb.append(simpleDateFormat.format(new Date()))
                        .append("数量:")
                        .append(dt.getPlanNumber())
                        .append(";");
            }
        }
        dere = shippingRequestDetailsDao.saveAndFlush(dere);
        int result=0;
        if(type.equals("productPlan")){
            result=productionPlanDetailsDao.changeSend(dere.getExpectLevel(), dere.getSalePlanId(), sb.toString(), dere.getPlanNumber(), dere.getId());
        }else {
            //修改关联的销售计划的字段
             result = salesPlanDao.changeSend(dere.getExpectLevel(), dere.getSalePlanId(), sb.toString(), dere.getPlanNumber(), dere.getId());
        }

        if (dere != null&&result > 0) {
            ro.setSuccess(true);
            ro.setMsg("发货请求修改成功");
            //发送消息
            long salePlanId=0;
            int re=0;
            if(type.equals("productPlan")){
             ProductionPlanDetails  productionPlanDetails= productionPlanDetailsDao.findById(dere.getSalePlanId());
             if(null!=productionPlanDetails){
                 salePlanId=productionPlanDetails.getSalesPlan().getId();
             }
            }else {
                salePlanId=dere.getSalePlanId();
            }
                re = setMessage(salePlanId, dere.getId(), "修改发货请求", "ShippingRequestDetails");
            if(re>0) {
                ro.setSuccess(true);
                ro.setMsg("发送消息成功!");
            }else {
                ro.setSuccess(false);
                ro.setMsg("发送消息失败");
            }
        } else {
            ro.setSuccess(false);
            ro.setMsg("发货请求修改失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }
    /*
     * 发货请求的删除
     */
    @Override
    public ResultObject<ShippingRequestDetails> deletDre(long id) throws BusinessRuntimeException {
        ResultObject<ShippingRequestDetails> ro = new ResultObject<>();
        ShippingRequestDetails der = shippingRequestDetailsDao.findById(id);
        if (der != null) {
            der.setEnabled(false);
            shippingRequestDetailsDao.saveAndFlush(der);
            ro.setSuccess(true);
            ro.setMsg("操作成功");
            //删除发货请求之后删除在销售计划中修改的数据



            //发消息
            int re = setMessage(der.getSalePlanId(), der.getId(), "删除发货请求", "ShippingRequestDetails");
            if(re>0) {
                ro.setSuccess(true);
                ro.setMsg("发送消息成功");
            }else {
                ro.setSuccess(false);
                ro.setMsg("发送消息失败");
            }

        } else {
            ro.setSuccess(false);
            ro.setMsg("操作失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

    //分页查询
    @Override
    public ResultPage<ShippingRequestDetails> findByPage(String empName,String clientName,String productName, int page, int size) throws BusinessRuntimeException {
        ResultPage<ShippingRequestDetails> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ShippingRequestDetails> pages = shippingRequestDetailsDao.findSearch(empName,clientName,productName, pageable);
        if (pages!=null) {
            ro.setData(pages.getContent());
            ro.setTotal(pages.getTotalElements());
            ro.setTotalPages(pages.getTotalPages());
            ro.setSuccess(true);
        } else {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

    /*
     新增
    */
    @Override
    @Transactional
    public ResultObject<ShippingRequestDetails> add(long productDetailsId, ShippingRequestDetails shippingRequestDetails, String type) throws BusinessRuntimeException {
        ResultObject<ShippingRequestDetails> ro = new ResultObject<>();
        shippingRequestDetails.setSalePlanId(productDetailsId);
        shippingRequestDetails.setEnabled(true);
        shippingRequestDetails.setCreateDate(new Date());
        shippingRequestDetails.setDeliveryStatus(1);
        shippingRequestDetails = shippingRequestDetailsDao.saveAndFlush(shippingRequestDetails);
        List<ShippingRequestDetails> detailsList = shippingRequestDetailsDao.findbySalesId(productDetailsId);
        //更新 发货提示；
        StringBuilder sb = new StringBuilder();
        if (detailsList != null && !detailsList.isEmpty()) {
            for (int i = 0,n=detailsList.size(); i < n ; i++) {
                ShippingRequestDetails srd = detailsList.get(i);
                sb.append(simpleDateFormat.format(new Date()))
                        .append(",数量:")
                        .append(srd.getPlanNumber())
                        .append(";");
            }
        }
        int result=0;
        logger.info(type);
        if(type.equals("productPlan")){
//            salesPlanId为生产计划ID
            result=productionPlanDetailsDao.changeSend(shippingRequestDetails.getExpectLevel(), productDetailsId, sb.toString(), shippingRequestDetails.getPlanNumber(),shippingRequestDetails.getId());
        }else{
            result = salesPlanDao.changeSend(shippingRequestDetails.getExpectLevel(), productDetailsId, sb.toString(), shippingRequestDetails.getPlanNumber(),shippingRequestDetails.getId());
        }
        //修改对应销售计划发货请求紧急程度

        if (result > 0) {
            ro.setSuccess(true);
            ro.setMsg("新建发货请求成功");
            //发送消息
            if(type.equals("productPlan")){
                productDetailsId= productionPlanDetailsDao.findById(productDetailsId).getSalesPlan().getId();
            }
            int re = setMessage(productDetailsId, shippingRequestDetails.getId(), "新增发货请求", "ShippingRequestDetails");
            if(re>0) {
                ro.setSuccess(true);
                ro.setMsg("发送消息成功");
            }else {
                ro.setSuccess(false);
                ro.setMsg("发送消息失败");
            }
        } else {
            ro.setSuccess(false);
            ro.setMsg("新建发货请求失败");
        }
        return ro;
    }
    @Override
    public ResultObject<ShippingRequestDetails> findStatus() throws BusinessRuntimeException {
        ResultObject<ShippingRequestDetails> ro = new ResultObject<>();
        Set<ShippingRequestDetails> status = shippingRequestDetailsDao.findStatus();
        if (status != null) {
            List<ShippingRequestDetails> list = new ArrayList<>(status);
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

    @Override
    public ResultObject<ShippingRequestDetails> findsById(long id) {
        ResultObject<ShippingRequestDetails> ro=new ResultObject<>();
        ShippingRequestDetails srd=shippingRequestDetailsDao.findById(id);
         Map<String, Object>map= new HashMap<>();
         map.put("shipdetail",srd);
        if (srd!=null){
           ro.setSuccess(true);
           ro.setRoot(map);
        }else {
            ro.setSuccess(false);
        }

        return ro;
    }

    @Override
    @Transactional
    public ResultObject<ShippingRequestDetails> revoke(long id, String type) {
        ResultObject<ShippingRequestDetails> ro=new ResultObject<>();
       int  re= shippingRequestDetailsDao.revoke(id);
        int  rs=0;
       if (type.equals("plan")){
           rs=shippingRequestDetailsDao.revokePlan(id);
       }else{
            rs=shippingRequestDetailsDao.revokeSale(id);
       }
         if(re>0&&rs>0){
             ro.setSuccess(true);
             ro.setMsg("撤销成功");
         }else{
             ro.setSuccess(false);
             ro.setMsg("撤销失败！");
         }
        return ro;
    }



    //发送消息
    private int setMessage(long salesPlanId,long id,String con,String className){

        String userName = UsersUtils.getCurrentHr().getEmpName();
        StringBuilder title = new StringBuilder();
        title.append(userName);
        title.append(con);
        int re=0;
        StringBuilder content = new StringBuilder();
        if(salesPlanId!=0){
        SalesPlan salesPlan = salesPlanDao.findById(salesPlanId);
        content.append("客户名称:")
                .append(salesPlan.getClientName())
                .append("产品名称:")
                .append(salesPlan.getProductName())
                .append("数量:")
                .append(salesPlan.getQuantity());
         re= msgUtil.sendMsg(title.toString(), content.toString(), className,id, message.UserIds());

        }
        return re;
       }
    private boolean findLately(long salesPlanId,long id){
        boolean b = false;
        ShippingRequestDetails sales = shippingRequestDetailsDao.findLately(salesPlanId);
        if(id==sales.getId()){
            b=true;
        }
        return b;
    }

}
