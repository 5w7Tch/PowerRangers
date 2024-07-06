package servlets;

import models.DAO.Dao;
import models.USER.EmailSender;
import models.USER.User;
import models.friend.FriendRequest;
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

@WebServlet("/sendChallenge")
public class sendChallenge extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sender = (User)request.getSession().getAttribute("user");

        String receiverId = request.getParameter("receiverId");
        String quizId = request.getParameter("quizId");
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        EmailSender es = EmailSender.getInstance();
        String topic = "QuizTime Challenge received";
        String quizName;
        try {
            quizName = db.getQuiz(quizId).getName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String body = sender.getUsername() + " Challenged you into writing Quiz: "+ quizName;

        JSONObject json = new JSONObject();

        if(receiverId != null && quizId != null){
            try {
                if(db.sendChallenge(new Challenge(0, sender.getId(), Integer.parseInt(receiverId),Integer.parseInt(quizId), new Date()))){
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
