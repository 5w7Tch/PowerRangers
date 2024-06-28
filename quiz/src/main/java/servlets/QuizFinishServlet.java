package servlets;

import models.USER.Quiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@WebServlet("/finished")
public class QuizFinishServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date finishDate = new Date();
        Date startDate = (Date) request.getSession(false).getAttribute("startTime");

        long time1 = finishDate.getTime();
        long time2 = startDate.getTime();
        double differenceInMinutes = (time1-time2)/(1000.0 * 60.0);
        if(differenceInMinutes>((Quiz)request.getSession(false).getAttribute("quiz")).getDuration()){
            //give him window where he is notified that he took to mush time and can restart or go to his home page.
            //this will happen only if quiz writer cheats and changes client side code.
            //so we can even ban him for doing this, erase his account and give red banner that he cheated
        }else{
            //display quiz result page
        }
    }
}
