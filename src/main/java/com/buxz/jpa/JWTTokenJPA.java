package com.buxz.jpa;

import com.buxz.entity.JWTTokenEntity;
import com.buxz.entity.JWTUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/*
*
* JpaRepository接口（SpringDataJPA提供的简单数据操作接口，第一个参数是实体，第二个参数是主键的类型）
* JpaSpecificationExecutor（SpringDataJPA提供的复杂查询接口）
* Serializable（序列化接口)
*
* */
public interface JWTTokenJPA extends
        JpaRepository<JWTTokenEntity, String>,
        JpaSpecificationExecutor<JWTTokenEntity>,
        Serializable {

    
}
