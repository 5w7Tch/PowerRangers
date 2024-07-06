package models.Filters;
import models.USER.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class authFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = (User) request.getSession().getAttribute("user");

        if("/login".equals(request.getServletPath()) || "/signup".equals(request.getServletPath())){
            if(user!=null){
                response.sendRedirect("/");
                return;
            }
        }else{
            if(user==null && !"/static".equals(request.getServletPath())){
                response.sendRedirect("/login");
                return;
            }
        }

        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
