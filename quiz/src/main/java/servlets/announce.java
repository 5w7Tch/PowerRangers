package servlets;

import models.DAO.Dao;
import models.USER.EmailSender;
import models.USER.User;
import models.announcement.Announcement;
import models.notification.Note;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("/announce")
public class announce extends HttpServlet {
    private String getText(HttpServletRequest request) throws IOException {
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

        String note = getText(request);
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        JSONObject json = new JSONObject();


        try {
            if(db.rememberAnnouncement(new Announcement(0, sender.getId(),note, new Date()))){
                json.put("res",1);
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
