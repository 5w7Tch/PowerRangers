package servlets;

import models.DAO.Dao;
import models.USER.EmailSender;
import models.USER.User;
import models.notification.Challenge;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import models.notification.Note;
import org.json.JSONObject;

@WebServlet("/sendNote")
public class sendNode extends HttpServlet {

    private String getNote(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject json = new JSONObject(sb.toString());
        return json.getString("text");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sender = (User)request.getSession().getAttribute("user");

        String receiverId = request.getParameter("receiverId");
        String note = getNote(request);
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        EmailSender es = EmailSender.getInstance();
        String topic = "QuizTime Note received";
        String body = sender.getUsername() + " Left you a Note:";
        JSONObject json = new JSONObject();
        if(receiverId != null && note != null){
            try {
                if(db.rememberNote(new Note(0, sender.getId(), Integer.parseInt(receiverId),note, new Date()))){
                    es.sendEmail(db.getUserById(Integer.parseInt(receiverId)).getEmail(), topic, body);
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
