package servlets;

import com.google.gson.JsonObject;
import models.DAO.Dao;
import models.USER.Hasher;
import models.USER.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class signup extends HttpServlet {
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!";
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] userInfo = servletGeneralFunctions.readLoginCookies(request,response);

        if(userInfo==null){
            request.getRequestDispatcher("login_signup.jsp").forward(request,response);
            return;
        }

        Dao db = (Dao) getServletContext().getAttribute(Dao.DBID);
        try {
            if(db.accountExists(userInfo[0],Hasher.getPasswordHash(userInfo[1]))){
                request.getSession().setAttribute("user",db.getUser(userInfo[0],Hasher.getPasswordHash(userInfo[1])));
                response.sendRedirect("/");
            }else{
                request.getRequestDispatcher("login_signup.jsp").forward(request,response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean passwordIsValid(String password){
        for(int i=0; i<password.length(); i++){
            if(CHARS.indexOf(password.charAt(i)) == -1){
                return false;
            }
        }
        return !password.isEmpty();
    }
    private boolean emailIsValid(String email){
        int idx = email.indexOf("@");
        return idx != -1;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int usernamevalidity = 1;
        int emailValidity = 1;
        int passwordValidity = 1;
        Dao dao = ((Dao) request.getServletContext().getAttribute(Dao.DBID));
        if (!passwordIsValid(password)) {
            passwordValidity = 0;
        }
        if(!emailIsValid(email)){
            emailValidity = 0;
        }
        try {
            if(name.isEmpty() || dao.userNameExists(name)){
                usernamevalidity = 0;
            }
            if(usernamevalidity == 1 && emailValidity == 1 && passwordValidity == 1){
                User user = new User(-1,name, password,email,false);
                dao.addUser(user);
                request.getSession().setAttribute("user",user);
                servletGeneralFunctions.saveLoginCookies(request,response,name,password);
            }
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("usernameRP", usernamevalidity);
            jsonObject.addProperty("emailRP", emailValidity);
            jsonObject.addProperty("passwordRP", passwordValidity);

            // Construct JSON response
            // Send response back to client
            response.getWriter().write(jsonObject.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
