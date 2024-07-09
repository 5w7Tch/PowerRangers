package servlets.quizServlets;

import models.DAO.Dao;
import models.quizes.Quiz;
import models.quizes.questions.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

@WebServlet("/quizMultiplePage")
public class multiplePageQuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizId = request.getParameter("quizId");
        String practise =request.getParameter("practise");
        request.getSession(false).setAttribute("history", null);
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        Quiz quiz = null;
        try {
            quiz =db.getQuiz(quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            request.getSession(false).setAttribute("quiz", quiz);
            Date oldStart = (Date) request.getSession().getAttribute("startTime");
            if(oldStart != null){
                Double time = db.getQuiz(quizId).getDuration();
                time =  time * 60 * 1000L;
                Date oldEnd = new Date(oldStart.getTime() + time.longValue()+60000);
                int res = oldEnd.compareTo(new Date(System.currentTimeMillis()));
                if(res<=0){
                    request.getSession(false).setAttribute("questions", null);
                    request.getSession(false).setAttribute("startTime", new Date(System.currentTimeMillis()));
                }
            }else{
                request.getSession(false).setAttribute("questions", null);
                request.getSession(false).setAttribute("startTime", new Date(System.currentTimeMillis()));
            }
            if(request.getSession(false).getAttribute("questions") == null ){
                List<Question> quests = db.getQuestionsByQuizId(Integer.parseInt(quizId));
                if(quiz.isQuestionSecRand()){
                    Collections.shuffle(quests);
                }
                request.getSession(false).setAttribute("questions", quests);
            }
            request.getSession(false).setAttribute("practise", practise);
            response.setCharacterEncoding("UTF-8");
            request.getRequestDispatcher("multiplePageQuiz.jsp").forward(request,response);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
