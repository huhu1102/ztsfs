/**
 * 
 */
package com.zt.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.zt.po.RowMaterial;



/**
 * @author yh
 * @date 2019年4月28日
 */
@RepositoryRestResource(collectionResourceRel = "rowmaterial", path="rowmaterial")
public interface RowMaterialDao extends JpaRepository<RowMaterial,Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
  //新自定义模糊查询
    
    @Query("select a  from RowMaterial a where a.enabled=true and a.materialName like %?1% order by a.buyStatus desc") 
    Page<RowMaterial> findbypage(String query, Pageable pageable);
  //
    
    @Query(value="select * from zt_rowmaterial as mtl where mtl.enabled=true and mtl.materialName like %?1% order by mtl.createDate desc limit ?2,?3",nativeQuery =true) //..
    List<RowMaterial> findAllByPage(String materialName,Integer pageNumber,Integer pageSize);
    
    @Query(value="select count(*) from zt_rowmaterial as mtl where mtl.enabled=true and mtl.materialName like %?1%",nativeQuery =true) //.
    Integer countAllData(String materialName);
    
    RowMaterial findById(long id);

	/**
	 * @return
	 */
    @Query("select r.materialName,r.unitId,r.id,r.unit.name,r.specs  from RowMaterial r where r.enabled=true ") 
	List<Object[]> findAllMaterail();
	
	/*
	 * 修改有采购计划的状态
	 */
	@Transactional
	@Modifying
	@Query("update  RowMaterial  m set m.buyStatus=true where m.id=?1")
	public Integer updateByrepairman(Long id);
	
    /*
     * 查询名称+规格
     */
    @Query("select r from RowMaterial r where CONCAT(r.materialName,r.specs)  = ?1 AND r.enabled=1")
    public List<RowMaterial> findRowMaterial(String nameAndSpecs);
    
    /*
     * 根据名字查询规格
     */
    @Query(value="select materialName,GROUP_CONCAT(specs) from zt_rowmaterial  GROUP BY materialName",nativeQuery = true)
    public List<Object[]> findSpecs();

    /*
    修改数量
     */
    @Modifying
    @Query("update RowMaterial  r set r.quantity=?1 where r.id=?2")
    int updateQuan(float quantity,long id);
	
}

