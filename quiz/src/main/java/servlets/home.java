package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class home extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // if logged in show user home page
        // else redirect to /login

        if(request.getSession().getAttribute("user")!=null){
//            response.sendRedirect("/createQuiz");
            request.getRequestDispatcher("/home.jsp").forward(request,response);
        }else{
            response.sendRedirect("/login");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
