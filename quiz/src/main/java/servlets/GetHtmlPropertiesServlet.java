package servlets;

import com.google.gson.Gson;
import models.DAO.Dao;
import models.DAO.mySqlDb;
import models.USER.Hasher;
import models.USER.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/fetchStrings")
public class GetHtmlPropertiesServlet extends HttpServlet {

    private boolean checkUsernameExists(String username, Dao db) throws SQLException {
        return db.userNameExists(username);
    }
    private boolean checkAccountExists(String username, String password, Dao db) throws SQLException {
        return db.acountExists(username, Hasher.getPasswordHash(password));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String identifier = request.getParameter("identifier");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String string1 = "notFound";

        Dao db = (mySqlDb)request.getServletContext().getAttribute(mySqlDb.DBID);

        if ("signUp".equals(identifier)) {
            try {
                if (checkUsernameExists(username ,db)) {
                    string1 = "found";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("signIn".equals(identifier)) {
            try {
                if (checkAccountExists(username, password, db)) {
                    string1 = "found";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        // Set response content type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Construct JSON response
        String jsonResponse = "{\"string1\": \"" + string1 + "\"}";

        // Send response back to client
        response.getWriter().write(jsonResponse);
    }

}
