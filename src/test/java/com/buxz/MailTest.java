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
                .title("测试SpringBoot发送邮件")
                .content("祥哥送祝福了")
                .contentType(MailContentTypeEnum.TEXT)
                .targets(new ArrayList<String>(){{
                    add("1647623919@qq.com");
                }})
                .send();

    }
}
