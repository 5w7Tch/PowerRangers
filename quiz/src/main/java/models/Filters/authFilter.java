package models.Filters;
import models.DAO.Dao;
import models.USER.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class authFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = (User) request.getSession().getAttribute("user");
        Dao db = (Dao) request.getServletContext().getAttribute(Dao.DBID);

        if("/login".equals(request.getServletPath()) || "/signup".equals(request.getServletPath())){
            if (checkUserExists(request, response, user, db)) return;
        }else{
            if(user==null && !"/static".equals(request.getServletPath())){
                response.sendRedirect("/login");
                return;
            }

            if (checkUserExists(request, response, user, db)) return;
        }

        filterChain.doFilter(request,response);
    }

    private boolean checkUserExists(HttpServletRequest request, HttpServletResponse response, User user, Dao db) throws IOException {
        if(user!=null){
            try {
                if(!db.userNameExists(user.getUsername())){
                    request.getSession().setAttribute("user",null);
                    response.sendRedirect("/");
                    return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }


    @Override
    public void destroy() {

    }
}
