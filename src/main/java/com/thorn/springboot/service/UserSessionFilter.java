package com.thorn.springboot.service;

import com.thorn.springboot.model.userWithBLOBs;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录过滤
 */

public class UserSessionFilter implements Filter {
    //标示符：表示当前用户未登录(可根据自己项目需要改为json样式)
    String NO_LOGIN = "您还未登录";

    //不需要登录就可以访问的路径(比如:注册登录等)
    String[] includeUrls = new String[]{"/BBS/", "Login/", "/user/user", "user/login",
            "user/reg", "Home"};


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        //System.out.println("filter url:" + uri);

        userWithBLOBs user = (userWithBLOBs) request.getSession().getAttribute("userSession");
        Object obj = null;
        if (user != null && user.getUsername() != null) {
            obj = user;
        }
        if (null == obj) {
            boolean doFilter = isNeedFilter(uri);
            if (doFilter) {
                // 如果session中不存在登录者实体，则弹出框提示重新登录
                PrintWriter out = response.getWriter();
                //String loginPage = request.getContextPath()+"/index.jsp";
                String loginPage = "/user/user";
                StringBuilder builder = new StringBuilder();
                builder.append("<script type=\"text/javascript\">");
                builder.append("window.top.location.href='");
                builder.append(loginPage);
                builder.append("';");
                builder.append("</script>");
                out.print(builder.toString());
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            //已登录不能返回这个页面
            if (uri.indexOf("/user/user") == -1) {
                filterChain.doFilter(request, response);
            } else {
                //返回BBS主页面
                String requestType = request.getHeader("X-Requested-With");
                //判断是否是ajax请求
                if (requestType != null && "XMLHttpRequest".equals(requestType)) {
                    response.getWriter().write(this.NO_LOGIN);
                } else {
                    PrintWriter out = response.getWriter();
                    //String loginPage = request.getContextPath()+"/index.jsp";
                    String index = "/BBS/page";
                    StringBuilder builder = new StringBuilder();
                    builder.append("<script type=\"text/javascript\">");
                    builder.append("window.top.location.href='");
                    builder.append(index);
                    builder.append("';");
                    builder.append("</script>");
                    out.print(builder.toString());
                }
            }
        }
    }

    /**
     * @param uri
     * @Author: xxxxx
     * @Description: 是否需要过滤
     * @Date: 2018-03-12 13:20:54
     */
    public boolean isNeedFilter(String uri) {

        if (uri.equals("/")) {
            return false;
        }
        if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".jpg") || uri.endsWith("" +
                ".png")) {
            return false;
        }
        for (String includeUrl : includeUrls) {
            if (uri.indexOf(includeUrl) != -1) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}

