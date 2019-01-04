package com.buxz.jpa;

import com.buxz.entity.OAuth2Authority;
import com.buxz.entity.OAuth2User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

/*
*
* JpaRepository接口（SpringDataJPA提供的简单数据操作接口，第一个参数是实体，第二个参数是主键的类型）
* JpaSpecificationExecutor（SpringDataJPA提供的复杂查询接口）
* Serializable（序列化接口)
*
* */
public interface OAuth2AuthorityJPA extends
        JpaRepository<OAuth2Authority, String>,
        JpaSpecificationExecutor<OAuth2Authority>,
        Serializable {

}
