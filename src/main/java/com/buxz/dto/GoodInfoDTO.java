package com.buxz.dto;

import lombok.Data;

/**
 * 该类集成了商品信息、商品类型两张表内的数据，对应查询出信息后，我们需要使用MapStruct自动映射到该类。
 */
@Data
public class GoodInfoDTO {
    // 商品编号
    private String goodId;
    // 商品名称
    private String goodName;
    // 商品价格
    private double goodPrice;
    // 类型名称
    private String typeName;
}
