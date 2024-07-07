package servlets;

import models.DAO.Dao;
import models.DAO.mySqlDb;
import models.USER.Hasher;
import models.USER.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


public class login extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // todo: read cookies
        servletGeneralFunctions.checkLoginCookies(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Dao dao = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String res;
            if (dao.accountExists(username , Hasher.getPasswordHash(password))){
                User curUser = dao.getUser(username , password);
                HttpSession session = request.getSession(true);
                session.setAttribute("user", curUser);
                res = "found";
                // todo: save cookies;
                servletGeneralFunctions.saveLoginCookies(request,response,username,password);
            }else{
                res = "notFound";
            }
            String jsonResponse = "{\"res\": \"" + res + "\"}";
            response.getWriter().write(jsonResponse);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
