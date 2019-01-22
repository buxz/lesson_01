package com.buxz.jpa;

import com.buxz.entity.GoodInfoBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodInfoJPA extends
        JpaRepository<GoodInfoBean, Long>{

}
