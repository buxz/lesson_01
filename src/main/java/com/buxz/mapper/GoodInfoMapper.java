package com.buxz.mapper;

import com.buxz.dto.GoodInfoDTO;
import com.buxz.entity.GoodInfoBean;
import com.buxz.entity.GoodTypeBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @Mapper 注解是用于标注接口、抽象类是被MapStruct自动映射的标识，只有存在该注解才会将内部的接口方法自动实现。
 * @Mapping 注解我们用到了两个属性，分别是source、target
    source代表的是映射接口方法内的参数名称，如果是基本类型的参数，参数名可以直接作为source的内容，如果是实体类型，则可以采用实体参数名.字段名的方式作为source的内容
    target代表的是映射到方法方法值内的字段名称
 */
@Mapper(componentModel = "spring")
public interface GoodInfoMapper {

    @Mappings({
                @Mapping(source = "type.name", target = "typeName"),
                @Mapping(source = "good.id",target = "goodId"),
                @Mapping(source = "good.title",target = "goodName"),
                @Mapping(source = "good.price",target = "goodPrice")
    })
    GoodInfoDTO from(GoodInfoBean good, GoodTypeBean type);

}
