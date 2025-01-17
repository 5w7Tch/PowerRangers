package servlets.quizServlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/success")
public class successfullyFinished extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        httpServletRequest.getRequestDispatcher("quizFinish.jsp").forward(httpServletRequest, response);
    }
}
