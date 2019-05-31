package com.zzitbar.webim.interceptors;

import com.zzitbar.webim.utils.SessionUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN_KEY = "X-Token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = request.getHeader(TOKEN_KEY);
//        if (StringUtils.hasText(token) && TokenUtils.validate(token)) {
//            return true;
//        } else {
        //获取session的值
        Object user = SessionUtil.get(SessionUtil.USER);
        //判断是否有保存值，有即证明登录成功
        if (user != null) {
            //session不空，表示有保存值，有登陆，放行
            return true;
        } else {
            //没有值，没有登录，返回到登录页面：
            response.sendRedirect(request.getContextPath() + "/index");
//            request.getRequestDispatcher("/login").forward(request, response);
            return false;
        }
//        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}