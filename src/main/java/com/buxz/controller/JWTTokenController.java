package com.buxz.controller;

import com.buxz.core.JWTTokenResult;
import com.buxz.entity.JWTTokenEntity;
import com.buxz.entity.JWTUserEntity;
import com.buxz.entity.UserEntity;
import com.buxz.jpa.JWTTokenJPA;
import com.buxz.jpa.JWTUserJPA;
import com.buxz.jpa.UserJPA;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * 这个控制器我们开放，让SpringSecurity不去管理
 *
 */
@RestController
@RequestMapping(value = "/jwt")
public class JWTTokenController {

    @Autowired
    JWTTokenJPA tokenJPA;

    @Autowired
    JWTUserJPA userJPA;

    /**
     * 获取token，更新token
     * @param appId 用户编号
     * @param appSecret 用户密码
     * @return
     */
    @RequestMapping(value = "/token", method = {RequestMethod.POST,RequestMethod.GET})
    public JWTTokenResult token
    (
            @RequestParam String appId,
            @RequestParam String appSecret
    )
    {
        JWTTokenResult token = new JWTTokenResult();
        //appId is null
        if(appId == null || appId.trim() == "")
        {
            token.setFlag(false);
            token.setMsg("appId is not found!");
        }
        //appSecret is null
        else if(appSecret == null || appSecret.trim() == "")
        {
            token.setFlag(false);
            token.setMsg("appSecret is not found!");
        }
        else
        {
            //根据appId查询用户实体
            JWTUserEntity userDbInfo = userJPA.findOne(new Specification<JWTUserEntity>() {
                @Override
                public Predicate toPredicate(Root<JWTUserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    criteriaQuery.where(criteriaBuilder.equal(root.get("appId"), appId));
                    return null;
                }
            });
            //如果不存在
            if (userDbInfo == null)
            {
                token.setFlag(false);
                token.setMsg("appId : " + appId + ", is not found!");
            }
            //验证appSecret是否存在
            else if (!new String(userDbInfo.getAppSecret()).equals(appSecret.replace(" ","+")))
            {
                token.setFlag(false);
                token.setMsg("appSecret is not effective!");
            }
            else
            {
                //检测数据库是否存在该appId的token值
                JWTTokenEntity tokenDBEntity = tokenJPA.findOne(new Specification<JWTTokenEntity>() {
                    @Override
                    public Predicate toPredicate(Root<JWTTokenEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        criteriaQuery.where(criteriaBuilder.equal(root.get("appId"), appId));
                        return null;
                    }
                });
                //返回token值
                String tokenStr = null;
                //tokenDBEntity == null -> 生成newToken -> 保存数据库 -> 写入内存 -> 返回newToken
                if(tokenDBEntity == null)
                {
                    //生成jwt,Token
                    tokenStr = createNewToken(appId);
                    //将token保持到数据库
                    tokenDBEntity = new JWTTokenEntity();
                    tokenDBEntity.setAppId(userDbInfo.getAppId());
                    tokenDBEntity.setBuildTime(String.valueOf(System.currentTimeMillis()));
                    tokenDBEntity.setToken(tokenStr.getBytes());
                    tokenJPA.save(tokenDBEntity);
                }
                //tokenDBEntity != null -> 验证是否超时 ->
                //不超时 -> 直接返回dbToken
                //超时 -> 生成newToken -> 更新dbToken -> 更新内存Token -> 返回newToken
                else
                {
                    //判断数据库中token是否过期，如果没有过期不需要更新直接返回数据库中的token即可
                    //数据库中生成时间
                    long dbBuildTime = Long.valueOf(tokenDBEntity.getBuildTime());
                    //当前时间
                    long currentTime = System.currentTimeMillis();
                    //如果当前时间 - 数据库中生成时间 < 7200 证明可以正常使用
                    long second = TimeUnit.MILLISECONDS.toSeconds(currentTime - dbBuildTime);
                    if (second > 0 && second < 7200) {
                        tokenStr = new String(tokenDBEntity.getToken());
                    }
                    //超时
                    else{
                        //生成newToken
                        tokenStr = createNewToken(appId);
                        //更新token
                        tokenDBEntity.setToken(tokenStr.getBytes());
                        //更新生成时间
                        tokenDBEntity.setBuildTime(String.valueOf(System.currentTimeMillis()));
                        //执行更新
                        tokenJPA.save(tokenDBEntity);
                    }
                }
                //设置返回token
                token.setToken(tokenStr);
            }
        }
        return token;
    }
    /**
     * 创建新token
     * 检查appId以及appSecret-->检查是否存在该appId的对应Token-->根据存在与否、过期与否执行更新或者写入操作-->返回用户请求。
     * 默认过期时间为7200秒
     * @param appId
     * @return
     */
    private String createNewToken(String appId){
        //获取当前时间
        Date now = new Date(System.currentTimeMillis());
        //过期时间
        Date expiration = new Date(now.getTime() + 7200000); // 7200秒
        return Jwts
                .builder()
                .setSubject(appId)
                //.claim(YAuthConstants.Y_AUTH_ROLES, userDbInfo.getRoles())
                .setIssuedAt(now) // 设置生成时间
                .setIssuer("Online YAuth Builder")
                .setExpiration(expiration) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, "HengYuAuthv1.0.0") // 设置签名规则
                .compact();
    }
}
