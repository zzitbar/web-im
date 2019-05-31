package com.zzitbar.webim.cache;

import com.zzitbar.webim.dto.GroupDto;
import com.zzitbar.webim.dto.UserDto;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @auther H2102371
 * @create 2019-05-06 下午 02:30
 * @Description
 */
public class MemoryCache implements Cache {

    // 用户连接信息
    private static ConcurrentHashMap<String, Channel> CHANNELS = new ConcurrentHashMap<>();
    // 用户基础信息
    private static ConcurrentHashMap<String, UserDto> USERS = new ConcurrentHashMap();
    // 群组信息
    private static ConcurrentHashMap<String, GroupDto> GROUPS = new ConcurrentHashMap();
    // 群组用户信息
    private static ConcurrentHashMap<String, Set<String>> GROUP_USERS = new ConcurrentHashMap();

    protected MemoryCache() {
        super();
    }

    @Override
    public void addUser(String userId, UserDto userDto) {
        USERS.put(userId, userDto);
    }

    @Override
    public UserDto getUser(String userId) {
        return USERS.get(userId);
    }

    @Override
    public void addChannel(String userId, Channel channel) {
        CHANNELS.put(userId, channel);
    }

    @Override
    public Channel getChannel(String userId) {
        return CHANNELS.get(userId);
    }

    @Override
    public void addGroup(GroupDto groupDto) {
        GROUPS.put(groupDto.getGroupId(), groupDto);
    }

    @Override
    public GroupDto getGroup(String groupId) {
        return GROUPS.get(groupId);
    }

    @Override
    public List<GroupDto> groupList() {
        if (GROUPS.isEmpty()) {
            addGroup(new GroupDto("游戏"));
            addGroup(new GroupDto("工作"));
            addGroup(new GroupDto("学习"));
            addGroup(new GroupDto("电影"));
        }
        if (GROUP_USERS.isEmpty()) {
            GROUPS.entrySet().stream().forEach(e -> {
                GROUP_USERS.put(e.getKey(), new HashSet<>());
            });
        }
        GROUPS.entrySet().stream().forEach(e -> {
            e.getValue().setCnt(GROUP_USERS.get(e.getKey()).size());
        });

        return GROUPS.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
    }

    @Override
    public void joinGroup(String groupId, String userId) {
        GROUP_USERS.entrySet().stream().forEach(e -> {
            Set<String> channels = e.getValue();
            if (groupId.equals(e.getKey())) {
                if (null == channels) {
                    channels = new HashSet<>();
                }
                GroupDto groupDto = getGroup(groupId);
                groupDto.setCnt(groupDto.getCnt() + 1);

                channels.add(userId);
            } else {
                if (null != channels && channels.contains(userId)) {
                    channels.remove(userId);
                }
            }
        });
    }

    @Override
    public void quitGroup(String groupId, String userId) {
        GROUP_USERS.get(groupId).remove(userId);
    }

    @Override
    public Set<Channel> groupChannels(String groupId) {
        Set<String> userIds = GROUP_USERS.get(groupId);
        return userIds.stream().map(e -> CHANNELS.get(e)).collect(Collectors.toSet());
    }
}
