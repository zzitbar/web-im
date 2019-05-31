package com.zzitbar.webim.dto;

import cn.hutool.core.util.IdUtil;

import java.io.Serializable;

/**
 * @auther H2102371
 * @create 2019-05-06 上午 11:24
 * @Description
 */
public class UserDto implements Serializable {

    private String userId;
    private String name;
    private Integer sex;
    private String avatar;// 头像

    public UserDto() {
    }

    public UserDto(String name, Integer sex) {
        this.userId = IdUtil.objectId();
        this.name = name;
        this.sex = sex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
