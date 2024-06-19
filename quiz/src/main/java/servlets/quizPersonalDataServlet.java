package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.DAO.Dao;
import models.USER.Quiz;
import models.USER.WritenQuiz;
import models.comparators.compareByDate;
import models.comparators.compareByTime;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet("/personData")
public class quizPersonalDataServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderBy = request.getParameter("orderBy");

        List<WritenQuiz> dataList = null;
        try {
            dataList = ((Dao)request.getServletContext().getAttribute(Dao.DBID)).getQuizHistory(((Quiz)request.getSession().getAttribute("quiz")).getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (orderBy != null) {
            if(orderBy.equals("Time")){
                dataList.sort(new compareByTime());
            }else if(orderBy.equals("Date")){
                dataList.sort(new compareByDate());
            }
        }
        // Convert the list to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(dataList);

        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Write the JSON response
        response.getWriter().write(jsonResponse);
    }
}