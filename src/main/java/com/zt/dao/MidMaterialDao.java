package com.zt.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zt.po.MidMaterial;

/**
 * @author wl
 * @date 2019年6月24日 
 */
public interface MidMaterialDao extends JpaRepository<MidMaterial,Long>{
	
	MidMaterial findById(long id);
}
