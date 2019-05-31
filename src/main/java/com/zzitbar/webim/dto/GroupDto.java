package com.zzitbar.webim.dto;

import cn.hutool.core.util.IdUtil;

import java.io.Serializable;

/**
 * 群聊信息
 * @auther H2102371
 * @create 2019-05-06 下午 02:47
 * @Description
 */
public class GroupDto implements Serializable {
    private String groupId;// ID
    private String name; // 群组名
    private String avatar;// 头像
    private Integer cnt = 0;// 人数
    private Integer containsMe = 0;// 当前用户是否在该群组

    public GroupDto() {
    }

    public GroupDto(String name) {
        this.groupId = IdUtil.objectId();
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Integer getContainsMe() {
        return containsMe;
    }

    public void setContainsMe(Integer containsMe) {
        this.containsMe = containsMe;
    }
}
