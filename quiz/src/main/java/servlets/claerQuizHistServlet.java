package servlets;

import models.DAO.Dao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/clearQuizHistory")
public class claerQuizHistServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.getSession().setAttribute("history", null);
        String quizId = request.getParameter("quizId");
        try {
            ((Dao)request.getServletContext().getAttribute(Dao.DBID)).clearQuizHistory(quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/quiz?quizid=" + quizId);
    }
}
