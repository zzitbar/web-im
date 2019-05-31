package com.zzitbar.webim.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 消息
 *
 * @auther H2102371
 * @create 2019-05-06 下午 01:55
 * @Description
 */
public class Message implements Serializable {

    private Integer command; // 命令
    private String msg; // 消息体
    private String userId;// 用户ID
    private String groupId; // 群聊ID
    private Date createTime = new Date();
    private UserDto userDto;
    private Integer userCnt;// 群聊用户数

    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public Integer getUserCnt() {
        return userCnt;
    }

    public void setUserCnt(Integer userCnt) {
        this.userCnt = userCnt;
    }
}
