package com.buxz.exceptions;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * api所有结构返回实体，由jsonobject改成该类
 * 最终呈现还是json方式
 * 项目名称：credirCardApi
 * 项目版本：V1.0
 * 包名称：com.xunmei.core.bean
 * 创建人：yuqy
 * 创建时间：2017/3/17 16:20
 * 修改人：yuqy
 * 修改时间：2017/3/17 16:20
 * 修改备注：
 */
public class ApiResult implements Serializable
{
    //禁止new创建对象
    private ApiResult() {}

    /**
     * 统一创建ApiResultBean对象
     * 方便后期扩展
     * @return
     */
    public static ApiResult newInstance(){
        return new ApiResult();
    }

    //消息提示
    private String msg;
    //状态信息
    private boolean flag = true;
    //返回结构
    private Object result;
    //查询出的结构总数
    private int rows;
    //需要跳转的路径
    private String jumpUrl;
    //接口相应时间毫秒单位
    private long time;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
