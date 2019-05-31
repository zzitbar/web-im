package com.zzitbar.webim.cache;

import com.zzitbar.webim.dto.GroupDto;
import com.zzitbar.webim.dto.UserDto;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;

/**
 * @auther H2102371
 * @create 2019-05-06 下午 02:30
 * @Description
 */
public interface Cache {

    void addUser(String userId, UserDto userDto);

    UserDto getUser(String userId);

    /**
     * 用户连接
     * @param userId
     * @param channel
     */
    void addChannel(String userId, Channel channel);

    /**
     * 获取用户连接
     * @param userId
     * @return
     */
    Channel getChannel(String userId);

    /**
     * 新增群组
     * @param groupDto
     */
    void addGroup(GroupDto groupDto);

    /**
     * 获取群组信息
     * @param groupId
     * @return
     */
    GroupDto getGroup(String groupId);

    /**
     * 获取所有群组
     * @return
     */
    List<GroupDto> groupList();

    /**
     * 加入群组
     * @param groupId
     * @param userId
     */
    void joinGroup(String groupId, String userId);

    /**
     * 退出群聊
     * @param groupId
     * @param userId
     */
    void quitGroup(String groupId, String userId);

    /**
     * 获取群组内所有连接
     * @param groupId
     * @return
     */
    Set<Channel> groupChannels(String groupId);
}
