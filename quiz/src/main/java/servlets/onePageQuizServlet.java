package servlets;

import models.DAO.Dao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

@WebServlet("/quizSinglePage")
public class onePageQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizId = request.getParameter("quizId");
        request.getSession(false).setAttribute("history", null);
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        try {
            request.getSession(false).setAttribute("quiz", db.getQuiz(quizId));
            request.getSession(false).setAttribute("startTime", new Date());
            request.getSession(false).setAttribute("questions", db.getQuizQuestions(quizId));
            request.getRequestDispatcher("onePageQuiz.jsp").forward(request,response);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
