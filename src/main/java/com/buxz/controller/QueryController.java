package com.buxz.controller;

import com.buxz.entity.GoodEntity;
import com.buxz.entity.QGoodEntity;
import com.buxz.jpa.GoodJPA;
import com.buxz.natives.Inquirer;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by SQ_BXZ on 2018-12-14.
 */
@RestController
@RequestMapping(value = "/goods")
public class QueryController {

    // 注入 entityManager
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    GoodJPA goodJPA;

    @RequestMapping(value = "/query")
    public List<GoodEntity> query(){
        // qsl查询实体
        QGoodEntity qGoodEntity = QGoodEntity.goodEntity;
        // 构建jpa查询对象
        JPAQuery<QGoodEntity> jpaQuery = new JPAQuery<>(entityManager);
        // 返回查询接口
        return jpaQuery
                // 查询字段
                .select(qGoodEntity)
                // 查询表
                .from(qGoodEntity)
                // 查询条件
                .where(qGoodEntity.type.id.eq(Long.valueOf("1")))
                // 返回结果
                .fetch();
    }

    /**
     * spring data jpa 整合 querydsl完成查询
     *
     * @return
     */
    @RequestMapping(value = "/join")
    public List<GoodEntity> join(){
        // qsl查询实体
        QGoodEntity qGoodEntity = QGoodEntity.goodEntity;
        // 查询条件
        BooleanExpression expression = qGoodEntity.type.id.eq(Long.valueOf("1"));
        // 执行查询
        Iterator<GoodEntity> iterator =goodJPA.findAll(expression).iterator();
        List<GoodEntity> goods = new ArrayList<>();
        // 转换成list
        while (iterator.hasNext()){
            goods.add(iterator.next());
        }
        return goods;
    }

    /**
     * 使用Inquirer 封装查询
     *
     * @return
     */
    @RequestMapping(value = "/join_2")
    public List<GoodEntity> join_2(){
        // qsl查询实体
        QGoodEntity qGoodEntity = QGoodEntity.goodEntity;
        // 自定义查询对象
        Inquirer inquirer= new Inquirer();
        // 添加查询条件
        inquirer.putExpression(qGoodEntity.type.id.eq(Long.valueOf("1")));
        // 返回查询结果
        return inquirer.iteratorToList(goodJPA.findAll(inquirer.buidleQuery()));
    }

}
