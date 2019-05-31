package com.zzitbar.webim.controller;

import com.zzitbar.webim.cache.CacheUtil;
import com.zzitbar.webim.dto.UserDto;
import com.zzitbar.webim.utils.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther H2102371
 * @create 2019-05-06 上午 09:18
 * @Description
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public String index() {
        if (null != SessionUtil.get(SessionUtil.USER)) {
            return "redirect:/chat";
        } else {
            return "index";
        }
    }

    @PostMapping
    public String index(String name, Integer sex) {
        if (StringUtils.isEmpty(name)) {
            return "redirect:/index";
        }
        if (null == SessionUtil.get(SessionUtil.USER)) {
            UserDto userDto = new UserDto(name, sex);
            SessionUtil.set(SessionUtil.USER, userDto);
            CacheUtil.getCache().addUser(userDto.getUserId(), userDto);
        }
        return "redirect:/chat";
    }
}
