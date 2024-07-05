package servlets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.DAO.Dao;
import models.friend.FriendRequest;
import models.friend.abstractions.IFriendRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/AcceptFriendRequest")
public class acceptFriendRequest extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(request.getSession().getAttribute("user")!=null){
            request.getRequestDispatcher("/home.jsp").forward(request,response);
        }else{
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Dao db = ((Dao)request.getServletContext().getAttribute(Dao.DBID));
        JsonObject friendRequestJson = (JsonObject) readObj(request);
        int friendRequestId = friendRequestJson.get("friendRequestId").getAsInt();
        boolean success = false;
        try {
            IFriendRequest friendRequest = db.getFriendRequestById(friendRequestId);
            if(friendRequest == null)
                throw new SQLException("friend request can't be null");
            success = db.acceptFriendRequest(friendRequest);

        } catch (SQLException e) {
            // TODO
        }
        response.setContentType("application/json");
        JsonObject object = new JsonObject();
        if(success) {
            object.addProperty("status","Its OK");
        } else {
            object.addProperty("status","Its BAD");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(object.toString());
        response.getWriter().flush();
    }

    private JsonElement readObj(HttpServletRequest request){
        StringBuilder data = new StringBuilder();
        try(BufferedReader reader = request.getReader()){
            String line;
            while ((line= reader.readLine())!=null){
                data.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return JsonParser.parseString(data.toString());
    }
}
