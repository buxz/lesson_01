package com.buxz.jpa;

import com.buxz.entity.GoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by SQ_BXZ on 2018-12-14.
 * QueryDslPredicateExecutor , SpringDataJPA提供的querydsl查询接口
 * GoodJPA就拥有了SpringDataJPA整合QueryDSL的方法实现
 */
public interface GoodJPA extends JpaRepository<GoodEntity,Long>,QueryDslPredicateExecutor<GoodEntity> {

}
