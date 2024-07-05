package servlets.quizServlets;

import models.DAO.Dao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Date;

@WebServlet("/quizSinglePage")
public class onePageQuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizId = request.getParameter("quizId");
        String practise =request.getParameter("practise");
        request.getSession(false).setAttribute("history", null);

        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        try {
            request.getSession(false).setAttribute("quiz", db.getQuiz(quizId));
            Date oldStart = (Date) request.getSession().getAttribute("startTime");
            if(oldStart != null){
                Double time = db.getQuiz(quizId).getDuration();
                time =  time * 60 * 1000L;
                Date oldEnd = new Date(oldStart.getTime() + time.longValue()+60000);
                int res = oldEnd.compareTo(new Date(System.currentTimeMillis()));
                if(res<=0){
                    request.getSession(false).setAttribute("startTime", new Date(System.currentTimeMillis()));
                }
            }else{
                request.getSession(false).setAttribute("startTime", new Date(System.currentTimeMillis()));
            }
            request.getSession(false).setAttribute("questions", db.getQuestionsByQuizId(Integer.parseInt(quizId)));
            request.getSession(false).setAttribute("practise", practise);
            request.getRequestDispatcher("onePageQuiz.jsp").forward(request,response);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
