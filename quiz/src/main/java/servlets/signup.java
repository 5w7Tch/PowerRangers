package servlets;

import models.DAO.Dao;
import models.USER.Hasher;
import models.USER.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class signup extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("user")==null){
            request.getRequestDispatcher("login_signup.jsp").forward(request,response);
        }else{
            response.sendRedirect("/");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        System.out.println(name);
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User(-1,name, Hasher.getPasswordHash(password),email,false);

        Dao dao = ((Dao) request.getServletContext().getAttribute(Dao.DBID));
        try {
            if(name.isEmpty() || dao.userNameExists(name)){
                // Set response content type
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                // Construct JSON response
                String jsonResponse = "{\"string1\": \"found\"}";

                // Send response back to client
                response.getWriter().write(jsonResponse);
            }else{
                dao.addUser(user);
                request.getSession().setAttribute("user",user);
                response.sendRedirect("/");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
