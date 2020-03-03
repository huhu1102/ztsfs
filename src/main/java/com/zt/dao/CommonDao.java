package com.zt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 公共接口
 * @param <T>
 * @param <Long>
 */
@NoRepositoryBean
public interface CommonDao<T,Long> extends JpaRepository<T,Long> {

    @Query("select t from T t where t.enabled=true and t.id=?1")
    T findById(long id);
}
