package com.zt.serviceImp;

import com.zt.common.Message;
import com.zt.common.MessageUtil;
import com.zt.common.UsersUtils;
import com.zt.common.Utils;
import com.zt.dao.*;
import com.zt.model.*;
import com.zt.po.*;
import com.zt.service.ProductionPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wl
 * @date 2019年5月6日 
 */
@Service("productionPlanService")

//@CacheConfig(cacheNames = "zt_caches_marketingplan")
public class ProductionPlanServiceImp implements ProductionPlanService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MessageUtil msgUtil;
    @Autowired
    ProductionPlanDao productionPlanDao;
    @Autowired
    ProductionPlanDetailsDao productionPlanDetailsDao;
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
    ProductDao productDao;
    @Autowired
    ProductionPlanSerialNumberDao productionPlanSerialNumberDao;
    @Autowired
    SalesPlanDao salesPlanDao;
    @Autowired
    Message message;
    @Autowired
    FinishedProductDao finishedProductDao;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");


	/*
       分页查询所有
     */
    @Override
    public ResultPage<ProductionPlan> findByPage(int page, int size, String productName, String empName, String endDate, String startDate, Integer status, Integer contractStatus,String clientName) throws BusinessRuntimeException {
        ResultPage<ProductionPlan> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductionPlan> pages = productionPlanDao.findSearch(productName, empName,endDate, startDate, status, contractStatus,clientName, pageable);
//        Page<ProductionPlan> pages = productionPlanDao.findSearch(status,  pageable);
        List<ProductionPlan> plan=pages.getContent();
//        if(plan!=null&&plan.size()>0&&status!=null){
            //过滤子状态中与status不同的
//            for (int i =  plan.size()-1; i >=0; i--) {
//                ProductionPlan elemet=plan.get(i);
//                List<ProductionPlanDetails> detailsList=elemet.getDetails();
//                int n= detailsList.size();
//                if(n>0){
//                    for (int j = n-1; j >=0 ; j--) {
//                        ProductionPlanDetails detail= detailsList.get(j);
//                        if(detail.getStatus()!=status){
//                            detailsList.remove(j);
//                        }
//                    }
//                }
//            }
//        }

        if (pages.getContent()!=null) {
            int totals = (int) pages.getTotalElements();
            ro.setData(plan);
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
    public ResultPage<ProductionPlanDetails> findByNewPlan(int page, int size, String productName, String empName, String endDate, String startDate, String clientName) {
        ResultPage<ProductionPlanDetails> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<ProductionPlanDetails> pages = productionPlanDetailsDao.findNewPlan( productName, empName,endDate, startDate, clientName, pageable);
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
     * 删除
     */
    @Override
    public ResultObject<ProductionPlan> deletDel(long id) throws BusinessRuntimeException {
        ResultObject<ProductionPlan> ro = new ResultObject<>();
        if (id > 0) {
            ProductionPlan productionPlan = productionPlanDao.findById(id);
            productionPlan.setEnabled(false);
//            List<ProductionPlanDetails> productionPlanDetailsList = productionPlan.getDetails();
//            if (productionPlanDetailsList.size() > 0) {
//                for (int i = 0, n = productionPlanDetailsList.size(); i < n; i++) {
//                    ProductionPlanDetails productionPlanDetails = productionPlanDetailsList.get(i);
//                    productionPlanDetails.setEnabled(false);
//                    List<ProductionPlanSerialNumber> productionPlanDetailsSerialNumbersList = productionPlanDetails.getSerialNumbers();
//                    if (productionPlanDetailsSerialNumbersList.size() > 0) {
//                        for (int y = 0, m = productionPlanDetailsSerialNumbersList.size(); y < m; y++) {
//                            ProductionPlanSerialNumber productionPlanSerialNumber = productionPlanDetailsSerialNumbersList.get(y);
//                            productionPlanSerialNumber.setEnabled(false);
//                        }
//                    }
//                }
//            }
            productionPlan = productionPlanDao.saveAndFlush(productionPlan);
            if (productionPlan != null) {
                ro.setSuccess(true);
                ro.setMsg("删除成功");
                //发消息
                String userName = UsersUtils.getCurrentHr().getEmpName();
//                List<ProductionPlanDetails> marPlanDetailsList = productionPlan.getDetails();
                String cliName = null;
                Date delDate = new Date();
                StringBuilder title = new StringBuilder();
                title.append(userName);
                title.append("撤销了,客户为:");
                title.append(cliName);
                title.append("的销售订单");

                StringBuilder content = new StringBuilder();
                content.append("撤销时间");
                content.append(delDate);
                int re = msgUtil.sendMsg(title.toString(), content.toString(), "ProductionPlan", id, message.UserIds());
                if (re > 0) {
                    ro.setSuccess(true);
                    ro.setMsg("发送撤销消息成功");
                } else {
                    ro.setSuccess(false);
                    ro.setMsg("发送撤销消息失败");
                }
                ro.setSuccess(true);
                ro.setMsg("操作成功");
            } else {
                ro.setSuccess(false);
                ro.setMsg("删除失败");
            }
        } else {
            ro.setSuccess(false);
            ro.setMsg("操作失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

    /*
    页面初始化
     */
    @Override
    public ResultObject<ProductionPlan> basedata() throws BusinessRuntimeException {
        ResultObject<ProductionPlan> ro = new ResultObject<>();
        Map<String, Object> root = new HashMap<>();
        Set<Object[]> products = productDao.findProduct();
        Set<Client> clients = clientDao.findClient();
        Set<Employee> emps= empDao.findAllEmps();
        if (products != null && clients != null) {
            root.put("clients", clients);
            root.put("products", products);
            root.put("emps", emps);
            ro.setRoot(root);
            ro.setSuccess(true);
        } else {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

    /**
     *根据客户Id查询最新详情
     *  查询逻辑 ： 在 公司有分公司情况下 查询所有所有分公司与总公司的所有最近编码 用时间 排序 取最近三对 排序
     *在公司无子公司情况下 只查询子公司的最近三 次的值
     *无论在使用子公司编码序列还是 总公司编码序列 都有可能 不连续  所有的值最终都由用户设置，实现用户最大自由度；
     */
    @Override
    public ResultObject<ProductionPlanDetails> findPlanDetails(long clientId) throws BusinessRuntimeException {
        ResultObject<ProductionPlanDetails> ro = new ResultObject<>();
        //根据客户Id查询是否有父类
        Client client = clientDao.findById(clientId);
          List<Client> children=client.getChild();
        /**
         * 此刻用户选 的为总公司 查询该公司下所有子公司 最近三次编码
         */
          /**
         * 此刻用户选 的为分公司  仅查询该公司 最近三次编码
         */
        List<Long> childIds=new ArrayList<>();
        childIds.add(client.getId());
        if(children.size()>0){
            Iterator<Client> it=children.iterator();
            while (it.hasNext()){
                Client  c=it.next();
                childIds.add(c.getId());
            }
           // 查出最近三次的数据
        }
        List<ProductionPlanSerialNumber>  slist= productionPlanSerialNumberDao.findallParent(childIds);
        Map<String, Object> map = new HashMap<>();
            if (slist != null) {
                map.put("clientList", slist);
                ro.setRoot(map);
                ro.setSuccess(true);
                logger.info("操作成功!");
            } else {
                ro.setSuccess(false);
                logger.info("操作失败!");
                throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
            }
        return ro;
    }

	/*
        新建生产计划
         */
    @Override
    @Transactional
    public ResultObject<ProductionPlan> addNew(ProductionPlanModel marketingPlanModel) throws Exception {
        ResultObject<ProductionPlan> ro = new ResultObject<>();
        ProductionPlanModel mo = new ProductionPlanModel();
        //销售计划单号
        //	 当天有的计划单号
        List<ProductionPlan> currentPlanList = productionPlanDao.currentPlan();
        if (currentPlanList != null && currentPlanList.size() > 0) {
            //更新
            ProductionPlan currentPlan = currentPlanList.get(0);
//            List<ProductionPlanDetails> planDetails = getPlanDetails(currentPlan, marketingPlanModel);
//            currentPlan.setDetails(planDetails);
            currentPlan.setNotes(marketingPlanModel.getNotes());
            //更新其详情
            currentPlan = productionPlanDao.saveAndFlush(currentPlan);
            if (currentPlan == null) {
                ro.setSuccess(false);
                ro.setMsg("失败！");
            } else {
                ro.setSuccess(true);
                ro.setMsg("创建成功！");
                ro = sendmessage(currentPlan,"新建一个生产计划");
            }
        } else {
            //新建；
            ProductionPlan marketingPlan = mo.v1p(marketingPlanModel);
            String planNo = productionPlanDao.findMaxPlanNo();
            String number = Utils.newPlanNo(planNo, "P");
            if (number != null) {
                marketingPlan.setPlanNo(number);
            }
            marketingPlan.setCreateDate(new Date());
            marketingPlan.setStatus(1);
            marketingPlan = productionPlanDao.save(marketingPlan);
            if (marketingPlan != null) {
                ro.setSuccess(true);
                ro.setMsg("操作成功");
                ro = sendmessage(marketingPlan,"新建一个生产计划");
            } else {
                ro.setSuccess(false);
                ro.setMsg("操作失败");
                throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
            }
        }

        return ro;
    }

    private ResultObject<ProductionPlan> sendmessage(ProductionPlan marketingPlan,String content) {
        ResultObject<ProductionPlan> ro = new ResultObject<>();
        String userName = UsersUtils.getCurrentHr().getEmpName();
        StringBuilder title = new StringBuilder();
        title.append(userName);
        title.append(content);
        int re = msgUtil.sendMsg(title.toString(), title.toString(), "ProductionPlan", marketingPlan.getId(), message.UserIds());
        if (re > 0) {
            ro.setSuccess(true);
            ro.setMsg("发送消息成功");
        } else {
            ro.setSuccess(false);
            ro.setMsg("发送消息失败");
        }
        return ro;
    }
//	private List<ProductionPlanDetails>  getPlanDetails(ProductionPlan marketingPlan,ProductionPlanModel productionPlanModel) {
//		ProductionPlanDetailsModel productionPlanDetailsModel = new ProductionPlanDetailsModel();
////		List<ProductionPlanDetails> productionPlanDetailsList =marketingPlan.getDetails();
//		String  moStr=productionPlanModel.getProductionPlanDetails();
//         logger.info("isnull ?"+moStr);
//		if (null!=moStr&&!moStr.equals("")) {
//		JSONArray array = JSONArray.fromObject(moStr);
//		if (array.size() > 0) {
//			for (int i = 0, n = array.size(); i < n; i++) {
//				JSONObject object = array.getJSONObject(i);
//				ProductionPlanDetailsModel detailsModel = new ProductionPlanDetailsModel();
//                String   idStr=String.valueOf(object.get("id")) ;
//                if(!(idStr.equals("null")||idStr.equals(0))){
//                    detailsModel.setId(Long.valueOf(idStr));
//                }
//
//				detailsModel.setCreateDate(new Date());
//                Float actualQuantity = Float.valueOf(String.valueOf(object.get("actualQuantity")));
//                detailsModel.setActualQuantity(actualQuantity);
//				detailsModel.setNote(String.valueOf(object.get("note")));
//				detailsModel.setMark(String.valueOf(object.get("mark")));
//				Long salesPlanId = Long.valueOf(String.valueOf(object.get("salesPlanId")));
//                detailsModel.setProductionPlanSerialNumber(String.valueOf(object.get("productionPlanSerialNumber")));
//				Integer status = Integer.valueOf(String.valueOf(object.get("status")));
//                if(salesPlanId!=null&&salesPlanId>0) {
//                    SalesPlan salesPlan = salesPlanDao.findById((long) salesPlanId);
//                    if (status != null && status > 0) {
//                        detailsModel.setStatus(status);
//                        salesPlanDao.updateNextQuantity(actualQuantity, salesPlanId);
//                        salesPlanDao.updatePlanStatus(status,salesPlanId);
//                    }
//                    detailsModel.setSalesPlanId(salesPlanId);
//                    ProductionPlanDetails planDetails = productionPlanDetailsModel.v2p(detailsModel);
//                    planDetails.setStatus(1);
//                    if(salesPlan.getStatus()!=2){
//                        salesPlanDao.updateStatus(2,salesPlanId);
//                    }
//                    List<ProductionPlanSerialNumber> serialNumberList = new ArrayList<>();
//                    ProductionPlanSerialNumberModel serialNumberModel = new ProductionPlanSerialNumberModel();
//                             logger.info(detailsModel.getProductionPlanSerialNumber());
//                    JSONArray arr = JSONArray.fromObject(detailsModel.getProductionPlanSerialNumber());
//                    if (arr!=null&&arr.size() > 0) {
//                        for (int j = 0, m = arr.size(); j < m; j++) {
//                            JSONObject ob = arr.getJSONObject(j);
//                            ProductionPlanSerialNumberModel numberModel = new ProductionPlanSerialNumberModel();
//                            numberModel.setEndNo(String.valueOf(ob.get("endNo")));
//                            numberModel.setNotes(String.valueOf(ob.get("note")));
//                            numberModel.setStartNo(String.valueOf(ob.get("startNo")));
//                            numberModel.setId(Long.valueOf(String.valueOf(ob.get("id"))));
//                            ProductionPlanSerialNumber mm = serialNumberModel.v2p(numberModel);
//                            SalesPlan sales = salesPlanDao.findById((long) salesPlanId);
//                            mm.setProductId(sales.getProId());
//                            mm.setCreateDate(new Date());
//                            mm.setProdutionPlan(planDetails);
//                            mm.setClientId(sales.getClientId());
//                            serialNumberList.add(mm);
//                        }
//                    }
//                    if(finishedProductDao.findSalesPlanId(salesPlanId).size()==0){
//                        //新建成品库
//                        FinishedProduct finishedProduct = new FinishedProduct();
//                        finishedProduct.setSalesPlanId(salesPlanId);
//                        finishedProduct.setSalesPlan(salesPlan);
//                        finishedProduct.setProNumber(0);
//                        finishedProduct.setProduct(salesPlan.getProduct());
//                        finishedProduct.setProductId(salesPlan.getProId());
//                        finishedProduct.setCreateDate(new Date());
//                        finishedProduct.setEnabled(true);
//                        finishedProduct.setNote(salesPlan.getNotes());
//                        finishedProductDao.save(finishedProduct);
//                    }
//                    //查找该销售计划之前是否有已经下发的
//                    List<ProductionPlanDetails> detailsList = productionPlanDetailsDao.findBySalesPlanId(salesPlanId);
//                    DecimalFormat b = new DecimalFormat("#");
//                    if(detailsList.size()==0){
//                        //第一次下发计划
//                        StringBuilder sb = new StringBuilder();
//                        sb.append(simpleDateFormat.format(new Date())).append(",数量:").append(b.format(actualQuantity)).append(";");
//                        salesPlanDao.updatePlanString(sb.toString(), salesPlanId);
//                    }else{
//                        StringBuilder sb = new StringBuilder();
//                        sb.append(simpleDateFormat.format(new Date())).append(",数量").append(b.format(actualQuantity)).append(";");
//                        String pp = salesPlan.getPlanString()+sb.toString();
//                        salesPlanDao.updatePlanString(pp, salesPlanId);
//                    }
//                    planDetails.setSalesPlan(salesPlan);
//                    planDetails.setSerialNumbers(serialNumberList);
//                    planDetails.setEmployeeId(UsersUtils.getCurrentHr().getEmpId());
//                    planDetails.setEmployee(empDao.findById(UsersUtils.getCurrentHr().getEmpId()));
//                    planDetails.setSalesPlanId(salesPlanId);
////                    设置更新起始时间创建的当前系统时间
//                    planDetails.setUpdateDate(new Date());
//                    //设置起始请求状态为 普通发货请求
//                    planDetails.setExpectLevel(4);
//                    planDetails.setSalesPlan(salesPlanDao.findById((long) salesPlanId));
////                    planDetails.setProductionPlan(marketingPlan);
////                    productionPlanDetailsList.add(planDetails);
//                 }
//                }
//		}
//
//		}
//		return productionPlanDetailsList;
//	}
    /*
    生产计划的修改
     */
    @Override
    @Transactional
    public ResultObject<ProductionPlan> updatePlan(long id, String notes) throws BusinessRuntimeException {
        ResultObject<ProductionPlan> ro = new ResultObject<>();
        Integer update = productionPlanDao.update(notes, id);
        if (update > 0) {
            ro.setSuccess(true);
            ro.setMsg("修改成功");

        } else {
            ro.setSuccess(false);
            ro.setMsg("修改失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }


    @Override
    public ResultObject<ProductionPlan> findStatus() throws BusinessRuntimeException {
        ResultObject<ProductionPlan> ro = new ResultObject<>();
        Set<ProductionPlan> status = productionPlanDao.findStatus();
        if(status!=null){
            List<ProductionPlan> list = new ArrayList<>(status);
            ro.setData(list);
            logger.info("成功");
            ro.setSuccess(true);
        }else{
            logger.info("查询失败！");
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;

    }
}
