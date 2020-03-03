package com.zt.serviceImp;

import com.zt.common.Utils;
import com.zt.dao.*;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.*;
import com.zt.service.ProductService;
import com.zt.vo.MidMaterialModel;
import com.zt.vo.ProductModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author wl
 * @date 2019年4月18日
 * 产品业务层实现类
 */
@Service("produteService")
@CacheConfig(cacheNames = "zt_caches_produte")
public class ProductServiceImp implements ProductService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ProductDao productDao;
    @Autowired
    WorkstepDao stepsDao;
    @Autowired
    MiddleProductDao midProductDao;
    @Autowired
    ClientDao clientDao;
    @Autowired
    MidMaterialDao midMaterialDao;
    @Autowired
    SpecificationDao specDao;
    @Autowired
    SysUnitDao  unitDao;
    @Autowired
    ColorDao   colorDao;
    @Autowired
    ProductPreProcessDao productPreProcessDao;
    @Autowired
    ProductedUseDao productedUseDao;
    @Autowired
    UploadFileDao uploadFileDao;

    /**
     *  多条件分页模糊查询
     * @param queryName  产品名
     * @param keyColorName  颜色名
     * @param keyspeName 型号名
     * @param keyUse 用途名
     * @param page 第几页
     * @param size 每页行数
     * @return
     * @throws BusinessRuntimeException
     */
    @Override
//	@Cacheable(key="'prod_'+#queryName+'_'+#page+'_'+#size")
    public ResultPage<Product> findSearch(String queryName,String keyColorName,String keyUse, String keyspeName,int page, int size)
            throws BusinessRuntimeException {
        ResultPage<Product> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> pages = productDao.findSearch(queryName,keyColorName,keyUse, keyspeName,pageable);
        if (pages.getContent()!= null) {
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
    查询所有
     */
    @Override
    public ResultPage<Object> findAll(int page,int size) throws BusinessRuntimeException {
        ResultPage<Object> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Object> pages = productDao.selectAll(pageable);
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

    /**
     * 产品的新建
     */
    @Override
    public ResultObject<Product> addProduct(ProductModel productModel) throws Exception {
        ResultObject<Product> ro = new ResultObject<>();
        ProductModel mo = new ProductModel();
        Product product = mo.v2p(productModel);
        //规格 保存；
        String  specStr=product.getSpecification();
        String  colorStr=product.getColor();
        StringBuilder sb=new StringBuilder();
        StringBuilder colorsb=new StringBuilder();
        List<Specification> specsList=getSpecString(specStr);
        List<Color> colorList=getColors(colorStr);
        for (Specification specification : specsList) {
            sb.append("/")
              .append(specification.getName());
        }
        for (Color color:colorList) {
            colorsb.append(",")
                    .append(color.getName());
        }
        List<ProductUse> uselist=getUses(productModel.getProType());
        specStr= sb.toString();
        specStr=  specStr.replaceFirst("/","");
        colorStr=colorsb.toString();
        colorStr= colorStr.replaceFirst(",","");
        product.setColorName(colorStr);
        product.setSpecName(specStr);
        product.setColors(colorList);
        product.setSpecs(specsList);
        SysUnit unit = unitDao.findById(product.getSysUnitId());
        product.setSysUnit(unit);
        product.setUses(uselist);

        // 产品所需要的半成品
        List<MidMaterial> midMaterialList = new ArrayList<>();
        String jsStr=productModel.getMidMaterials();
         logger.info(jsStr);
         if(!jsStr.equals("")){
        JSONArray array = JSONArray.fromObject(jsStr);
        MidMaterialModel midModel = new MidMaterialModel();
        if (array.size() > 0) {
            for (int i = 0, n = array.size(); i < n; i++) {
                JSONObject object = array.getJSONObject(i);
                MidMaterialModel midMatModel = new MidMaterialModel();
                Long mid = Long.parseLong(String.valueOf(object.get("midProductId")));
                Integer quantity = Integer.parseInt(String.valueOf(object.get("quantity")));
                midMatModel.setMidProductId(mid);
                midMatModel.setQuantity(quantity);
                midMatModel.setName(String.valueOf(object.get("name")));
                MidMaterial mm = midModel.v2p(midMatModel);
                midMaterialList.add(mm);
            }
         }
         }
        product.setMidMaterials(midMaterialList);
        String maxNumber = productDao.getMaxNumber();
        if(maxNumber!=null){
            Long no = Long.valueOf(maxNumber);
            no+=1;
            StringBuilder ssss =  new StringBuilder();
            ssss.append("%0").append(5).append("d");
            String string = ssss.toString();
            String s = String.format(string,no);
            product.setSerialNumber(s);
        }else{
            product.setSerialNumber("00001");
        }
        product = productDao.saveAndFlush(product);
        if (product != null) {
            ro.setSuccess(true);
            ro.setMsg("保存成功!");
        } else {
            ro.setSuccess(false);
            ro.setMsg("操作失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }
    private List<Color> getColors(String colorStr) {
        List<Long> ids= getIdsByArr(colorStr);
        List<Color> re=colorDao.findColors(ids);
        return re;
    }

    private List<Specification> getSpecString(String specStr) {
        List<Long> ids= getIdsByArr(specStr);
        List<Specification> re=specDao.findSpecs(ids);
        return re;
    }
    private List<ProductPreProcess> getPreProcess(String stepIds){
        List<ProductPreProcess> re = new ArrayList<>();
        if (!stepIds.equals("")) {
        List<Long> ids= getIdsByArr(stepIds);
            re =productPreProcessDao.findSpecs(ids);
        }
        return re;
    }
    private List<Long> getIdsByArr(String specStr) {
        String[] specArr=specStr.split(",");
        List<Long>  specs=new ArrayList<>();
        if(!specStr.equals("")){
            int n=specArr.length;
            for (int i = 0; i <n; i++) {
                specs.add(Long.parseLong(specArr[i]));
            }
        }
        return specs;
    }
    /**
     * 产品的修改
     */
    @Override
//    @CacheEvict(allEntries = true)
    public ResultObject<Product> saveProduct(ProductModel productModel) {
        ResultObject<Product> ro = new ResultObject<>();
        ProductModel mo = new ProductModel();
        MidMaterialModel midModel = new MidMaterialModel();
        if (productModel.getId() > 0) {
            Product pro = productDao.findById(productModel.getId());
            List<MidMaterial> midMaterialList = pro.getMidMaterials();
            pro = mo.v2p(productModel);
            List<Specification> specList=getSpecString(productModel.getSpecification());
            List<Color> colorList=getColors(productModel.getColor());
            List<ProductUse> uselist=getUses(productModel.getProType());
            SysUnit unit = unitDao.findById(pro.getSysUnitId());
            pro.setSysUnit(unit);
            StringBuilder specsb=new StringBuilder();
            StringBuilder colorsb=new StringBuilder();
            for (Specification s:specList ) {
                if (s.getName()!= null) {
                specsb.append("/").append(s.getName());
                }
            }
            for (Color c:colorList ) {
                if (c.getName() != null) {
                    colorsb.append(",").append(c.getName());
                }
            }
             String specStr=  specsb.toString().replaceFirst("/","");
            pro.setSpecName(specStr);
            pro.setSpecs(specList);
            String colorStr=colorsb.toString().replaceFirst(",","");
            pro.setColorName(colorStr);
            pro.setColors(colorList);
            pro.setUses(uselist);
            String jsStr=productModel.getMidMaterials();
            if(!jsStr.equals("")){
            JSONArray array = JSONArray.fromObject(jsStr);
            if (array.size() > 0) {
                for (int i = 0, n = array.size(); i < n; i++) {
                    JSONObject object = array.getJSONObject(i);
                    MidMaterialModel midMatModel = new MidMaterialModel();
                    Long midProductId = Long.parseLong(String.valueOf(object.get("midProductId")));
                    Integer qu = Integer.parseInt(String.valueOf(object.get("quantity")));
                    String mtype = String.valueOf(object.get("mtype"));
                    String midProductName = String.valueOf(object.get("name"));
                    MiddleProduct middleProduct = null;
                    if (midProductId > 0) {
                        Optional<MiddleProduct> sa = midProductDao.findById(midProductId);
                        if (sa.isPresent()) {
                            middleProduct = sa.get();
                        }
                    }
                    midMatModel.setId(Long.parseLong(String.valueOf(object.get("id"))));
                    midMatModel.setName(midProductName);
                    midMatModel.setMtype(mtype);
                    midMatModel.setQuantity(qu);
                    if (midProductId > 0) {
                        midMatModel.setMidProductId(midProductId);
                    }
                    MidMaterial midMaterial = midModel.v2p(midMatModel);
                    switch (mtype) {
                        /**
                         * delete
                         * update
                         * new
                         */
                        case "new":
                            midMaterial = midMaterialDao.save(midMaterial);
                            midMaterialList.add(midMaterial);
                            break;
                        case "update":

                            midMaterial = midMaterialDao.saveAndFlush(midMaterial);
                            for (int j = 0, c = midMaterialList.size(); j < c; j++) {
                                MidMaterial mm = midMaterialList.get(j);
                                if (mm.getId() == midMaterial.getId()) {
                                    midMaterialList.remove(j);
                                    midMaterialList.add(j,midMaterial);
                                }
                            }
                            break;
                        case "delete":
                            for (int j = 0, c = midMaterialList.size(); j < c; j++) {
                                MidMaterial mm = midMaterialList.get(j);
                                if (mm.getId() == midMaterial.getId()) {
                                    midMaterialDao.deleteById(midMatModel.getId());
                                    midMaterialList.remove(j);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            }
            pro.setMidMaterials(midMaterialList);
            pro = productDao.saveAndFlush(pro);
            if(pro!=null) {
            	ro.setSuccess(true);
            	ro.setMsg("修改成功！");
            }else {
            	ro.setSuccess(false);
            	ro.setMsg("操作失败,请重试！");
            	throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
            }
        }

        return ro;
    }

    private List<ProductUse> getUses(String proType) {
        List<ProductUse> uses=new ArrayList<>();
        String[] list=  proType.split(",");
        List<String> resultList = new ArrayList<>(list.length);
        Collections.addAll(resultList,list);
        logger.info(resultList.toString());
        List<ProductUse> uselist= productedUseDao.findByUseName(resultList);
        return uselist;
    }

    /*
     * 删除
     */
    @Override
    @CacheEvict(allEntries = true)
    public ResultObject<Product> deletPro(long id) {
        ResultObject<Product> ro = new ResultObject<>();
        Product pro = productDao.findById(id);
        if (pro != null) {
            pro.setEnabled(false);
            productDao.saveAndFlush(pro);
            ro.setSuccess(true);
            ro.setMsg("更新成功");
        } else {
            ro.setSuccess(false);
            ro.setMsg("操作失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

    @Override
    public ResultObject<Object> getBaseData() throws BusinessRuntimeException {
        ResultObject<Object> ro = new ResultObject<>();
        Map<String, Object> map = new HashMap<String, Object>();
        Set<Object[]> midList = midProductDao.findAllPro();
        Set<Client> clients = clientDao.findAllByTypes("down");
        Set<Specification> specs = specDao.findAllspec();
        List<Workstep> list = stepsDao.findAllsteps();
        Set<SysUnit>  unitList=unitDao.findAllUnits();
        List<Color> colors=colorDao.findAll();
        List<ProductUse> uses=productedUseDao.findAll();
        Set<Object>  productType=productedUseDao.findallprodutType();
        if (list == null) {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
        } else {
            map.put("clients", clients);
            map.put("specs", specs);
            map.put("steps", list);
            map.put("units",unitList);
            map.put("colors",colors);
            map.put("midProducts", midList);
            map.put("uses", uses);
            map.put("productType", productType);
            ro.setRoot(map);
            ro.setSuccess(true);
        }

        return ro;

    }
    @Override
    public ResultObject<ProductPreProcess> deletePre(long id) throws BusinessRuntimeException {
        ResultObject<ProductPreProcess> ro = new ResultObject<>();
        Product pro = productDao.findById(id);

        if (pro != null) {
            pro.setPreprosess(null);
            pro.setEnabled(false);
            productDao.saveAndFlush(pro);
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
    添加工序的方法
     */
    @Override
    @Transactional
    public ResultObject<ProductPreProcess> addProcess(long productId, String stepIds) throws BusinessRuntimeException {
        ResultObject<ProductPreProcess> ro =  new ResultObject<>();
        List<ProductPreProcess> productPreProcessList = getPreProcess(stepIds);
        Product product = productDao.findById(productId);
        List<ProductPreProcess> processList = product.getPreprosess();
        if(processList.size()>0){
            processList.removeAll(processList);
            product = productDao.saveAndFlush(product);
        }
           if (productPreProcessList.size()>0){
            product.setPreprosess(productPreProcessList);
           }
            product = productDao.saveAndFlush(product);
            if(product!=null){
                ro.setSuccess(true);
                ro.setMsg("添加成功");
            }else{
                ro.setSuccess(false);
                ro.setMsg("添加失败");
                throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
            }

        return ro;
    }


}
