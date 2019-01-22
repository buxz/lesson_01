package com.buxz.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by SQ_BXZ on 2019-01-22.
 * mapstruct 商品类型实体
 */
@Entity
@Table(name = "good_types")
@Data
public class GoodTypeBean {
    @Id
    @Column(name = "tgt_id")
    private long id;
    @Column(name = "tgt_name")
    private String name;
    @Column(name = "tgt_is_show")
    private int show;
    @Column(name = "tgt_order")
    private int order;

}
