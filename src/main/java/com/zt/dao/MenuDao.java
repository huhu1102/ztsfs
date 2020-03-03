/**
 *
 */

package com.zt.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.Meeting;
import com.zt.po.Menu;
import com.zt.vo.MenuModel;
import com.zt.vo.MenuViewModel;

/**
 * @author whl
 * @date 2019年4月16日
 */
@RepositoryRestResource(collectionResourceRel = "menu", path = "menu")
public interface MenuDao extends JpaRepository<Menu, Long> {

    /**
     * @param
     * @return
     */
    @Query(value = "SELECT DISTINCT m.* from zt_menu m  LEFT JOIN  menu_sysrole  m_r  on m_r.menuId=m.id LEFT JOIN  zt_sysrole  ro ON ro.id=m_r.sysroleId  LEFT JOIN  zt_users_roles  u_r on ro.id=u_r.roles_id LEFT JOIN  zt_users  u  on u.id= u_r.users_id  WHERE u.id=:userId", nativeQuery = true)
    Set<Menu> findAllmenu(@Param("userId") Long userId);

    @Query("select m  from Menu m join  m.sysRole sy  where sy.id in(select r.id from SysRole  r join r.users u where u.id=:userId)")
    Set<Menu> findAllobyr(@Param("userId") Long userId);

    @Query(value = "SELECT m1.id as id,m2.path as path2,m2.component as component2,m2.iconCls as iconCls2,m2.`name` as name2,m2.requireAuth as requireAuth2,m2.keepAlive as keepAlive2 FROM zt_menu m1, zt_menu m2 WHERE m1.`id` = m2.`parentId` AND m1.id != 1 AND m2.id IN (SELECT mr.menuId FROM zt_users_roles u_r,menu_sysrole mr WHERE u_r.roles_id = mr.sysroleId AND u_r.Users_id =:userId)group by m2.id ORDER BY m1.id,m2.id", nativeQuery = true)
    Set<Object> findAllMenuChild(@Param("userId") Long userId);

    /**
     * @return
     */
    @Query(value = "SELECT m.*, m.id,m.component,m.iconCls,m.keepAlive,m.`name`,m.path,m.requireAuth,m.url,m.parentId,  r.`id` AS rid,r.`name` AS roleName FROM zt_menu m LEFT JOIN menu_sysrole mr ON m.`id` = mr.menuId LEFT JOIN zt_sysrole r ON mr.sysroleId = r.`id` WHERE m.enabled=true ORDER BY m.`id` DESC", nativeQuery = true)
    List<Menu> findAuthors();

    Menu findById(long id);


    @Query(value = "SELECT * from zt_menu  m WHERE  m.enabled=TRUE  and m.id=1", nativeQuery = true)
    Set<Menu> findAllobyrole();

    @Query(value = "SELECT e.menuId from menu_sysrole  e where  e.sysroleId=?1 ", nativeQuery = true)
    Set<Long> findAllmenus(long roleId);
    @Modifying
    @Query(value = "DELETE  FROM menu_sysrole  WHERE sysroleId=?1", nativeQuery = true)
    void deleteMids(  long  id);

    @Query(value = "SELECT * from zt_menu  m WHERE  m.enabled=TRUE and m.id in(?1) ", nativeQuery = true)
    List<Menu> findAllByMiId(List<Long> arrayList);

//	@Query("select m from Menu m where m.id in(select me.id form SysRoles sy join Menu me on sy.id=me.id   where sy.id in(select s.id from SysRoles s join Users u   where s.id=u.id and u.id=:userId))")
//	Set<Menu> findAllmenus(@Param("userId") Long userId);

}
