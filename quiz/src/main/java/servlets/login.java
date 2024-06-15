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
import java.io.IOException;
import java.sql.SQLException;


public class login extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user")==null){
            request.getRequestDispatcher("login_signup.jsp").forward(request,response);
        }else{
            response.sendRedirect("/");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Dao dao = (Dao)request.getServletContext().getAttribute(Dao.DBID);

        try {
            if (dao.accountExists(username , password)){
                User curUser = dao.getUser(username , password);
                request.getSession().setAttribute("user" , curUser);
                response.sendRedirect("/");
            }else{
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                String res = "notFound";
                String jsonResponse = "{\"res\": \"" + res + "\"}";

                response.getWriter().write(jsonResponse);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
