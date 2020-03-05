package com.zt.serviceImp;

import com.zt.common.Message;
import com.zt.common.MessageUtil;
import com.zt.common.UsersUtils;
import com.zt.common.Utils;
import com.zt.constant.DepmentAndPosCode;
import com.zt.constant.ProductProcessCode;
import com.zt.dao.*;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.*;
import com.zt.service.ProductManageDetailsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wl
 * @date 2019年5月17日
 */
@Service("productManageDetailsService")
public class ProductManageDetailsServiceImp implements ProductManageDetailsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ProductManageDetailsDao productManageDetailsDao;
    @Autowired
    ProductManageDao productManageDao;
    @Autowired
    SysUnitDao sysUnitDao;
    @Autowired
    ProductProcessDao productProcessDao;
    @Autowired
    MiddleProductDao middleProductDao;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    MidProductOutRecievingDao midProductOutRecievingDao;
    @Autowired
    ProductManageProgressDao productManageProgressDao;
    @Autowired
    MidProgressRecievingDao midProgressRecievingDao;
    @Autowired
    FinishedProductDao finishedProductDao;
    @Autowired
    FinishedRecievingDao finishedRecievingDao;
    @Autowired
    Message message;
    @Autowired
    MessageUtil msgUtil;
    @Autowired
    SalesPlanDao salesPlanDao;
    @Autowired
    ProductPreProcessDao productPreProcessDao;


    @Autowired
    ProductionPlanDetailsDao productionPlanDetailsDao;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");

    /*
    分页查询
     */
    @Override
    public ResultPage<ProductManageDetails> findSearch(int page, int size) throws BusinessRuntimeException {
        ResultPage<ProductManageDetails> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductManageDetails> pages = productManageDetailsDao.findSearch(pageable);
        if (pages != null) {
            int totals = (int) pages.getTotalElements();
            ro.setData(pages.getContent());
            ro.setTotalPages(pages.getTotalPages());
            ro.setTotal(totals);
            ro.setSuccess(true);
        } else {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
        }

        return ro;
    }

    @Override
    public ResultPage<ProductManageDetails> findByMangeId(long manageId, int page, int size, String start, String end) {
        ResultPage<ProductManageDetails> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        Map<String, String> stringStringMap = Utils.updateTime(start, end);
        start = stringStringMap.get("0");
        end = stringStringMap.get("1");

        Page<ProductManageDetails> pages = productManageDetailsDao.findByMangeId(manageId,start,end,pageable);
        if (pages != null) {
            ro.setTotal(pages.getTotalElements());
            ro.setData(pages.getContent());
            ro.setTotalPages(pages.getTotalPages());
            ro.setSuccess(true);
        } else {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
        }

        return ro;
    }

    /**
     * 用于添加生产管理进度的简化版
     * 该方法用于保存成品数量
     *
     * @param manageId      生产管理Id
     * @param manageDetails 生产管理详情（生产步骤中最后一步完成 的数量）
     * @return
     */
    @Override
    @Transactional
    public ResultObject<ProductManageDetails> addFinish(long manageId, ProductManageDetails manageDetails) {
        //保存完成数量
        ResultObject<ProductManageDetails> ro = new ResultObject<>();
        //半成品数量减少
        //半成品出库记录增加
        ProductManage productManage=productManageDao.findById(manageId);
        Employee employee =getCurrentEmployee();
        Product product=productManage.getPlanDetails().getSalesPlan().getProduct();
        //新增数据保存
        manageDetails=getmanageData(manageDetails,productManage);
        manageDetails=productManageDetailsDao.saveAndFlush(manageDetails);
        float qualifiedNo = manageDetails.getQualifiedNo();
        ro = updateMiddleProductQuantity(ro,manageDetails.getId(),product,qualifiedNo, manageDetails.getNotes(), employee, DepmentAndPosCode.POSITIVE_NUMBER);


        //成品库数量增加
        //成品库入库记录增加
        ProductionPlanDetails planDetails =productManage.getPlanDetails();
        SalesPlan salesPlan=planDetails.getSalesPlan();
        ro = updateFinishProduct(ro,manageDetails,product,salesPlan,qualifiedNo,planDetails,DepmentAndPosCode.POSITIVE_NUMBER);

        return ro;
    }

    /**
    修改记录的方法
  */
    @Override
    @Transactional
    public ResultObject<ProductManageDetails> updateFinish(long manageId,ProductManageDetails manageDetails) {
        ResultObject<ProductManageDetails> ro=new ResultObject<>();
        if(manageDetails.getId()!=0){
            ProductManageDetails oldData=productManageDetailsDao.findById(manageDetails.getId());
             //更新 该条数据
            int result=productManageDetailsDao.updateData(manageDetails.getQualifiedNo(),manageDetails.getNotes(),manageDetails.getId());

            ProductManage productManage=productManageDao.findById(manageId);
            Employee employee =productManage.getManger(); //           原数据                    跟新数据
            ProductionPlanDetails planDetails = productManage.getPlanDetails();
            SalesPlan salesPlan=planDetails.getSalesPlan();
            Product  product=planDetails.getSalesPlan().getProduct();
            float qunn = manageDetails.getQualifiedNo()-oldData.getQualifiedNo();
            if(result>0){
                //半成品数量更新
                //半成品出库记录更新
                ro = updateMiddleProductQuantity(ro,manageDetails.getId(),product,qunn, manageDetails.getNotes(), employee, DepmentAndPosCode.UPDATE_NUMBER);
                //成品库数量增加
                //成品库入库记录增加
                ro = updateFinishProduct(ro,manageDetails,product,salesPlan,qunn,planDetails,DepmentAndPosCode.UPDATE_NUMBER);

            }else{
                ro.setSuccess(false);
                ro.setMsg("更新失败");
            }
        } else{
            ro.setSuccess(false);
            ro.setMsg("请重试");
        }


        return ro;
    }

    /**
     * 删除记录方法
     */
    @Override
    @Transactional
    public ResultObject<ProductManageDetails> deleteFinish(long manageDetailsId) {
        ResultObject<ProductManageDetails> ro = new ResultObject<>();
        //对应的成品库中数量减少
        ProductManageDetails manageDetails = productManageDetailsDao.findById(manageDetailsId);
        //成品入库记录删除（数据库直接删除）
        float qualifiedNo = manageDetails.getQualifiedNo();
        ProductionPlanDetails planDetails = manageDetails.getProductManage().getPlanDetails();
        SalesPlan salesPlan=planDetails.getSalesPlan();
        Product product=salesPlan.getProduct();
        Employee employee =manageDetails.getProductManage().getManger();

        //半成品库数量增加
        //半成品库数量进行删除
        ro = updateMiddleProductQuantity(ro,manageDetails.getId(),product,-qualifiedNo, manageDetails.getNotes(), employee, DepmentAndPosCode.MINUS_NUMBER);
        ro = updateFinishProduct(ro,manageDetails,product,salesPlan,-qualifiedNo,planDetails,DepmentAndPosCode.MINUS_NUMBER);
        manageDetails.setUpsDate(new Date());
        manageDetails.setEnabled(false);
        manageDetails=productManageDetailsDao.saveAndFlush(manageDetails);
        if(manageDetails!=null){
            ro.setSuccess(true);
            ro.setMsg("删除成功！");
        }else{
            ro.setMsg("删除失败");
            ro.setSuccess(false);
        }

        return ro;
    }

    //获取当前登录用户对应的员工；
    private Employee getCurrentEmployee() {
        return  employeeDao.findById(UsersUtils.getCurrentHr().getEmpId());
    }
    /**
    新建
     */
    @Override
    @Transactional
    public ResultObject<ProductManageDetails> add(long manageId, String manageDetails) throws BusinessRuntimeException {
        ResultObject<ProductManageDetails> ro = new ResultObject<>();
        //获取当前登录系统的员工id
        Employee employee =getCurrentEmployee();
        ProductManage productManage = productManageDao.findById(manageId);
        if (manageDetails.length() > 0) {
            Product product = productManage.getPlanDetails().getSalesPlan().getProduct();
            JSONArray array = JSONArray.fromObject(manageDetails);
            if (array.size() > 0) {
                for (int i = 0, n = array.size(); i < n; i++) {
                    JSONObject object = array.getJSONObject(i);
                    ProductManageDetails productManageDetails = new ProductManageDetails();
                    productManageDetails.setEnabled(true);
                    //车间名称
                    productManageDetails.setPlant(String.valueOf(object.get("plant")));
                    //合格数                                                        qualifiedNo
                    String qulified = String.valueOf(object.get("qualifiedNo"));
                    Float qualifiedNo = 0f;
                    if (qulified != null && qulified.length() > 0) {
                        qualifiedNo = Float.valueOf(qulified);
                    }
                    productManageDetails.setQualifiedNo(qualifiedNo);
                    //报废数
                    String juked = String.valueOf(object.get("junkedNo"));
                    Float junkedNo = 0f;
                    if (juked != null && juked.length() > 0) {
                        junkedNo = Float.valueOf(juked);
                    }
                    productManageDetails.setJunkedNo(junkedNo);
                    //计量单位
                    Long unitsId = Long.valueOf(String.valueOf(object.get("unitsId")));
                    if (unitsId > 0) {
                        productManageDetails.setUnitsId(unitsId);
                        productManageDetails.setSysUnit(sysUnitDao.findById((long) unitsId));
                    }
                    String notes = String.valueOf(object.get("notes"));
                    productManageDetails.setNotes(notes);
                    productManageDetails.setCreateDate(new Date());
                    productManageDetails.setUpsDate(new Date());
                    productManageDetails.setProductManage(productManage);
                    //工序步骤
                    long productProcessId = Long.valueOf(String.valueOf(object.get("productProcessId")));
                    if (productProcessId > 0) {
                        productManageDetails.setProductProcessId(productProcessId);
                        ProductProcess productProcess = productProcessDao.findById(productProcessId);
                        productManageDetails.setProductProcess(productProcess);
                        productManageDetails = productManageDetailsDao.save(productManageDetails);
                        if (productManageDetails != null) {
                            if (productManage.getMangeStatus() != 2) {
                                productManageDao.upStatusId(2, manageId);
                            }
                            logger.info("添加详情成功");
                            switch (productProcess.getProductProcessCode()) {
                                case ProductProcessCode.FIRST_STEP:
                                    ro = firstStep(productManageDetails,employee,ro);
                                    break;
                                case ProductProcessCode.END_STEP:
                                    ro = finishedStep(productManageDetails, ro);
                                    break;
                                case ProductProcessCode.MIDDLE_STEP:  //修改汇总表数据 和添加周转表数据
                                    ro = savePoolAndRecord(productManageDetails, employee,  ro);
                                    break;
                                default:
                                    break;
                            }
                            ro.setSuccess(true);
                        } else {
                            ro.setSuccess(false);
                            logger.info("添加详情失败");
                        }

                    } else {
                        ro.setMsg("请为该产品添加工序！！！");
                        ro.setSuccess(false);
                    }
                }
            }
        } else {
            ro.setMsg("请重新选择！");
            ro.setSuccess(false);
        }
        return ro;
    }


    /**
     * 第一步
     *
     * @param productManageDetails
     * @param ro
     * @return
     */
    private ResultObject<ProductManageDetails> firstStep( ProductManageDetails productManageDetails,Employee employee,  ResultObject<ProductManageDetails> ro) {
        //如果是第一步工序
        //半成品库减少
        //并做半成品出库记录
        //获取半成品库
        float qualifiedNo=productManageDetails.getQualifiedNo();
        float junkedNo=productManageDetails.getJunkedNo();
        String notes=productManageDetails.getNotes();
        qualifiedNo +=junkedNo;
        Product product=productManageDetails.getProductManage().getPlanDetails().getSalesPlan().getProduct();
        long productManageDetailsId = productManageDetails.getId();
        ro = updateMiddleProductQuantity(ro,productManageDetailsId,product, qualifiedNo, notes, employee,DepmentAndPosCode.POSITIVE_NUMBER);
        return savePoolAndRecord(productManageDetails, employee, ro);
    }


    //修改半成品库数量 和 半成品库 出入库记录

    private ResultObject<ProductManageDetails> updateMiddleProductQuantity(ResultObject<ProductManageDetails> ro,long productManageDetailsId, Product product, float quan, String notes, Employee employee,String type) {
        List<MidMaterial> midMaterialList = product.getMidMaterials();
        if (midMaterialList.size() > 0) {
            for (int j = 0, m = midMaterialList.size(); j < m; j++) {
                MidMaterial midMaterial = midMaterialList.get(j);
                //产品和半成品的关系比
                Integer q = midMaterial.getQuantity();
                //获取半成品仓库
                long midProductId = midMaterial.getMidProductId();
                if (midProductId > 0) {
                    //将数据库修改成该数量
                    float quu = quan * q;
                    //sql语句修改
                    Integer update = middleProductDao.update(quu, midProductId);
                    MiddleProduct middleProduct = middleProductDao.findById(midProductId);
                    if (update > 0) {
                        ro.setSuccess(true);
                        logger.info("半成品库存已修改");
                        if(DepmentAndPosCode.POSITIVE_NUMBER.equals(type)){
                            //添加半成品出库记录
                            ro = midproductReceived(ro,productManageDetailsId,middleProduct, quu, notes, employee);
                            //发警戒通知
                            ro=midMaterialSendMsg(middleProduct,ro);
                        }else if(DepmentAndPosCode.MINUS_NUMBER.equals(type)){
                            //删除对应的半成品出库记录
                            int a = midProductOutRecievingDao.deleteByManageId(productManageDetailsId);
                            if(a>0){
                                ro.setSuccess(true);
                                logger.info("记录删除成功");
                            }else{
                                ro.setSuccess(false);
                                logger.info("记录删除失败");
                            }
                        }else{
                            //更新出入库记录
                            int b = midProductOutRecievingDao.updateReciev(quu,productManageDetailsId);
                            if(b>0){

                                ro=midMaterialSendMsg(middleProduct,ro);
                                logger.info("记录更新成功");
                            }else{
                                ro.setSuccess(false);
                                logger.info("记录更新失败");
                            }
                        }
                    } else {
                        ro.setSuccess(false);
                        logger.info("半成品库存修改失败");
                    }
                }else{
                    logger.info("未关联半成品库");
                }

            }
        }
        return ro;
    }

    /**
     *  发送半成品储量不足警告
     * @param middleProduct
     * @param ro
     * @return
     */
       private ResultObject<ProductManageDetails> midMaterialSendMsg(MiddleProduct middleProduct, ResultObject<ProductManageDetails> ro){
            if (middleProduct.getStoreStatue() <= middleProduct.getProductNo()) {
                //发送消息提示该半成品库存已到警戒线
                StringBuilder sb = new StringBuilder();
                sb.append("半成品:").append(middleProduct.getName()).append(",库存较少,剩余库存数量为:").append(middleProduct.getProductNo()).append(",请及时补充库存");
                int mess = msgUtil.sendMsg("库存不足", sb.toString(), "MiddleProduct", middleProduct.getId(), message.UserIds());
                if (mess > 0) {
                    ro.setSuccess(true);
                    logger.info("消息发送成功");
                } else {
                    ro.setSuccess(false);
                    logger.info("消息发送失败");
                }
            }
            return ro;
        }

    /**
     * 生产管理详情添加最后一步
     *
     * @param productManageDetails
     * @param ro
     * @return
     */
    private ResultObject<ProductManageDetails> finishedStep(ProductManageDetails productManageDetails, ResultObject<ProductManageDetails> ro) {
        //最后一步工序
        //修改半成品的库存
        ProductManage  productManage=productManageDetails.getProductManage();
        ProductionPlanDetails planDetails=productManage.getPlanDetails();
        SalesPlan salesPlan=planDetails.getSalesPlan();
        Product product=salesPlan.getProduct();
        float qualifiedNo=productManageDetails.getQualifiedNo();
        ro = updateMiddleProductQuantity(ro,productManageDetails.getId(), product, productManageDetails.getJunkedNo(), productManageDetails.getNotes(), getCurrentEmployee(),DepmentAndPosCode.POSITIVE_NUMBER);
        ro = updateFinishProduct(ro,productManageDetails,product,salesPlan,qualifiedNo,planDetails,DepmentAndPosCode.POSITIVE_NUMBER);
        return ro;
    }
    /*
    修改成品库的数量和成品的入库记录
     */
    private ResultObject<ProductManageDetails> updateFinishProduct(ResultObject<ProductManageDetails> ro,ProductManageDetails productManageDetails,Product product,SalesPlan salesPlan,float qualifiedNo,ProductionPlanDetails planDetails,String type){
        //成品库数据
        long salessId=planDetails.getId();
        List<FinishedProduct> finishedProduct = finishedProductDao.findSalesPlanId(salessId);
        FinishedProduct finishe = new FinishedProduct();
        //当成品库中无数据时 在成品库中创建记录
        if (finishedProduct.size() <= 0) {
            finishe.setEnabled(true);
            finishe.setCreateDate(new Date());
            finishe.setNote(productManageDetails.getNotes());
            finishe.setProduct(product);
            finishe.setProductId(product.getId());
            finishe.setProductionPlanDetails(planDetails);
            finishe.setProductionPlanDetailsId(planDetails.getId());
            finishe = finishedProductDao.saveAndFlush(finishe);
        } else {
            finishe = finishedProduct.get(0);
        }
//        修改成品库数据 成品库增加
        int result = finishedProductDao.updateQuantity(qualifiedNo, finishe.getId());
        if(result>0){
            switch (type){
                case DepmentAndPosCode.POSITIVE_NUMBER:
                    result = finishReceiv(finishe,productManageDetails.getId(), product, productManageDetails.getNotes(), getCurrentEmployee(), qualifiedNo);
                    //当前生产进度逻辑只需要对最后一步进行数量的添加
                    //            ro = savePoolAndRecord(productManageDetails, getCurrentEmployee(), ro);
                    break;
                case   DepmentAndPosCode.MINUS_NUMBER:
                    result = finishedRecievingDao.deleteByManageId(productManageDetails.getId());
                    break;
                default:
                    result = finishedRecievingDao.updateReciev(qualifiedNo,productManageDetails.getId());
                    break;
            }
            if(result>0){
                // 修改销售计划中生产完成数量； produtRecordStr 完成记录；
                StringBuilder sb=new StringBuilder();
                long finishSalesId=salesPlan.getId();
                List<ProductManageDetails> allDetailList=productManageDetailsDao.findBySalesId(finishSalesId);
                allDetailList.forEach(item->
                        sb.append(simpleDateFormat.format(item.getCreateDate()))
                                .append(",生产数量:")
                                .append(item.getQualifiedNo())
                                .append(";")
                );
                 String str=sb.toString();
                result=  salesPlanDao.updateProductStr(str,qualifiedNo,salessId);
                //修改生产计划中 生产完成数量
                int  res=productionPlanDetailsDao.updateAccomplishNo(qualifiedNo, planDetails.getId());
                if(result>0&&res>0){
                    ro.setSuccess(true);
                    ro.setMsg("数据修改成功！");
                }else{
                    ro.setSuccess(false);
                    ro.setMsg("数据修改失败");
                }
            }else{
                ro.setSuccess(false);
                ro.setMsg("操作失败");
            }
        }else{
            ro.setSuccess(false);
            ro.setMsg("请重试！");
        }

        return ro;
    }
    /**
     * 半成品出库记录添加
     *
     * @param ro            返回值
     * @param middleProduct 半成品
     * @param quan          总数量数量+报废数量 在这里 报废数量恒为零
     * @param notes         备注
     * @param employee      员工
     * @return ro
     */
    private ResultObject<ProductManageDetails> midproductReceived(ResultObject<ProductManageDetails> ro,long productManageDetailsId, MiddleProduct middleProduct, float quan, String notes, Employee employee) {
        MidProductOutRecieving midProductOutRecieving = new MidProductOutRecieving();
        midProductOutRecieving.setName(middleProduct.getName());
        midProductOutRecieving.setProductManageDetailsId(productManageDetailsId);
        midProductOutRecieving.setUnitName(middleProduct.getUnitName());
        midProductOutRecieving.setUnitId(middleProduct.getUnitId());
        midProductOutRecieving.setMidproduct(middleProduct);
        midProductOutRecieving.setMidProcutId(middleProduct.getId());
        midProductOutRecieving.setQuantity(quan);
        midProductOutRecieving.setVerifyStatus(1);
        midProductOutRecieving.setEnable(true);
        midProductOutRecieving.setNotes(notes);
        midProductOutRecieving.setOperatorId(employee.getId());
        midProductOutRecieving.setOperator(employee);
        midProductOutRecieving.setOperatorName(employee.getName());
        midProductOutRecieving.setCreateDate(new Date());
        midProductOutRecieving = midProductOutRecievingDao.save(midProductOutRecieving);
        if (midProductOutRecieving != null) {
            ro.setSuccess(true);
            logger.info("半成品出库记录添加成功");
            //修改进度表中数量
            //给周转表添加数据
            //修改汇总表数据 和添加周转表数据
        } else {
            logger.info("半成品出库记录添加失败");
            ro.setSuccess(false);
        }
        return ro;
    }

    /**
     * 成品表添加记录
     *
     * @param finishedProduct 成品
     * @param product         产品
     * @param notes           备注
     * @param employee        员工
     * @return
     */
    private int finishReceiv(FinishedProduct finishedProduct,long productManageDetailsId, Product product, String notes, Employee employee, float quantity) {
          int ro=0;
        FinishedRecieving finishedRecieving = new FinishedRecieving();
        finishedRecieving.setCreateDate(new Date());
        finishedRecieving.setProductManageDetailsId(productManageDetailsId);
        finishedRecieving.setEnable(true);
        finishedRecieving.setQuantity(quantity);
        finishedRecieving.setFinishedProduct(finishedProduct);
        finishedRecieving.setFinishProcutId(finishedProduct.getId());
        finishedRecieving.setName(product.getProducteName());
        finishedRecieving.setNotes(notes);
        finishedRecieving.setOperator(employee);
        finishedRecieving.setOperatorId(employee.getId());
        finishedRecieving.setOperatorName(employee.getName());
        finishedRecieving.setStoreType(2);
        finishedRecieving = finishedRecievingDao.save(finishedRecieving);
        if (finishedRecieving != null) {
           ro=1;
            logger.info("成品入库记录添加成功");
        } else {

            logger.info("成品入库记录添加失败");
        }
        return ro;
    }

    /**
     * 修改汇总表数据 和添加周转表数据
     *
     * @param employee         员工
     * @param ro               返回类
     * @return
     */
    private ResultObject<ProductManageDetails> savePoolAndRecord(ProductManageDetails productManageDetails, Employee employee, ResultObject<ProductManageDetails> ro) {
        //修改半成品库存
        ProductManage productManage=productManageDetails.getProductManage();
        long manageId =productManage.getId();
        ProductProcess productProcess=null;
        long productProcessId=0;
        if (productManageDetails.getProductProcess()!=null){
         productProcess=productManageDetails.getProductProcess();
         productProcessId=productProcess.getId();
        }
        Client client=productManage.getPlanDetails().getSalesPlan().getClient();
        String notes=productManageDetails.getNotes();
        long unitsId=productManageDetails.getUnitsId();
        long manageDetailsId=productManageDetails.getId();
        Product product=productManageDetails.getProductManage().getPlanDetails().getSalesPlan().getProduct();
          float qualifiedNo =productManageDetails.getQualifiedNo();

//        ro = updateMiddleProductQuantity(ro,product,junkedNo, notes, employee);

        int updateQuantity = productManageProgressDao.updateQuantity(qualifiedNo, manageId, productProcessId);
        if (updateQuantity > 0) {
            logger.info("进度汇总修改成功");
            StringBuilder sb = new StringBuilder();
            sb.append(product.getProducteName()).append("-");
            if (null!=productProcess) {//产品无工序的。。。。一般无可能、、、

                sb.append(productProcess.getName());
                int re = insertRecieving(sb.toString(), notes, productProcess, employee.getId(), employee, unitsId, qualifiedNo, manageDetailsId);
                if (re > 0) {
                    logger.info("周转记录添加成功");
                    //发送消息
                      ro=finishSendMsg(client, product,manageId,ro);
                } else {
                    ro.setSuccess(false);
                    logger.info("周转记录添加失败");
                }
            }else{
                ro=finishSendMsg(client, product,manageId,ro);

            }
        } else {
            ro.setSuccess(false);
            logger.info("进度汇总修改失败");
        }
        //添加周转表记录


        return ro;
    }
     private ResultObject<ProductManageDetails> finishSendMsg(Client client, Product product, long manageId,ResultObject<ProductManageDetails> ro ){

        StringBuilder sb = new StringBuilder();
         sb.append("客户:").append(client.getName())
                 .append("的产品:").append(product.getProducteName()).append("的生产进度更新");
         int rr = msgUtil.sendMsg("生产进度更新", sb.toString(), "ProductManage", manageId, message.UserIds());
         if (rr > 0) {
             ro.setSuccess(true);
             ro.setMsg("消息发送成功");
         } else {
             ro.setSuccess(false);
             ro.setMsg("消息发送失败");
         }
         return ro;
      }
    /*
    给周转表添加数据
     */
    public int insertRecieving(String name, String notes, ProductProcess productProcess, long employeeId, Employee employee, long unitsId, float quantity, long manageDetailsId) {
        int result = 0;
        MidProgressRecieving midProgressRecieving = new MidProgressRecieving();
        midProgressRecieving.setCreateDate(new Date());
        midProgressRecieving.setProductManageDetailsId(manageDetailsId);
        midProgressRecieving.setEnable(true);
        midProgressRecieving.setName(name);
        midProgressRecieving.setOperatorId(employeeId);
        midProgressRecieving.setOperator(employee);
        midProgressRecieving.setUnitId(unitsId);
        midProgressRecieving.setQuantity(quantity);
        midProgressRecieving.setUnitName(sysUnitDao.findById(unitsId).getName());
        midProgressRecieving.setNotes(notes);
        midProgressRecieving.setProductProcessCode(productProcess.getProductProcessCode());
        List<ProductionPlanDetails> byMangeDetails = productionPlanDetailsDao.findByMangeDetails(manageDetailsId);
        for (int i = 0, n = byMangeDetails.size(); i < n; i++) {
            ProductionPlanDetails productionPlanDetails = byMangeDetails.get(i);
            midProgressRecieving.setSalesPlanId(productionPlanDetails.getSalesPlanId());
            midProgressRecieving.setProductionPlanId(productionPlanDetails.getId());
        }
        midProgressRecieving = midProgressRecievingDao.save(midProgressRecieving);
        int i = updateRecieving(midProgressRecieving, productProcess.getProductProcessCode());
        if (midProgressRecieving != null && i > 0) {
            result = 1;
        }
        return result;
    }

    /*
    修改周转表
     */
    public int updateRecieving(MidProgressRecieving midProgressRecieving, String productProcessCode) {
        int result = 0;
        StringBuilder sb = new StringBuilder();
        //查询装箱操作的数据,将完成数量放在销售计划完成数量记录    还有生产计划
        List<MidProgressRecieving> midProgressRecievings = midProgressRecievingDao.findBySalesPlanId(midProgressRecieving.getSalesPlanId());
        for (int i = 0, m = midProgressRecievings.size(); i < m; i++) {
            MidProgressRecieving mid = midProgressRecievings.get(i);
            sb.append(simpleDateFormat.format(mid.getCreateDate())).append(mid.getQuantity()).append(";");
        }
        //确定是最后一步
        if (productProcessCode.equals(ProductProcessCode.END_STEP)) {
            float sumQuantity = midProgressRecievingDao.findSumQuantity(midProgressRecieving.getSalesPlanId());
            //修改销售计划和生产计划中的数量和记录
            logger.info(sb.toString());
            result = salesPlanDao.changeEndQuantity(sumQuantity, sb.toString(), midProgressRecieving.getSalesPlanId());
        }
        return result;
    }

    /**
     * 修改
     *
     * @param manageId       生产管理id
     * @param manageDetailId 生产管理详情id
     * @param quantity       合格数量
     * @param junkedNo       报废数量
     * @param plant          车间名称
     * @param notes          备注
     * @return
     * @throws BusinessRuntimeException
     */
    @Override
    @Transactional
    public ResultObject<ProductManageDetails> update(long manageId, long manageDetailId, float quantity, float junkedNo, String plant, String notes) throws BusinessRuntimeException {
        ResultObject<ProductManageDetails> ro = new ResultObject<>();
        ProductManageDetails oldDetail = productManageDetailsDao.findById(manageDetailId);
        Product product = oldDetail.getProductManage().getPlanDetails().getSalesPlan().getProduct();
        ProductProcess productProcess = oldDetail.getProductProcess();
        long employeeId = UsersUtils.getCurrentHr().getEmpId();
        Employee employee = employeeDao.findById(employeeId);
        ProductManageDetails newDetail=new ProductManageDetails();
        newDetail.setNotes(notes);
        newDetail.setQualifiedNo(quantity);
        newDetail.setJunkedNo(junkedNo);
        newDetail.setPlant(plant);
        newDetail.setUpsDate(new Date());
        newDetail.setId(oldDetail.getId());
        newDetail.setCreateDate(oldDetail.getCreateDate());
        newDetail.setUnitsId(oldDetail.getUnitsId());
        if(0!=oldDetail.getProductProcessId()){
        newDetail.setProductProcessId(oldDetail.getProductProcessId());
        newDetail.setProductProcess(oldDetail.getProductProcess());
        }
        newDetail.setExamineName(oldDetail.getExamineName());
        newDetail.setEnabled(true);
         newDetail = productManageDetailsDao.saveAndFlush(oldDetail);
        if (newDetail != null) {
            switch (productProcess.getProductProcessCode()) {
                case ProductProcessCode.FIRST_STEP:
                    //第一步的修改
                    ro = firstUpdate(oldDetail,newDetail, ro);
                    break;
                case ProductProcessCode.END_STEP:
                    ro = endUpdate(oldDetail,newDetail, ro);
                    break;
                case ProductProcessCode.MIDDLE_STEP:
                    ro = updateMiddleProductQuantity(ro,manageDetailId, product, junkedNo, notes, employee,DepmentAndPosCode.POSITIVE_NUMBER);
                    break;
                default:break;
            }
            //修改汇总表数据和修改周转表数据
            int updateQuantity = productManageProgressDao.updateQuantity(quantity, manageId, productProcess.getId());

            int update = midProgressRecievingDao.update(quantity, manageDetailId);
            if (updateQuantity > 0 && update > 0) {
                logger.info("进度汇总和周转表修改成功");
                ro.setSuccess(true);
                ro.setMsg("修改成功");
            } else {
                ro.setSuccess(false);
                logger.info("进度汇总和周转表修改失败");
            }

        } else {
            ro.setSuccess(false);
            ro.setMsg("操作失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }



        return ro;
    }


    /**
     * 修改第一步工序
     *
     * @param oldDetail 生产管理
     * @param newDetail   第一步中之前的数据
     * @param ro   第一步中修改的数据
     * @return
     */
    public ResultObject<ProductManageDetails> firstUpdate(ProductManageDetails oldDetail,ProductManageDetails newDetail, ResultObject<ProductManageDetails> ro) {

        Product product= oldDetail.getProductManage().getPlanDetails().getSalesPlan().getProduct();
        List<MidMaterial> midMaterialList =product.getMidMaterials();
        float newQuantity=newDetail.getQualifiedNo();
        float oldQuantity=oldDetail.getQualifiedNo();
        String notes=newDetail.getNotes();
        Float interpolation = newQuantity - oldQuantity;
        if(midMaterialList.size()>0){
        for (int j = 0, m = midMaterialList.size(); j < m; j++) {
            MidMaterial midMaterial = midMaterialList.get(j);
            //产品和半成品的关系比
            Integer q = midMaterial.getQuantity();
            //获取半成品仓库
            long midProductId = midMaterial.getMidProductId();
            if (midProductId > 0) {
                MiddleProduct middleProduct = middleProductDao.findById(midProductId);
                //将数据库修改成该数量
                float v = middleProduct.getProductNo() + interpolation * q;
                //修改半成品库的数量
                ro = updateMiddleProductQuantity(ro,newDetail.getId(), product, v, notes, getCurrentEmployee(),DepmentAndPosCode.UPDATE_NUMBER);
            }
        }
        }
        return ro;
    }

    /**
     * @return
     */
    public ResultObject<ProductManageDetails> endUpdate(ProductManageDetails oldDetail,ProductManageDetails newDetail, ResultObject<ProductManageDetails> ro) {
        //修改半成品库
        float newQuantity=newDetail.getQualifiedNo();
        logger.info(newDetail.getQualifiedNo()+"");
        logger.info(oldDetail.getQualifiedNo()+"");
        float oldQuantity=oldDetail.getQualifiedNo();
        float junkedNo=newDetail.getJunkedNo()-oldDetail.getJunkedNo();
        String notes=newDetail.getNotes();
        ProductManage productManage=newDetail.getProductManage();
        SalesPlan salesPlan=productManage.getPlanDetails().getSalesPlan();
        Product product=salesPlan.getProduct();
        ro = updateMiddleProductQuantity(ro,newDetail.getId(), product, junkedNo, notes, getCurrentEmployee(),DepmentAndPosCode.UPDATE_NUMBER);

        Float interpolation = newQuantity - oldQuantity;
          long detailsId=   productManage.getPlanDetails().getId();
        //最后一步的修改
        //成品库数据  需要考虑 无成品的情况
        List<FinishedProduct> finishedProduct = finishedProductDao.findSalesPlanId(detailsId);
        //修改成品库数据 成品库增加
        if(finishedProduct.size()>0){

        int q = finishedProductDao.updateQuantity(finishedProduct.get(0).getProNumber() + interpolation, finishedProduct.get(0).getId());

        ro = updateFinishProduct(q, newQuantity, finishedProduct, productManage.getId(), oldDetail.getId(), oldDetail.getProductProcess());
        }
        return ro;
    }

    //修改成品库的库存
    public ResultObject<ProductManageDetails> updateFinishProduct(Integer q, float quantity, List<FinishedProduct> finishedProduct, long manageId, long manageDetailId, ProductProcess productProcess) {
        ResultObject<ProductManageDetails> ro = new ResultObject<>();
        if (q > 0) {
            logger.info("成品库存修改成功");
            logger.info(finishedProduct.size()+"");
            //修改成品入库记录
            finishedRecievingDao.updateReciev(quantity, manageDetailId);
            //修改汇总表数据和修改周转表数据
            int updateQuantity = productManageProgressDao.updateQuantity(quantity, manageId, productProcess.getId());
            int update = midProgressRecievingDao.update(quantity, manageDetailId);
            if (updateQuantity > 0 && update > 0) {
                logger.info("进度汇总和周转表修改成功");
                ro.setSuccess(true);
                //修改汇总表成功之后将销售计划中的字段进行修改
                updateRecieving(midProgressRecievingDao.findByManageDetailId(manageDetailId), productProcess.getProductProcessCode());

            } else {
                ro.setSuccess(false);
                logger.info("进度汇总和周转表修改失败");
            }
        } else {
            ro.setSuccess(false);
            logger.info("成品库存修改失败");
        }
        return ro;
    }

    /**
     * 查询每一步工序已经完成的
     * @param productManageId 生产管理Id
     * @throws BusinessRuntimeException
     * @return
     */
    @Override
    public ResultObject<ProductManageDetails> findEnd(long productManageId) throws BusinessRuntimeException {
        ResultObject<ProductManageDetails> ro = new ResultObject<>();
        Object o = productManageDetailsDao.findEnd(productManageId);
        Map<String, Object> map = new HashMap<>();
        map.put("qualifiedNo", o);
        ro.setRoot(map);
        return ro;
    }

    //生产进度数据
    private ProductManageDetails getmanageData(ProductManageDetails manageDetails,ProductManage productManage) {
        Product product=productManage.getPlanDetails().getSalesPlan().getProduct();
        manageDetails.setJunkedNo(0);
        manageDetails.setPlant("装配车间");
        manageDetails.setUpsDate(new Date());
        manageDetails.setCreateDate(new Date());
        manageDetails.setEnabled(true);
        manageDetails.setProductManage(productManage);
        manageDetails.setSysUnit(product.getSysUnit());
        manageDetails.setWorkshopManageName("车间主任");//车间负责人
        manageDetails.setExamineName("厂长");
        manageDetails.setUnitsId(product.getSysUnitId());
        //在存在
        if (productManage.getProgressId()!=0){
            ProductPreProcess progresslist = productPreProcessDao.findById(productManage.getProgressId()).get();
            List<ProductProcess> list = progresslist.getProcess();

            for (int i = 0, n = list.size(); i < n; i++) {
                ProductProcess ppp = list.get(i);
                if (ppp.getProductProcessCode().equals(ProductProcessCode.END_STEP)) {
                    manageDetails.setProductProcess(ppp);
                    manageDetails.setProductProcessId(ppp.getId());
                }
            }

        }else{
            manageDetails.setProductProcess(null);
            manageDetails.setProductProcessId(0);
        }
        return manageDetails;
    }
}
