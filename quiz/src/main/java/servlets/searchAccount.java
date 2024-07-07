package servlets;

import models.DAO.Dao;
import models.USER.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/searchAccount")
public class searchAccount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("query");

        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);

        try {
            int userId = db.getUserByName(userName);
            if (userId == -1){
                userId = ((User) request.getSession().getAttribute("user")).getId();
            }
            request.getRequestDispatcher("/account?id=" + userId).forward(request , response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}