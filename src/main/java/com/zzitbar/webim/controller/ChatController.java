package com.zzitbar.webim.controller;

import com.zzitbar.webim.config.NettyAccountConfig;
import com.zzitbar.webim.dto.UserDto;
import com.zzitbar.webim.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther H2102371
 * @create 2019-05-06 上午 09:33
 * @Description
 */
@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private NettyAccountConfig nettyAccountConfig;

    @PostMapping
    public String chat(String name, Integer sex, Model model, HttpServletRequest request) {
        if (StringUtils.isEmpty(name)) {
            return "redirect:/index";
        }
        if (null == SessionUtil.get(SessionUtil.USER)) {
            SessionUtil.set(SessionUtil.USER, new UserDto(name, sex));
        }
        model.addAttribute("websocketUrl", websocketUrl(request));
        return "chat";
    }

    @GetMapping
    public String chat(Model model, HttpServletRequest request) {
        model.addAttribute("websocketUrl", websocketUrl(request));
        return "chat";
    }

    private String websocketUrl(HttpServletRequest request) {
        return "ws://" + request.getRemoteHost() + ":" + nettyAccountConfig.getPort() + nettyAccountConfig.getContextPath();
    }
}
