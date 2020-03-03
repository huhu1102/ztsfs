/**
 * 
 */
package com.zt.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yh
 * @date 2019年5月9日
 */
//人员绩效考核表
@Entity
@Table(name="zt_achievements")
@org.hibernate.annotations.Table(appliesTo = "zt_achievements",comment="人员绩效考核表")
public class Achievements extends BasePo{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//岗位名称
	private Position positionName;
}
