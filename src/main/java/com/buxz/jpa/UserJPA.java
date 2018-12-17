package com.buxz.jpa;

import com.buxz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/*
*
* JpaRepository接口（SpringDataJPA提供的简单数据操作接口，第一个参数是实体，第二个参数是主键的类型）
* JpaSpecificationExecutor（SpringDataJPA提供的复杂查询接口）
* Serializable（序列化接口)
*
* */
public interface UserJPA extends
        JpaRepository<UserEntity, Long>,
        JpaSpecificationExecutor<UserEntity>,
        Serializable {

    /**
     * nativeQuery = true，表明使用原生的SQL
     * @param age
     * @return
     */
    @Query(value = "select * from t_user where t_age > ?1",nativeQuery = true)
    List<UserEntity> nativeQuery(int age);

    /**
     * @Query配合@Modifying 可以实现数据的删除、添加、更新操作
     *
     * SpringDataJPA自定义SQL时需要在对应的接口或者调用接口的地方添加事务注解 @Transactional，来开启事务自动化管理
     * @param name
     * @param pwd
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE  from t_user where t_name = ?1 AND t_pwd = ?2",nativeQuery = true)
    void deleteQuery(String name, String pwd);

    /**
     * 使用springDataJpa方法定义查询
     * @param username
     * @return
     */
    UserEntity findByUsername(String username);


}
