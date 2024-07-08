package servlets;

import models.DAO.Dao;
import models.USER.EmailSender;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/makeAdmin")
public class makeAdmin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String topic = "Admin Promotion";
        String body = "Hello ";
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        try {
            body+= db.getUserById(Integer.parseInt(userId)).getUsername();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        body+=". I am here to congratulate you your promotion. You have become admin. We recommend using this status for good cause. Good luck";

        EmailSender es = EmailSender.getInstance();
        JSONObject json = new JSONObject();
        if(userId != null){
            try {
                if(db.promoteUser(Integer.parseInt(userId))){
                    es.sendEmail(db.getUserById(Integer.parseInt(userId)).getEmail(), topic, body);
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
