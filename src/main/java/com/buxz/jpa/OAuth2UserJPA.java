package com.buxz.jpa;

import com.buxz.entity.OAuth2User;
import com.buxz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
public interface OAuth2UserJPA extends
        JpaRepository<OAuth2User, String>,
        JpaSpecificationExecutor<OAuth2User>,
        Serializable {

    @Query("SELECT u FROM OAuth2User u WHERE LOWER(u.username) = LOWER(:username)")
    OAuth2User findByUsernameCaseInsensitive(@Param("username") String username);

}
