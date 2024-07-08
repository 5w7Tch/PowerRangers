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

@WebServlet("/account")
public class displayStrangersAccount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        String userId = request.getParameter("id");
        if (userId == null) {
            response.sendError(404);
            return;
        }
        User me = (User)request.getSession(false).getAttribute("user");
        User usr;
        try {
             usr = db.getUserById(Integer.parseInt(userId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (usr == null){
            response.sendError(404);
            return;
        }
        if(me.getId().equals(usr.getId())){
            request.getRequestDispatcher("/home.jsp").forward(request,response);
            return;
        }
        request.getSession(false).setAttribute("visitingUser", usr);
        request.getRequestDispatcher("visitingAccount.jsp").forward(request, response);
    }
}
