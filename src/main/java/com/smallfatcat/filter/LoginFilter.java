package com.smallfatcat.filter;

import com.alibaba.fastjson.JSON;
import com.smallfatcat.common.BaseContext;
import com.smallfatcat.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zsz
 * @Description-拦截器
 * @date 2022/10/5
 */
@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
@Slf4j
public class LoginFilter implements Filter {

    //路径比较,路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1：获取本次请求的uri
        String requestURI = request.getRequestURI();
        //一些不需要处理的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        //2：判断本次请求是否需要处理
        boolean cheak = cheak(urls, requestURI);
        //3：不需要处理，直接放行
        if (cheak) {
            filterChain.doFilter(request, response);
            return;
        }
        //4：判断登录状态，如果已登录直接放行
        if (request.getSession().getAttribute("employee") != null) {
            //将当前登录用户的id从session里面取出来
            Long empId = (Long) request.getSession().getAttribute("employee");
            //通过封装的threadlocal，将当前用户id放到里面
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request, response);
            return;
        }
        //4：判断登录状态，如果已登录直接放行
        if (request.getSession().getAttribute("user") != null) {
            //将当前登录用户的id从session里面取出来
            Long userId = (Long) request.getSession().getAttribute("user");
            //通过封装的threadlocal，将当前用户id放到里面
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        }
        //5：如果未登录则返回登录结果,通过输出流像客户端响应数据
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
        return;
    }

    /**
     * 检查本次请求是否需要放行
     *
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean cheak(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }

}
