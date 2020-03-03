package com.zt.serviceImp;

import com.zt.constant.ProductProcessCode;
import com.zt.dao.ProductDao;
import com.zt.dao.ProductPreProcessDao;
import com.zt.dao.ProductProcessDao;
import com.zt.dao.WorkstepDao;
import com.zt.model.*;
import com.zt.po.ProductPreProcess;
import com.zt.po.ProductProcess;
import com.zt.po.Workstep;
import com.zt.service.ProductPreProcessService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author wl
 * @date 2019年5月7日
 */
@Service("productPreProcessService")
@CacheConfig(cacheNames = "zt_caches_productpreprocess")
public class ProductPreProcessServiceImp implements ProductPreProcessService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ProductPreProcessDao preprocessDao;
    @Autowired
    WorkstepDao stepsDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    ProductProcessDao processDao;

    /*
     * 分页查找
     */
    @Override
//	@Cacheable(key="'ppp_'+#queryName+'_'+#page+'_'+#size")
    public ResultPage<ProductPreProcess> findSearch(String queryName, int page, int size)
            throws BusinessRuntimeException {
        ResultPage<ProductPreProcess> ro = new ResultPage<ProductPreProcess>();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductPreProcess> pages = preprocessDao.findSearch(queryName, pageable);

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

    @Override
    public ResultObject<ProductPreProcess> findById(long id) {
        ResultObject<ProductPreProcess> ro = new ResultObject<>();
        ProductPreProcess productPreProcess = preprocessDao.findById(id);
        if (productPreProcess != null) {
            ro.setSuccess(true);
            ro.setT(productPreProcess);
        } else {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
        }
        return ro;
    }

    /**
     * 保存方法
     * stepNumber;//工序步骤编号
     * private long  step;//工序步骤
     * private String  stepType;//类
     * ProcessModel 相当于工序跟产品关联的类
     * ProgressModel 是前台展示的字段
     */
    @Override
    public ResultObject<ProductPreProcess> save(ProcessModel process) throws BusinessRuntimeException {
        //前台传的数据
        //"[{"stepNumber":1,"step":4,"stepType":"add"},{"stepNumber":2,"step":5,"stepType":"add"}]"
        ResultObject<ProductPreProcess> ro = new ResultObject<>();
        Integer code = preprocessDao.findCode(process.getCode());
        if (code == null || code == 0) {
            //下面这个是将json字符串转换成json数组(也就是没有双引号)
            //[{"stepNumber":1,"step":4,"stepType":"add"},{"stepNumber":2,"step":5,"stepType":"add"}]
            JSONArray array = JSONArray.fromObject(process.getCurrentSteps());
            List<ProgressModel> progressmodel = new ArrayList<ProgressModel>();
            if (array.size() > 0) {
                for (int i = 0; i < array.size(); i++) {
                    //把每一个对象转成json对象
                    JSONObject progressed = array.getJSONObject(i);
                    ProgressModel pro = new ProgressModel();
                    //将前台传过来的数据,进行封装.放在list集合里
                    pro.setStep(Long.parseLong(progressed.get("step").toString()));
                    pro.setStepNumber((Long.parseLong(progressed.get("stepNumber").toString())));
                    pro.setStepType((String) progressed.get("stepType"));
                    progressmodel.add(pro);
                }
            }
            //根据前台传过来的工序步骤id,去查询工步的具体数据,封装在list集合里面
            Map<String, Object> map = getSteps(progressmodel);
            List<Long> steps = new ArrayList<>();// ;
            steps.addAll((List<Long>) map.get("steps"));

            List<Workstep> steplist = stepsDao.findSteps(steps, map.get("stepsStr").toString());
            //根据ProcessModel的方法,获取该工序关联的产品
            //		Product product=productDao.findById(process.getProductId());
            //		List<Product> productlist=new ArrayList<Product>();
            //将产品封装在list集合里
            //		productlist.add(product);
            ProductPreProcess preprocess = new ProductPreProcess();
            preprocess.setEnabled(true);
            preprocess.setCreateDate(new Date());
            preprocess.setName(process.getName());
            //关联产品
            //		 preprocess.//    setProduct(productlist);
            //给工步传值,将信息封装在list集合里
            List<ProductProcess> processlist = new ArrayList<>();
            int stepSize = steplist.size();
            for (int i = 0; i < stepSize; i++) {
                //得到每一个工步的具体信息
                Workstep step = steplist.get(i);
                if (null != step) {
                    //将信息储存在工步中
                    ProductProcess processtep = new ProductProcess();
                    processtep.setName(step.getWorkstepName());
                    processtep.setEnabled(true);
                    processtep.setStepNumber(i + 1);
                    if (i == 0) {
                        processtep.setProductProcessCode(ProductProcessCode.FIRST_STEP);
                    } else if (i == stepSize - 1) {
                        processtep.setProductProcessCode(ProductProcessCode.END_STEP);
                    } else {
                        processtep.setProductProcessCode(ProductProcessCode.MIDDLE_STEP);
                    }
                    processtep.setCreateDate(new Date());
                    processtep.setWorkstep(step);
                    processtep.setPreProcess(preprocess);
                    //将工步封装成list集合
                    processlist.add(processtep);
                }
            }
            //工步关联工序
            preprocess.setProcess(processlist);
            preprocess.setCode(process.getCode());
            //将信息存储在工序中
            preprocess = preprocessDao.save(preprocess);
            //工序关联产品
            //		 product.setProsessName(process.getProcessName());
            //		 product.setPreprosess(rePreProcess);
            //将信息存储在产品中
            //		 Product  newproduct =  productDao.saveAndFlush(product);
            if (preprocess != null) {
                ro.setSuccess(true);
                ro.setMsg("操作成功");
            } else {
                ro.setSuccess(false);
                ro.setMsg("操作失败");
                throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
            }
        } else {
            ro.setSuccess(false);
            ro.setMsg("该工序已存在");
        }
        return ro;
    }

    /*
     * 更新
     */
    @Transactional //事务
    @Override
//	@CacheEvict(allEntries = true)
    public ResultObject<ProductPreProcess> update(ProcessModel process)
            throws BusinessRuntimeException {
        ResultObject<ProductPreProcess> ro = new ResultObject<>();
        Integer code = preprocessDao.findCodeId(process.getCode(), process.getPreProcessId());
        if (code != null && code > 0) {
            ro.setSuccess(false);
            ro.setMsg("该工序已存在");
        } else {
            JSONArray array = JSONArray.fromObject(process.getCurrentSteps());
            ProductPreProcess productPreProcess = preprocessDao.findById(process.getPreProcessId());
            List<ProductProcess> productProcessList = productPreProcess.getProcess();
            List<ProgressModel> progressmodel = new ArrayList<ProgressModel>();
            if (array.size() > 0) {
                for (int i = 0; i < array.size(); i++) {
                    JSONObject progressed = array.getJSONObject(i);//把每一个对象转成json对象
                    ProgressModel pro = new ProgressModel();
                    pro.setStep(Long.parseLong(progressed.get("step").toString()));
                    pro.setStepNumber((Long.parseLong(progressed.get("stepNumber").toString())));
                    pro.setStepType((String) progressed.get("stepType"));
                    progressmodel.add(pro);
                }
            }
            Iterator<ProgressModel> it = progressmodel.iterator();
            while (it.hasNext()) {
                ProgressModel progressed = it.next();
                Workstep step = stepsDao.findById(progressed.getStep());
                switch (progressed.getStepType()) {
                    case "add":
                        if (null != step) {
                            ProductProcess processtep = new ProductProcess();
                            processtep.setName(step.getWorkstepName());
                            processtep.setEnabled(true);
                            processtep.setStepNumber(progressed.getStepNumber());
                            processtep.setCreateDate(new Date());
                            processtep.setPreProcess(productPreProcess);
                            processtep.setWorkstep(step);
                            ProductProcess re = processDao.saveAndFlush(processtep);
                            productProcessList.add(re);
                        }
                        break;
                    case "update":
                        if (null != step) {
                            for (int i = productProcessList.size() - 1; i >= 0; i--) {
                                ProductProcess pro = productProcessList.get(i);
                                String prosetpNo = String.valueOf(progressed.getStepNumber());
                                String stepNumbers = String.valueOf(pro.getStepNumber());
                                if (prosetpNo.equals(stepNumbers)) {
                                    pro.setName(step.getWorkstepName());
                                    pro.setWorkstep(step);
                                    ProductProcess re = processDao.saveAndFlush(pro);
                                    productProcessList.remove(i);
                                    productProcessList.add(i, re);
                                }
                            }
                        }
                        break;
                    case "delete":
                        if (null != productProcessList) {
                            for (int i = productProcessList.size() - 1; i >= 0; i--) {
                                ProductProcess proxed = productProcessList.get(i);
                                long stepNumber = progressed.getStepNumber();
                                 long stepNumber1 = proxed.getStepNumber();
                                if (stepNumber ==stepNumber1) {
                                    processDao.delete(proxed);
                                    productProcessList.remove(i);
                                }
                            }

                        }
                        break;
                    default:
                        break;
                }
            }
            int len = productProcessList.size() - 1;
            for (int i = len; i >= 0; i--) {
                ProductProcess element = productProcessList.get(i);
                element.setStepNumber(i + 1);
                if (i == productProcessList.size() - 1) {
                    element.setProductProcessCode(ProductProcessCode.END_STEP);
                } else if (i == 0) {
                    element.setProductProcessCode(ProductProcessCode.FIRST_STEP);
                } else {
                    element.setProductProcessCode(ProductProcessCode.MIDDLE_STEP);
                }
            }
            productPreProcess.setProcess(productProcessList);
            productPreProcess.setName(process.getName());
            productPreProcess = preprocessDao.saveAndFlush(productPreProcess);
            if (productPreProcess != null) {
                ro.setSuccess(true);
                ro.setMsg("操作成功");
            } else {
                ro.setSuccess(false);
                ro.setMsg("操作失败");
                throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
            }
        }

        return ro;
    }

    private Map<String, Object> getSteps(List<ProgressModel> list) {
        Map<String, Object> map = new HashMap<>();
        List<Long> steps = new ArrayList<Long>();
        for (ProgressModel progre : list) {
            steps.add(progre.getStep());
        }
        StringBuilder sb = new StringBuilder();
        if (steps.size() > 0) {
            for (int i = 0; i < steps.size(); i++) {
                sb.append(",").append(steps.get(i).toString());
            }
            sb.append(",");
        }
        String stepsStr = sb.toString();
        map.put("steps", steps);
        map.put("stepsStr", stepsStr);

        return map;
    }

    @Override
    //@CacheEvict(allEntries = true)
    public ResultObject<ProductPreProcess> deletDel(long id) throws BusinessRuntimeException {
        ResultObject<ProductPreProcess> ro = new ResultObject<>();
        ProductPreProcess pos = preprocessDao.findById(id);
        if (pos != null) {
            pos.setEnabled(false);
            pos = preprocessDao.saveAndFlush(pos);

            ro.setSuccess(true);
            ro.setMsg("操作成功");
        } else {
            ro.setSuccess(false);
            ro.setMsg("没有该工序");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

}
