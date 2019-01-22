package com.buxz.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by SQ_BXZ on 2019-01-22.
 * mapstruct 商品基本信息
 */
@Entity
@Table(name = "good_infos")
@Data
public class GoodInfoBean {
    @Id
    @Column(name = "tg_id")
    private long id;
    @Column(name = "tg_title")
    private String title;
    @Column(name = "tg_price")
    private double price;
    @Column(name = "tg_order")
    private int order;
    @Column(name = "tg_type_id")
    private Long typeId;

}
