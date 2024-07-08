package servlets.quizServlets;

import com.google.gson.JsonObject;
import models.DAO.Dao;
import org.json.simple.JSONObject;

import javax.faces.view.ActionSource2AttachedObjectTarget;
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
        response.setCharacterEncoding("UTF-8");

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
            if (quiz == null || !myDb.quizExists(Integer.parseInt(quiz))) {
                System.out.println(1);
                sendResponse(response, "false");
                return;
            }else{
                System.out.println(2);
                sendResponse(response, "true");
                return;
            }
        } catch (SQLException e) {
            System.out.println(3);
            sendResponse(response, "false");
            return;
        }
    }

    private void sendResponse(HttpServletResponse response, String data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject json = new JSONObject();
        json.put("result", data);
        response.getWriter().write(json.toString());
    }
}

