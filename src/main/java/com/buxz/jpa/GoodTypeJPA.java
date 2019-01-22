package com.buxz.jpa;

import com.buxz.entity.GoodTypeBean;
import com.buxz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public interface GoodTypeJPA extends
        JpaRepository<GoodTypeBean, Long>{

}
