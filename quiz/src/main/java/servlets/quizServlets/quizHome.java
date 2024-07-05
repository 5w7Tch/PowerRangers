package servlets.quizServlets;

import models.DAO.Dao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class quizHome extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        String quiz = request.getParameter("quizid");
        Dao myDb = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        try {
            if(quiz != null && myDb.getQuiz(quiz) != null && request.getSession().getAttribute("user") != null){;
                request.getRequestDispatcher("generateQuizHomePage.jsp").forward(request,response);
            }else{
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

