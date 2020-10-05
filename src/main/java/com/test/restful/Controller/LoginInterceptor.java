package com.test.restful.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

//        UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConst.USER_SESSION_KEY);
        String empId = (String) request.getSession().getAttribute("user");
        logger.info(request.getRequestURI().toString());
        if (empId == null || empId.equals(""))  {
            response.sendRedirect("/login");
            logger.info("请先登录");
            return false;
        }
        return true;
    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        logger.info("postHandle...");
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        logger.info("afterCompletion...");
//    }
}
