package com.buxz.controller;

import com.buxz.entity.GoodInfoBean;
import com.buxz.jpa.GoodInfoJPA;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 * HelloController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>01/23/2019</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HelloControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GoodInfoJPA goodInfoJPA;

    @Before
    public void before() throws Exception {
        // 获取mock对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: HelloWorld(int number)
     *
     */
    @Test
    public void testHelloWorld() throws Exception {

        MvcResult mvcResult = mockMvc.perform( // perform方法其实只是为了构建一个请求，并且返回ResultActions实例，该实例则是可以获取到请求的返回内容。
                MockMvcRequestBuilders.get("/hello/say") // MockMvcRequestBuilders该抽象类则是可以构建多种请求方式，如：Post、Get、Put、Delete等常用的请求方式，其中参数则是我们需要请求的本项目的相对路径，/则是项目请求的根路径。
                .param("number","20") //param方法用于在发送请求时携带参数，当然除了该方法还有很多其他的方法
        ).andReturn(); // andReturn 方法则是在发送请求后需要获取放回时调用，该方法返回MvcResult对象，该对象可以获取到返回的视图名称、返回的Response状态、获取拦截请求的拦截器集合等。


        int status = mvcResult.getResponse().getStatus();
        String responserString = mvcResult.getResponse().getContentAsString();

        Assert.assertEquals("请求错误",200,status);
        Assert.assertEquals("返回结果不一致","Hello World",responserString);

    }

    /**
     *
     * Method: list()
     *
     */
    @Test
    public void testList() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/hello/list")
                        .param("number","20")
        ).andReturn();

        HandlerInterceptor[] interceptors = mvcResult.getInterceptors();
        for (HandlerInterceptor interceptor : interceptors) {
            log.info("经历的拦截器的名称=="+interceptor.getClass().getName());
        }

        int status = mvcResult.getResponse().getStatus();
        String responserString = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals("请求错误",200,status);
        log.info("返回参数=="+responserString);

    }

    /**
     *
     * Method: add()
     *
     */
    @Test
    public void testAdd() throws Exception {

        GoodInfoBean bean = goodInfoJPA.findOne(1L);

        log.info("获取的商品信息=="+bean.toString());

    }

    /**
     *
     * Method: delete(Long id)
     *
     */
    @Test
    public void testDelete() throws Exception {

    }


} 
