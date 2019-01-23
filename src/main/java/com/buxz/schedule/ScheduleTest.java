package com.buxz.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by SQ_BXZ on 2019-01-21.
 */
@Component
public class ScheduleTest {

//    @Scheduled(cron = "*/5 * * * * ?")
    public void cron(){
        System.out.println("执行测试时间：" + new Date(System.currentTimeMillis()));
    }

}
