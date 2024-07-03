package servlets.quizServlets;

import models.USER.Quiz;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/finished")
public class QuizFinishServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date finishDate = new Date();
        Date startDate = (Date) request.getSession(false).getAttribute("startTime");
        long time1 = finishDate.getTime();
        long time2 = startDate.getTime();
        double differenceInMinutes = Math.abs((time1-time2)/(1000.0 * 60.0));
        JSONObject json = new JSONObject();

        if(differenceInMinutes>((Quiz)request.getSession(false).getAttribute("quiz")).getDuration()+1){
            json.put("bad", 1);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
        }else{
            //remember

            json.put("bad", 0);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
        }
    }
}
