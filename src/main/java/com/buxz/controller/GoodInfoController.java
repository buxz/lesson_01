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
