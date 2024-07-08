package servlets;

import com.google.gson.Gson;
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
        String query = request.getParameter("query");
        if (query != null && !query.isEmpty()) {
            Dao db = (Dao) request.getServletContext().getAttribute(Dao.DBID);
            try {
                List<Integer> userIds = db.searchUserByUsername(query);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                String json = new Gson().toJson(userIds);
                response.getWriter().write(json);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        } else {
            request.getRequestDispatcher("/").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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