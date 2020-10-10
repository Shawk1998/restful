package com.test.restful.Controller;

import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Repository
public class LoginServlet extends HttpServlet {
    public static Map<String, HttpSession> user_Session = new HashMap<String, HttpSession>();

    public void mLogin(String user, HttpSession session) {  // 按登录按钮调用的方法
        // TODO Auto-generated method stub
        HttpSession session1 = user_Session.get(user);
        System.out.println(session1);
        if (user_Session.get(user) != null && session != session1) {
            user_Session.put(user, session);
            System.out.println("qingli");
            try {
                session1.invalidate();
            } catch (Exception e) {
                System.out.println("重复登陆踢出上一账号！");
            }
        } else {
            user_Session.put(user, session);
        }
    }
}
