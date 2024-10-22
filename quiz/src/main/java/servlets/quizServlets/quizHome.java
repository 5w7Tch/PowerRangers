package servlets.quizServlets;

import com.google.gson.JsonObject;
import models.DAO.Dao;

import org.json.simple.JSONObject;

import models.USER.User;
import models.achievement.UserAchievement;


import javax.faces.view.ActionSource2AttachedObjectTarget;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class quizHome extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        String quiz = request.getParameter("quizid");
        Dao myDb = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        String pdone = request.getParameter("practise");


        if(pdone != null){
            try {
                practiceQuizAchievement(request);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quiz = request.getParameter("quizid");
        Dao myDb = (Dao)request.getServletContext().getAttribute(Dao.DBID);

        try {
            Integer id = Integer.parseInt(quiz);
        } catch (NumberFormatException e) {
            sendResponse(response, "false");
            return;
        }
        try {
            if (quiz == null || !myDb.quizExists(Integer.parseInt(quiz))) {
                sendResponse(response, "false");
            }else{
                sendResponse(response, "true");
            }
        } catch (SQLException e) {
            sendResponse(response, "false");
        }
    }

    private void sendResponse(HttpServletResponse response, String data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject json = new JSONObject();
        json.put("result", data);
        response.getWriter().write(json.toString());
    }
    private void practiceQuizAchievement(HttpServletRequest request) throws SQLException {
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        User user = (User)request.getSession().getAttribute("user");
        Date currentTimestamp = new Date(System.currentTimeMillis());
        int userId = user.getId();
        int achievementId = db.getAchievementIdFromType(5);
        UserAchievement userAchievement = new UserAchievement(0, userId, achievementId, currentTimestamp);
        db.putUserAchievements(userAchievement);

    }
}

