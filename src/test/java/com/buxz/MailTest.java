package com.buxz;

import com.buxz.core.MailSender;
import com.buxz.enums.MailContentTypeEnum;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by SQ_BXZ on 2018-12-12.
 */
public class MailTest {

    @Test
    public void sendMail() throws Exception {
        new MailSender()
                .title("哟哟切克闹")
                .content("逗逼 要不要炸你")
                .contentType(MailContentTypeEnum.TEXT)
                .targets(new ArrayList<String>(){{
                    add("353015520@qq.com");
                }})
                .send();

    }
}
