package com.zt.dao;


import com.zt.po.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;



@RepositoryRestResource(collectionResourceRel = "contract", path="contract")
public interface ContractDao extends JpaRepository<Contract,Long> {

    /*
     * 自定义分页模糊查询
     */
    @Query(value = "SELECT\n" +
            "\tcon.* \n" +
            "FROM\n" +
            "\tzt_contract con\n" +
            "\tLEFT JOIN zt_employee e ON e.id = con.empId\n" +
            "\tLEFT JOIN zt_client c ON c.id = con.cliId\n" +
            "WHERE\n" +
            "\tcon.enabled = 'true'\n" +
            "\tand if(:contractName != '',con.contractName LIKE %:contractName%,1=1)\n" +
            "\tand if(:empName != '', e.name LIKE %:empName%,1=1)\n" +
            "\tand if(:clientName != '', c.name LIKE %:clientName%, 1 = 1)\n" +
            "\tand if(:createDateStart != '', DATE_FORMAT( o.createDate, '%Y-%m-%d %k:%i:%s' ) >=:createDateStart, 1 = 1)\n" +
            "  and if(:createDateEnd != '', DATE_FORMAT( o.createDate, '%Y-%m-%d %k:%i:%s' ) <=:createDateEnd, 1 = 1)\n" +
            "\tand if(:startDateStart != '',DATE_FORMAT( o.startDate, '%Y-%m-%d %k:%i:%s' ) >=:startDateStart, 1 = 1)\n" +
            "\tand if(:startDateEnd!= '', DATE_FORMAT( o.startDate, '%Y-%m-%d %k:%i:%s' ) <=:startDateEnd, 1 = 1)\n" +
            "\tand if(:endDateStart != '',DATE_FORMAT( o.endDate, '%Y-%m-%d %k:%i:%s' ) >=:endDateStart, 1 = 1)\n" +
            "\tand if(:endDateEnd != '', DATE_FORMAT( o.endDate, '%Y-%m-%d %k:%i:%s' ) <=:endDateEnd, 1 = 1)\n" +
            "\tand if(:endDateStart != '',DATE_FORMAT( o.signContractDate, '%Y-%m-%d %k:%i:%s' ) >=:signDateStart, 1 = 1)\n" +
            "\tand if(:endDateEnd != '', DATE_FORMAT( o.signContractDate, '%Y-%m-%d %k:%i:%s' ) <=:signDateEnd, 1 = 1)\n" +
            "\tand if(:contractStatus !='',con.contractStatus =:contractStatus ,1=1)",nativeQuery = true)
    Page<Contract> findSearch(@Param("contractName") String contractName,
                              @Param("clientName") String clientName,
                              @Param("empName")String empName,
                              @Param("createDateStart")String createDateStart,
                              @Param("createDateEnd")String createDateEnd,
                              @Param("startDateStart")String startDateStart,
                              @Param("startDateEnd")String startDateEnd,
                              @Param("endDateStart")String endDateStart,
                              @Param("endDateEnd")String endDateEnd,
                              @Param("signDateStart")String signDateStart,
                              @Param("signDateEnd")String signDateEnd,
                              @Param("contractStatus")Integer status,
                              Pageable pageable);

    @Query("select c from Contract c where c.enabled=true and c.id=?1")
    Contract findById(long id);

    //查找自定义最大的编号

}