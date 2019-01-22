package com.buxz;

import com.buxz.core.MailSender;
import com.buxz.entity.UserEntityLombok;
import com.buxz.enums.MailContentTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by SQ_BXZ on 2018-12-12.
 */
@Slf4j
public class LombokTest {

    @Test
    public void lombokTest() throws Exception {
        log.info("dddddddddddddd");

        UserEntityLombok entityLombok = new UserEntityLombok("hh",12,"sdf");
//        entityLombok.setName("哟哟切克闹");
        System.out.println(entityLombok.getName());
        System.out.println(entityLombok.toString());

    }
}
