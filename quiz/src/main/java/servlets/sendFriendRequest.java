package servlets;



import models.DAO.Dao;
import models.USER.EmailSender;
import models.USER.User;
import models.friend.FriendRequest;

import javax.jws.soap.SOAPBinding;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.json.simple.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

@WebServlet("/sendFriendRequest")
public class sendFriendRequest extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sender = (User)request.getSession().getAttribute("user");
        String receiverId = request.getParameter("receiverId");
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        EmailSender es = EmailSender.getInstance();
        String topic = "QuizTime Friend Request received";
        String body = sender.getUsername() + " Sent you Friend Request";
        JSONObject json = new JSONObject();

        try {
            if(receiverId != null && !db.friendConnectionExists(sender.getId(), Integer.parseInt(receiverId))){
                try {
                    if(db.addFriendRequest(new FriendRequest(0, sender.getId(), Integer.parseInt(receiverId), new Date()))){
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
