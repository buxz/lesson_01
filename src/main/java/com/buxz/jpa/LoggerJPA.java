package com.buxz.jpa;

import com.buxz.entity.LoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoggerJPA
        extends JpaRepository<LoggerEntity,Long>
{

}
