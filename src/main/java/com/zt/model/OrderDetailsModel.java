package com.zt.model;

import com.zt.po.SalesOrderDetails;
import net.sf.json.JSONObject;
/*
最新订单详情model
 */
public class OrderDetailsModel {

    //订单详情ID
    long id;
    //生产计划详情id
    long productDetailsId;

    //数量
    double productNo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getProductNo() {
        return productNo;
    }

    public void setProductNo(double productNo) {
        this.productNo = productNo;
    }

    public long getProductDetailsId() {
        return productDetailsId;
    }

    public void setProductDetailsId(long productDetailsId) {
        this.productDetailsId = productDetailsId;
    }

    public OrderDetailsModel j2m(JSONObject object){
        OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
        String idStr = String.valueOf(object.get("id"));
        String productNo;
        if(null!=object.get("productNo")){
            productNo="";
        }else {
        productNo= String.valueOf(object.get("productNo"));
        }
        if(!idStr.equals("null")&&!idStr.equals("0")){
            orderDetailsModel.setId(Long.parseLong(idStr));
        }
        if(productNo!=null&&productNo.equals("null")){
            orderDetailsModel.setProductNo(0);
        }else{
        orderDetailsModel.setProductNo(Double.parseDouble(productNo));
        }
        if(object.get("productDetailsId")!=null){
         String  productId = String.valueOf(object.get("productDetailsId")) ;
            orderDetailsModel.setProductDetailsId(Long.parseLong(productId));
         }
        return orderDetailsModel;
    }

    public SalesOrderDetails v2p(OrderDetailsModel mo){
        SalesOrderDetails salesOrderDetails = new SalesOrderDetails();
        if(mo.getId()!=0){
            salesOrderDetails.setId(mo.getId());
        }
        if(mo.getProductDetailsId()!=0){
            salesOrderDetails.setProductDetailsId(mo.getProductDetailsId());
        }
        salesOrderDetails.setProductNo(mo.getProductNo());
        return salesOrderDetails;
    }

}
