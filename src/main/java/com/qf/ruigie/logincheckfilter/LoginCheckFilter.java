package com.qf.ruigie.logincheckfilter;

import com.alibaba.fastjson.JSON;
import com.qf.ruigie.common.BaseContext;
import com.qf.ruigie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.beancontext.BeanContext;
import java.io.IOException;
import java.security.PublicKey;

/**
 * 设置过滤器
 * 检查用户是否登入
 */

@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器,支持通配符(**)
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1.获取本次请求的URl
        String requestURI = request.getRequestURI();
        log.info("拦截到请求:{}", requestURI);

        //定义不需要处理的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };

        //2.判断本次请求是否需要处理
        boolean chek = chek(urls, requestURI);

        //3.如果不需要处理,则直接放行
        if (chek) {
            log.info("本次请求{}不需要处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        //4.判断登入状态,如果已登入,则直接放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已登入id为: {}", request.getSession().getAttribute("employee"));

            Long empid = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empid);


            filterChain.doFilter(request, response);
            return;
        }

        log.info("用户未登入");
        //5.如果未登入则返回登入结果
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

//        log.info("拦截到请求: {}",request.getRequestURI());
//        filterChain.doFilter(request, response);
    }

    /**
     * 路劲匹配,检查本次请求是否需要放行
     *
     * @param requestURI
     * @return
     */
    public boolean chek(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
