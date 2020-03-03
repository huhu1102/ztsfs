package com.zt.dao;

import com.zt.po.SalesOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;


/**
 * @author wl
 * @date 2019年4月18日
 */
@RepositoryRestResource(collectionResourceRel = "salesorder", path = "salesorder")
public interface SalesOrderDao extends JpaRepository<SalesOrder, Long> {
    /*
     * 自定义分页模糊查询
     */
    @Query(value = "SELECT o.* FROM zt_salesorder o LEFT JOIN zt_employee e ON e.id = o.employeeId" +
            " LEFT JOIN zt_client c ON c.id = o.clienteleid WHERE o.enabled = TRUE " +
            "and if(:clientName != '', c.name LIKE %:clientName%, 1 = 1)\n" +
            "and if(:name != '', e.NAME LIKE %:name%, 1 = 1)\n" +
            "and if(:start != '', DATE_FORMAT( o.createDate, '%Y-%m-%d %k:%i:%s' ) >=:start, 1 = 1)\n" +
            "and if(:end != '', DATE_FORMAT( o.createDate, '%Y-%m-%d %k:%i:%s' ) <=:end, 1 = 1)" +
            "and if(:status !='',o.orderStatus =:status ,1=1)",
            countQuery = "SELECT count(*) FROM zt_salesorder o LEFT JOIN zt_employee e ON e.id = o.employeeId" +
                    " LEFT JOIN zt_client c ON c.id = o.clienteleid WHERE o.enabled = TRUE " +
                    "and if(:clientName != '', c.name LIKE %:clientName%, 1 = 1)\n" +
                    "and if(:name != '', e.NAME LIKE %:name%, 1 = 1)\n" +
                    "and if(:start != '', DATE_FORMAT( o.createDate, '%Y-%m-%d %k:%i:%s' ) >=:start, 1 = 1)\n" +
                    "and if(:end != '', DATE_FORMAT( o.createDate, '%Y-%m-%d %k:%i:%s' ) <=:end, 1 = 1)" +
                    "and if(:status !='',o.orderStatus =:status ,1=1)",
            nativeQuery = true)
    Page<SalesOrder> findSearch(@Param("clientName") String clientName,

                                @Param("name") String empName,
                                @Param("start") String start,
                                @Param("end") String end,
                                @Param("status") Integer status,
                                Pageable pageable);

    @Query("select s from SalesOrder s where s.enabled=true and s.id=?1")
    SalesOrder findById(long id);

    /*
     * 查找最大的订单编号
     */
    @Query("select max(p.orderNo) from SalesOrder p where p.enabled=true")
    String findMaxPlanNo();

    @Modifying
    @Query("update SalesOrder s set s.orderStatus=?1 where s.id=?2")
    int updateStatus(Integer status, long id);

    @Query("select count(s.orderStatus) from SalesOrder s where s.enabled=true group by s.orderStatus")
    Set<SalesOrder> findStatus();


}
