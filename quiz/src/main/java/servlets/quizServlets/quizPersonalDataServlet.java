package servlets.quizServlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.USER.WritenQuiz;
import models.comparators.compareByDate;
import models.comparators.compareByScore;
import models.comparators.compareByTime;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/personData")
public class quizPersonalDataServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderBy = request.getParameter("orderBy");

        ArrayList<WritenQuiz> dataList = (ArrayList<WritenQuiz>)request.getSession().getAttribute("history");;

        if (orderBy != null) {
            if(orderBy.equals("Time")){
                dataList.sort(new compareByTime());
            }else if(orderBy.equals("Date")){
                dataList.sort(new compareByDate());
            }else{
                dataList.sort(new compareByScore());
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