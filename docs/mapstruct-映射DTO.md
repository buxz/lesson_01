# MapStruct映射DTO
> MapStruct是一种类型安全的bean映射类生成java注释处理器。
## 1. 使用步骤
1. 引入依赖
    ```html
		<!--mapStruct依赖-->
		<dependency>
			<groupId>org.mapstruct</groupId>
			<artifactId>mapstruct-jdk8</artifactId>
			<version>RELEASE</version>
		</dependency>
		<dependency>
			<groupId>org.mapstruct</groupId>
			<artifactId>mapstruct-processor</artifactId>
			<version>RELEASE</version>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>javax.inject</groupId>
			<artifactId>javax.inject</artifactId>
			<version>1</version>
		</dependency>
    ```
2. 创建DTO
```java

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

```
3. 创建映射Mapper
```java
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

```
4. 应用（以GoodInfoController示例）
````java

package com.buxz.controller;

import com.buxz.dto.GoodInfoDTO;
import com.buxz.entity.GoodInfoBean;
import com.buxz.entity.GoodTypeBean;
import com.buxz.jpa.GoodInfoJPA;
import com.buxz.jpa.GoodTypeJPA;
import com.buxz.mapper.GoodInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodInfoController {

    @Autowired
    private GoodInfoJPA goodInfoJPA;

    @Autowired
    private GoodTypeJPA goodTypeJPA;

    @Autowired
    private GoodInfoMapper goodInfoMapper;

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/{id}")
    public GoodInfoDTO detail(@PathVariable("id") Long id)
    {
        //查询商品基本信息
        GoodInfoBean goodInfoBean = goodInfoJPA.findOne(id);
        //查询商品类型基本信息
        GoodTypeBean typeBean = goodTypeJPA.findOne(goodInfoBean.getTypeId());
        //返回转换dto
        return goodInfoMapper.from(goodInfoBean,typeBean);
    }

}
````
>  输入访问地址 http://127.0.0.1:8080/detail/1,显示如下
```json
{
goodId: "1",
goodName: "芹菜",
goodPrice: 12.4,
typeName: "青菜"
}
```

## 2. Tips
1. MapStruct为我们提供了多种的获取Mapper的方式, 示例Spring 方式
```html

//注解配置
@Mapper(componentModel = "spring")

//注入Mapper实现类
@Autowired
private GoodInfoMapper goodInfoMapper;

//调用
goodInfoMapper.from(goodBean,goodTypeBean);
```


