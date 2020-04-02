package com.zt.model;

import com.zt.po.BasePo;
import com.zt.po.ProductionPlanDetails;
import com.zt.po.ProductionPlanSerialNumber;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductionPlanDetailsModel extends BasePo {

    private static final long serialVersionUID = 1L;

    private long id;
    //员工Id
    private long empId;
    //实际发货数量
    private float actualQuantity;
    //    //生产计划id
//    private long productionPlanId;
    //销售计划id
    private long salesPlanId;
    //  //客户Id
    private long clientId;
    //产品Id
    private Long productId;
    //编码
    private String productionPlanSerialNumber;
    //备注
    private String note;
    //标记
    private String mark;
    //时间
    private Date createDate;
    //数据类型
    private String type;
    //状态
    private Integer status;

    /*
    添加合同的状态
    1.没有合同
    2.合同完结
    3.有部分合同
 */
    private Integer contractStatus;
    /*
    完成合同中的数量
     */
    private double contractNo;
    private Integer checkStatus;
    private Integer expectLevel;
    /**
     * 最近一次请求发货数量
     */
    private float resendNo;
    //发货请求Id
    private Long resendId;


    /**
     * 发货请求描述
     */
    private String resendStr;

    private Long colorId;
    private Long specId;
    //客户Ids 父子id
    private String pcCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(float actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public String getProductionPlanSerialNumber() {
        return productionPlanSerialNumber;
    }

    public void setProductionPlanSerialNumber(String productionPlanSerialNumber) {
        this.productionPlanSerialNumber = productionPlanSerialNumber;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public long getSalesPlanId() {
        return salesPlanId;
    }

    public void setSalesPlanId(long salesPlanId) {
        this.salesPlanId = salesPlanId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public double getContractNo() {
        return contractNo;
    }

    public void setContractNo(double contractNo) {
        this.contractNo = contractNo;
    }

    public ProductionPlanDetails v2p(ProductionPlanDetailsModel mo) {
        ProductionPlanDetails ppdetail = new ProductionPlanDetails();
        if (mo.getId() != 0) {
            ppdetail.setId(mo.getId());
        }
        if (mo.getMark() != null) {
            ppdetail.setMark(mo.getMark());
        }
        if(mo.getContractStatus()==null||mo.getContractStatus()==0){
            ppdetail.setContractStatus(1);
        }else {
            ppdetail.setContractStatus(mo.getContractStatus());
        }
        if(mo.getContractNo()==0){
            ppdetail.setContractNo(0.0);
        }else{
            ppdetail.setContractNo(mo.getContractNo());
        }
        List<ProductionPlanSerialNumber> serialNumberList = new ArrayList<>();
        JSONArray arr = JSONArray.fromObject(mo.getProductionPlanSerialNumber());
        ProductionPlanSerialNumberModel smo = new ProductionPlanSerialNumberModel();
        if (arr != null && arr.size() > 0) {
            for (int j = 0, m = arr.size(); j < m; j++) {
                JSONObject ob = arr.getJSONObject(j);
                ProductionPlanSerialNumberModel numberModel = new ProductionPlanSerialNumberModel();
                numberModel.setEndNo(String.valueOf(ob.get("endNo")));
                numberModel.setStartNo(String.valueOf(ob.get("startNo")));
                ProductionPlanSerialNumber ppsn = smo.v2p(numberModel);
                ppsn.setProdutionPlan(ppdetail);
                ppsn.setClientId(mo.getClientId());
                ppsn.setProductId(mo.getProductId());
                ppsn.setCreateDate(new Date());
                serialNumberList.add(ppsn);
            }
        }
        ppdetail.setSalesPlanId(mo.getSalesPlanId());
        if (mo.getCheckStatus() != null) {
            ppdetail.setCheckStatus(mo.getCheckStatus());
        }
        ppdetail.setEnabled(true);
        ppdetail.setUpdateDate(new Date());
        ppdetail.setSerialNumbers(serialNumberList);
        if (null != mo.getCreateDate()) {
            ppdetail.setCreateDate(mo.getCreateDate());
        } else {
            ppdetail.setCreateDate(new Date());
        }
        ppdetail.setActualQuantity(mo.getActualQuantity());
        ppdetail.setNote(mo.getNote());
        if (mo.getExpectLevel() != null) {
            ppdetail.setExpectLevel(mo.getExpectLevel());
        }

        ppdetail.setResendNo(mo.getResendNo());
        if (null != mo.getResendId()) {

            ppdetail.setResendId(mo.getResendId());
        } else {
            ppdetail.setResendId(0L);
        }
        if (null != mo.getResendStr()) {

            ppdetail.setResendStr(mo.getResendStr());
        }

        return ppdetail;
    }

    public Integer getExpectLevel() {
        return expectLevel;
    }

    public void setExpectLevel(Integer expectLevel) {
        this.expectLevel = expectLevel;
    }

    public float getResendNo() {
        return resendNo;
    }

    public void setResendNo(float resendNo) {
        this.resendNo = resendNo;
    }

    public Long getResendId() {
        return resendId;
    }

    public void setResendId(Long resendId) {
        this.resendId = resendId;
    }

    public String getResendStr() {
        return resendStr;

    }

    public void setResendStr(String resendStr) {
        this.resendStr = resendStr;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
    }

    public String getPcCode() {
        return pcCode;
    }

    public void setPcCode(String pcCode) {
        this.pcCode = pcCode;
    }
}
