package servlets.quizServlets;

import models.quizes.Quiz;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/getQuizSessionAttribute")
public class quizTimeFetchServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Get the session, false means do not create if it doesn't exist

        if (session != null) {
            Quiz quiz = (Quiz) session.getAttribute("quiz"); // Get the quiz attribute from session

            JSONObject json = new JSONObject();
            json.put("quizTime", quiz.getDuration());
            json.put("quizId", quiz.getId());

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime nowPlus5Minutes = now.plusMinutes((long)quiz.getDuration());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            json.put("startDate", now.format(formatter));
            json.put("endDate", nowPlus5Minutes.format(formatter));
            json.put("practise", request.getSession().getAttribute("practise").toString());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
