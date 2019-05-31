package com.zzitbar.webim.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @auther H2102371
 * @create 2019-05-06 上午 09:28
 * @Description
 */
public class SessionUtil {

    public static final String USER = "user";

    public static Object get(String key) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        return session.getAttribute(key);
    }

    public static void set(String key, Object value) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.setAttribute(key, value);
    }

    public static void remove(String key) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.removeAttribute(key);
    }
}
