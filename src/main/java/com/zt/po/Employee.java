package com.zt.po;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

/**
* @author wl
* @version 2019年4月13日 
* 员工表的实体类
*/
@Entity
@Table(name="zt_employee")
@org.hibernate.annotations.Table(appliesTo = "zt_employee",comment="员工实体类")
public class Employee extends BasePo{
	private static final long serialVersionUID = 1L;
	//主键id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//员工名称
	@Column(columnDefinition ="varchar(255)  COMMENT '员工名称'" )
	private String name;		
	//员工地址
	@Column(columnDefinition ="varchar(255)  COMMENT '员工地址.'" )
	private String address;
	//员工电话
	@Column(columnDefinition ="varchar(255)  COMMENT '员工电话.'" )
	private String phone;
	//创建记录时间
	@Column(columnDefinition ="date COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	/**
	 * 用户名，权鉴用
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '用户名.'" )
	private String username;
	/**
	 * 是否可用，权鉴用
	 */
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	 private boolean enabled;
	/**
	 * 关联部门ids
	 * @return
	 */
	@Column(columnDefinition ="varchar(100)  COMMENT '关联部门.'" )
	private String departmentIds;


	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="zt_emp_dept",joinColumns={@JoinColumn(name="e_id")}
			,inverseJoinColumns={@JoinColumn(name="dept_id")})
	private List<Department> dept;
	/**
	 * 关联部门名称
	 *
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '关联部门名称.'" )
	 private String departmentName;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="zt_emp_pos",joinColumns={@JoinColumn(name="e_id")}
			,inverseJoinColumns={@JoinColumn(name="pos_id")})
	private List<Position> post;
	/**
	 * 关联职位
	 *
	 */
	@Column(columnDefinition ="varchar(200)  COMMENT '关联岗位.'" )
	private String positionIds;
	/**
	 * 职位名称
	 *
	 */
	@Column(columnDefinition ="varchar(50)  COMMENT '关联岗位.'" )
	private String positionName;
	/**
	 * 性别
	 */
	@Column(columnDefinition ="INT(2)  COMMENT '性别'" )
	private int  gender;
	/**
	 * 入职时间
	 */
	@Column(columnDefinition ="datetime  COMMENT '入职时间.'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;
	/**
	 * 年龄
	 */
	@Column(columnDefinition ="datetime  COMMENT '生日.'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthday;
	/**
	 * 邮箱
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '邮箱.'" )
     private String email;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @return the gender
	 */
	public int getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}
	/**
	 * @return the entryDate
	 */
	public Date getEntryDate() {
		return entryDate;
	}
	/**
	 * @param entryDate the entryDate to set
	 */
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	
	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}
	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public List<Department> getDept() {
		return dept;
	}

	public void setDept(List<Department> dept) {
		this.dept = dept;
	}

	public List<Position> getPost() {
		return post;
	}

	public void setPost(List<Position> post) {
		this.post = post;
	}

    public String getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(String departmentIds) {
        this.departmentIds = departmentIds;
    }

    public String getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(String positionIds) {
        this.positionIds = positionIds;
    }
}
