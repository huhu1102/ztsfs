package com.zt.serviceImp;

import com.zt.common.Message;
import com.zt.common.MessageUtil;
import com.zt.common.UsersUtils;
import com.zt.common.Utils;
import com.zt.dao.*;
import com.zt.model.*;
import com.zt.po.*;
import com.zt.service.ProductionPlanDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wl
 * @date 2019年5月10日
 */
@Service("productionPlanDetailsService")
@CacheConfig(cacheNames = "zt_caches_marketingplandetails")
public class ProductionPlanDetailsServiceImp implements ProductionPlanDetailsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ProductionPlanDetailsDao productionPlanDetailsDao;
    @Autowired
    ProductionPlanSerialNumberDao productionPlanSerialNumberDao;
    @Autowired
    SalesPlanDao salesPlanDao;
    @Autowired
    ProductionPlanDao productionPlanDao;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    ColorDao  colorDao;
    @Autowired
    SpecificationDao  specificationDao;
    @Autowired
    ClientDao  clientDao;
    @Autowired
    ProductDao  productDao;
    @Autowired
    MessageUtil msgUtil;
    @Autowired
    Message message;
    @Autowired
    ProductManageDao productManageDao;


    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");

    /*
     * 分页模糊查询
     */
    @Override
    public ResultPage<ProductionPlanDetails> findSearch(int page, int size, String productName, String empName, String endDate, String startDate, String clientName,String state)
            throws BusinessRuntimeException {
        ResultPage<ProductionPlanDetails> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductionPlanDetails> pages = productionPlanDetailsDao.findSearch(productName,empName,endDate,startDate,clientName,state, pageable);

        if (pages != null) {
            ro.setData(pages.getContent());
            ro.setSuccess(true);
            ro.setTotal(pages.getTotalElements());
            ro.setTotalPages(pages.getTotalPages());
        } else {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
        }
        return ro;
    }

    /*
    根据销售计划id查
     */
    @Override
    public ResultPage<ProductionPlanDetails> findPlan(int page, int size, long salesPlanId) {
        ResultPage<ProductionPlanDetails> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductionPlanDetails> pages = productionPlanDetailsDao.findPlan(pageable, salesPlanId);
        if (pages != null) {
            int totals = (int) pages.getTotalElements();
            ro.setData(pages.getContent());
            ro.setTotal(totals);
            ro.setTotalPages(pages.getTotalPages());
            ro.setSuccess(true);
        } else {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }


        return ro;
    }
    /*
    查找所有未合并成订单的
     */

    @Override
    public ResultPage<ProductionPlanDetails> findForOrder(int page, int size, long clientId) {
        ResultPage<ProductionPlanDetails> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductionPlanDetails> pages = productionPlanDetailsDao.findForOrder(pageable, clientId);
        if (pages != null) {
            ro.setData(pages.getContent());
            ro.setTotal(pages.getTotalElements());
            ro.setSuccess(true);
            ro.setTotalPages(pages.getTotalPages());
        } else {
            ro.setSuccess(false);

            throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
        }
        return ro;
    }
    //查找 生产管理id
    @Override
    public Long findbyMangeId(long plandetailId) {
        Long managerId=productManageDao.findByManagerId(plandetailId);
         if(managerId==null){
             managerId=0L;
         }
        return managerId;
    }

    @Override
    public ResultObject<ProductionPlanDetails> findCount() {
        ResultObject<ProductionPlanDetails> ro = new ResultObject<>();
        Long byPlanStatus = salesPlanDao.findByPlanStatus();
        Long aNew = productionPlanDetailsDao.findNew();
        Long ing = productionPlanDetailsDao.findIng();
        Map<String, Long> map = new HashMap<>();
        map.put("newSalesPlanCount", byPlanStatus);
        map.put("newProductPlanCount", aNew);
        map.put("productPlaningCount", ing);
        ro.setRoot(map);
        if(byPlanStatus!=null&&aNew!=null&&ing!=null){
            ro.setSuccess(true);
        }else{
            ro.setSuccess(false);
        }
        return ro;
    }
    /**
     * 删除
     */
    @Override
    public ResultObject<ProductionPlanDetails> deletDel(long id) throws BusinessRuntimeException {
        ResultObject<ProductionPlanDetails> ro = new ResultObject<>();
        if (id > 0) {
            ProductionPlanDetails productionPlanDetails = productionPlanDetailsDao.findById(id);
            productionPlanDetails.setEnabled(false);
            List<ProductionPlanSerialNumber> productionPlanSerialNumberList = productionPlanDetails.getSerialNumbers();
            if (productionPlanSerialNumberList.size() > 0) {
                for (int i = 0, n = productionPlanSerialNumberList.size(); i < n; i++) {
                    ProductionPlanSerialNumber productionPlanSerialNumber = productionPlanSerialNumberList.get(i);
                    productionPlanSerialNumber.setEnabled(false);
                }
            }

            productionPlanDetails = productionPlanDetailsDao.saveAndFlush(productionPlanDetails);
            if (productionPlanDetails != null) {
                ro.setSuccess(true);
                ro.setMsg("删除成功");
            } else {
                ro.setSuccess(false);
                ro.setMsg("删除失败");
                throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
            }
        } else {
            ro.setMsg("操作失误,请重试~~");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

    /*
    修改
     */
    @Override
    @Transactional
    public ResultObject<ProductionPlanDetails> update(ProductionPlanDetailsModel detailsModel) throws BusinessRuntimeException {
        ResultObject<ProductionPlanDetails> ro = new ResultObject<>();
        //之前的生产计划详情
        ProductionPlanDetailsModel mo = new ProductionPlanDetailsModel();
        //删除之前与该单关联的编号
        ProductionPlanDetails  oldDetail=productionPlanDetailsDao.findById(detailsModel.getId());
        //原来 数量
        Float oldData=oldDetail.getActualQuantity();
        // 重新保存 该单编号
        ProductionPlanDetails nowDetail = mo.v2p(detailsModel);

        long salesPlanId = nowDetail.getSalesPlanId();
        nowDetail=completeDetail(nowDetail, salesPlanDao.findById(salesPlanId));
        nowDetail.setPlanNo(oldDetail.getPlanNo());
        nowDetail.setStatus(oldDetail.getStatus());

        nowDetail.setAccomplishNO(oldDetail.getAccomplishNO());
        nowDetail = productionPlanDetailsDao.saveAndFlush(nowDetail);
        if (nowDetail != null) {
        //修改销售计划中的相关数据   这里还有一个状态的修改            //更新时下发生产计划数量
            changeSaleData(detailsModel,nowDetail,"update",oldData);
            ro.setSuccess(true);
            ro.setMsg("修改成功");
        } else {
            ro.setMsg("修改失败");
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

    private ProductionPlanDetails completeDetail(ProductionPlanDetails nowDetail, SalesPlan salesPlan) {
        if(salesPlan!=null){
            nowDetail.setSalesPlan(salesPlan);
        }else{
            long salesPlanId = nowDetail.getSalesPlanId();
            salesPlan=salesPlanDao.findById(salesPlanId);
            nowDetail.setSalesPlan(salesPlan);
        }
        nowDetail.setEmployee(getEmp());
        nowDetail.setEmployeeId(getEmp().getId());
        return  nowDetail;

    }
    //获取当前登录成员员工及信息
     private Employee getEmp(){
         return employeeDao.findById(UsersUtils.getCurrentHr().getEmpId());
     }
    /**
     *  修改销售计划相关信息
     * @param detailsModel 当前前台传入 生产计划详情Model
     * @param nowDetail  当前保存好的生产计划详情实体
     * @param type  操作种类  update add
     * @param actualQuantity 当前生产计划 计划数量
     */
    private void changeSaleData(ProductionPlanDetailsModel detailsModel, ProductionPlanDetails nowDetail, String type, float actualQuantity) {
        List<ProductionPlanDetails> productionPlanDetailsList = productionPlanDetailsDao.getDetails(nowDetail.getSalesPlanId());
        StringBuilder sb = new StringBuilder();
        DecimalFormat d = new DecimalFormat("#");
        for (int i = 0, n = productionPlanDetailsList.size(); i < n; i++) {
            ProductionPlanDetails planDetails = productionPlanDetailsList.get(i);
            sb.append(d.format(planDetails.getActualQuantity()))
                    .append(",")
                    .append(simpleDateFormat.format(new Date())).append(";");
        }
        Integer salseStatus=0;
        float nowAcout=nowDetail.getActualQuantity();
        if(type.equals("update")){
            nowAcout= nowDetail.getActualQuantity()-actualQuantity;
            salseStatus=nowDetail.getSalesPlan().getStatus();
        }else{
            salseStatus=2;
        }
        salesPlanDao.updatePlanString(sb.toString(),salseStatus, detailsModel.getStatus(), nowDetail.getSalesPlanId(),nowAcout);
    }

    /*
    按状态查
     */
    @Override
    public ResultPage<ProductionPlanDetails> findBystatus(String queryName, int page, int size) throws BusinessRuntimeException {
        ResultPage<ProductionPlanDetails> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductionPlanDetails> pages = productionPlanDetailsDao.find(queryName, pageable);
        if (pages != null) {
            ro.setSuccess(true);
            ro.setData(pages.getContent());
            ro.setTotal(pages.getTotalElements());
            ro.setTotalPages(pages.getTotalPages());
        } else {
            ro.setSuccess(false);

            throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
        }
        return ro;
    }

    @Override
    @Transactional
    public ResultObject<ProductionPlanDetails> add(ProductionPlanDetailsModel detailModel) {
        ResultObject<ProductionPlanDetails> ro = new ResultObject<>();
        ProductionPlanDetails details = saveNewPlans(detailModel, null);
        String planNo = productionPlanDetailsDao.findMaxPlanNo();
        String number = Utils.newPlanNo(planNo, "P");
        if (number != null) {
            details.setPlanNo(number);
        }
        details = productionPlanDetailsDao.save(details);
        if (details != null) {
            changeSaleData(detailModel,details,"add", details.getActualQuantity());
            ro.setSuccess(true);
            ro.setMsg("保存成功！");
            //发送消息
            int re= msgUtil.sendMsg("新建销售计划和新建生产计划","", "ProductionPlanDetails",details.getId(), message.UserIds());
            if(re>0) {
                ro.setSuccess(true);
                ro.setMsg("发送消息成功");
            }else {
                ro.setSuccess(false);
                ro.setMsg("发送消息失败");
            }
        } else {
            ro.setSuccess(false);
            ro.setMsg("失败^_^");
        }
        return ro;
    }

    /**
     *  生产计划详情查看状态更改
     * @param id
     * @return
     */
    @Override
    @Transactional
    public ResultObject<ProductionPlanDetails> checkState(long id) {
        ResultObject<ProductionPlanDetails> ro= new ResultObject<>();
        if(productionPlanDetailsDao.checkState(id)>0){
            ro.setSuccess(true);
        }else{
            ro.setSuccess(false);
        }
        return ro;
    }

    @Override
    public ResultObject<ProductionPlanDetails> addSalesAndplan(ProductionPlanDetailsModel detailModel) {
        ResultObject<ProductionPlanDetails> ro = new ResultObject<>();

        SalesPlan salesPlan =saveNewSales(detailModel);
        if (salesPlan!=null){
            ro.setMsg("销售计划保存成功");
            ro.setSuccess(false);
         ProductionPlanDetails details =saveNewPlans(detailModel,salesPlan);
          if (details!=null){
               //发送消息
              int re= msgUtil.sendMsg("新建销售计划和新建生产计划","", "ProductionPlanDetails",details.getId(), message.UserIds());
              if(re>0) {
                  ro.setSuccess(true);
                  ro.setMsg("发送消息成功");
              }else {
                  ro.setSuccess(false);
                  ro.setMsg("发送消息失败");
              }
          }else{
              ro.setMsg("生产计划保存失败");
              ro.setSuccess(false);
          }
        }else {
            ro.setSuccess(false);
            ro.setMsg("销售计划保存失败");
        }

        return ro;

    }

    private ProductionPlanDetails saveNewPlans(ProductionPlanDetailsModel detailModel, SalesPlan salesPlan) {
        ProductionPlanDetailsModel mo = new ProductionPlanDetailsModel();
        ProductionPlanDetails details = mo.v2p(detailModel);
        details.setStatus(1);
        details.setManageStatus(1);
        details=completeDetail(details,salesPlan);
        details.setExpectLevel(4);
        details.setCheckStatus(1);
        details.setSalesPlanId(details.getSalesPlanId());
        String planNo = productionPlanDetailsDao.findMaxPlanNo();
        details.setPlanNo(Utils.newPlanNo(planNo, "P"));
        return productionPlanDetailsDao.save(details);
    }

    private SalesPlan saveNewSales(ProductionPlanDetailsModel detailModel) {
        SalesPlan  sale=new SalesPlan();
        sale.setCreateDate(new Date());
        sale.setUpsDate(new Date());
        sale.setStatus(2);
        sale.setColor(colorDao.findById(detailModel.getColorId()).get());
        sale.setColorId(detailModel.getColorId());
        sale.setManageStatus(0);
        sale.setEnabled(true);
        sale.setSpecification(specificationDao.findById(detailModel.getSpecId()).get());
        sale.setClient(clientDao.findById(detailModel.getClientId()));
        sale.setClientId(detailModel.getClientId());
        sale.setEditer(getEmp());
        sale.setAbbr(clientDao.findById(detailModel.getClientId()).getAbbreviation());
        sale.setExpectLevel(4);
        sale.setEditerId(getEmp().getId());
        sale.setSerialNumber(Utils.newPlanNo(salesPlanDao.findMaxPlanNo(), "S"));
        sale.setPlanStatus(detailModel.getStatus());
        sale.setNextQuantity(detailModel.getActualQuantity());
        sale.setProductNo(productDao.findById(detailModel.getProductId()).get().getProducteName());
        sale.setProduct(productDao.findById(detailModel.getProductId()).get());
        sale.setProductName(productDao.findById(detailModel.getProductId()).get().getProducteName());
        sale.setNotes(detailModel.getNote());
        sale.setProId(detailModel.getProductId());
        sale.setPcCode(detailModel.getPcCode());
        sale.setShippingStatus(1);
        sale.setSpecsId(detailModel.getSpecId());
        StringBuilder sb=new StringBuilder();
        sb.append(detailModel.getActualQuantity())
        .append(",")
        .append(simpleDateFormat.format(new Date()));
        sale.setPlanString(sb.toString());
        sale.setClientName(clientDao.findById(detailModel.getClientId()).getName());
        sale.setQuantity(detailModel.getActualQuantity());
        return salesPlanDao.saveAndFlush(sale);
    }

}
