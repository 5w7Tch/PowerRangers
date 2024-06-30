package servlets.quizServlets;

import com.google.gson.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class createQuiz extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO : if user is not logged in, then redirect to login page
        request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject quizObj = (JsonObject) readObj(request);
        System.out.println(quizObj.toString());



        response.setContentType("application/json");
        JsonObject object = new JsonObject();
        object.addProperty("status","Its OK");
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
