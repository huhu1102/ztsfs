package com.zt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.QualityDeposit;

/**
 * @author wl
 * @date 2019年5月6日 
 */
@RepositoryRestResource(collectionResourceRel = "qualitydeposit", path="qualitydeposit")
public interface QualityDepositDao extends JpaRepository<QualityDeposit, Long>{
    
    QualityDeposit findById(long id);
}
