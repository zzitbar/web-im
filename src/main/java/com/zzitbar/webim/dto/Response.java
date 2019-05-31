package com.zzitbar.webim.dto;

import com.zzitbar.webim.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * @auther H2102371
 * @create 2019-05-06 下午 03:12
 * @Description
 */
public class Response implements Serializable {

    private Integer type; // 类型
    private Object msg;// 响应消息

    public Response(Integer type, Object msg) {
        this.type = type;
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public static Response groupList(List<GroupDto> groupDtos) {
        return new Response(Constants.RESPONSE_TYPE.GROUP_LIST.getValue(), groupDtos);
    }

    public static Response joinGroup(UserDto userDto) {
        return new Response(Constants.RESPONSE_TYPE.JOIN_GROUP.getValue(), userDto);
    }
    public static Response quitGroup(UserDto userDto) {
        return new Response(Constants.RESPONSE_TYPE.QUIT_GROUP.getValue(), userDto);
    }
    public static Response message(Message msg) {
        return new Response(Constants.RESPONSE_TYPE.MESSAGE.getValue(), msg);
    }
}
