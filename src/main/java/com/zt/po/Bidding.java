package com.zt.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yh
 * @date 2019年4月22日
 * 标书
 */
@Entity
@Table(name = "zt_bidding")//
@org.hibernate.annotations.Table(appliesTo = "zt_bidding", comment = "标书制作")
public class Bidding extends BasePo {
    private static final long serialVersionUID = 1L;
    //标书id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //编号
    @Column(columnDefinition = "varchar(255)  COMMENT '编号.'")
    private String numbers;

    //客户Id
    @Column(columnDefinition = "bigint(20)  COMMENT '客户Id.'")
    private Long clientId;

    //关联客户
    @ManyToOne
    private Client client;

    //是否可用
    @Column(columnDefinition = "TINYINT(1)  COMMENT '是否可见'")
    private boolean enabled;
    //创建时间
    @Column(columnDefinition = "datetime  COMMENT '创建时间'")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    //标书状态
    @Column(columnDefinition = "varchar(255)  COMMENT '标书状态.'")
    private String status;
    //制作人Id
    @Column(columnDefinition = "bigint(20)  COMMENT '制作人Id.'")
    private Long producerId;

    //关联员工表(制作人)
    @ManyToOne
    private Employee producer;
    //完成人
    @Column(columnDefinition = "bigint(20)  COMMENT '完成人Id.'")
    private Long finisherId;

    //关联员工表(完成人)
    @ManyToOne
    private Employee finisher;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return numbers;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.numbers = number;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getProducerId() {
        return producerId;
    }

    public void setProducerId(Long producerId) {
        this.producerId = producerId;
    }

    public Employee getProducer() {
        return producer;
    }

    public void setProducer(Employee producer) {
        this.producer = producer;
    }

    public Long getFinisherId() {
        return finisherId;
    }

    public void setFinisherId(Long finisherId) {
        this.finisherId = finisherId;
    }

    public Employee getFinisher() {
        return finisher;
    }

    public void setFinisher(Employee finisher) {
        this.finisher = finisher;
    }


}
