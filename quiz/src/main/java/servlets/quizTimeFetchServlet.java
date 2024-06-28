package servlets;

import models.USER.Quiz;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
