package servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.DAO.Dao;
import models.quizes.Quiz;
import models.quizes.questions.Question;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class editQuiz extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String quizId = request.getParameter("quizId");
            int id = Integer.parseInt(quizId);
            Dao db = (Dao) getServletContext().getAttribute(Dao.DBID);
            if(quizId==null || quizId.isEmpty() || !db.quizExists(id)){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

        }catch (NumberFormatException e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }catch (SQLException e){
            // todo: erase 500 error
            return;
        }
        request.setAttribute("quizId",request.getParameter("quizId"));
        request.getRequestDispatcher("/createQuiz.jsp").forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject obj = (JsonObject) servletGeneralFunctions.readObj(request);
        int quizId = obj.get("quizId").getAsInt();

        Dao db = (Dao) getServletContext().getAttribute(Dao.DBID);
        try {
            Quiz quiz = db.getQuiz(""+quizId);
            JsonArray questions =  getQuestions(quiz.getId(),db);
            JsonObject quizObject = quiz.getJson();
            quizObject.add("questions",questions);
            sendResponse(response,quizObject);
        } catch (SQLException | NoSuchMethodException | InstantiationException | InvocationTargetException |
                IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonArray getQuestions(int quizId,Dao db) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        JsonArray questionArray = new JsonArray();
        List<Question> questions = db.getQuestionsByQuizId(quizId);
        for(Question question : questions){
            questionArray.add(question.generateJson());
        }
        System.out.println(questionArray);
        return questionArray;
    }

    private void sendResponse(HttpServletResponse response,JsonObject data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(data.toString());
        response.getWriter().flush();
    }


}
