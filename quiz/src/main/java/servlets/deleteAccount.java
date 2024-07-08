package servlets;

import models.DAO.Dao;
import models.USER.EmailSender;
import models.USER.User;
import models.notification.Challenge;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("/deleteAccount")
public class deleteAccount extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String topic = "Account Deletion";
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        String body = "Hello ";
        try {
            body+= db.getUserById(Integer.parseInt(userId)).getUsername();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        body+=". Sorry to notify you that your QuizTime Account has been deleted";
        String email = null;
        try {
            email = db.getUserById(Integer.parseInt(userId)).getEmail();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        EmailSender es = EmailSender.getInstance();
        JSONObject json = new JSONObject();
        if(userId != null){
            try {
                if(db.deleteUser(Integer.parseInt(userId))){
                    es.sendEmail(email, topic, body);
                    json.put("res",1);
                }else{
                    json.put("res",0);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            json.put("res",0);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
