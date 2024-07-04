package servlets;

import com.google.gson.*;
import models.DAO.Dao;
import models.USER.User;
import models.quizes.Quiz;
import models.quizes.questions.Question;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Date;

public class createQuiz extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO : if user is not logged in, then redirect to login page
        if(request.getSession().getAttribute("user")==null){
            response.sendRedirect("/");
            return;
        }
        request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject quizObj = (JsonObject) readObj(request);

        JsonArray questionArr = quizObj.get("questions").getAsJsonArray();
        int quizId = 0;
        try {
            quizId = uploadQuizInfo(quizObj,request);
            uploadQuestionsToDB(questionArr,quizId);
        } catch (SQLException e) {
            //TODO: erase 500 error
            throw new RuntimeException(e);
        }

        sendResponse(response);
    }

    private int uploadQuizInfo(JsonObject quizObj,HttpServletRequest request) throws SQLException {
        int id = -1;
        int authorId = ((User) request.getSession().getAttribute("user")).getId();
        Date creationDate = new Date(System.currentTimeMillis());
        String title = quizObj.get("title").getAsString();
        String description = quizObj.get("description").getAsString();
        boolean isRandom = quizObj.get("isRandom").getAsBoolean();
        boolean immediateCorrection = quizObj.get("immediateCorrection").getAsBoolean();
        boolean practiceMode = quizObj.get("practiceMode").getAsBoolean();
        double duration = quizObj.get("duration").getAsInt();

        Quiz quiz = new Quiz(-1,authorId,title,creationDate,description,practiceMode,isRandom,duration,immediateCorrection);
        // TODO: save info to DB
        Dao db = (Dao) getServletContext().getAttribute(Dao.DBID);
        db.addQuiz(quiz);
        return quiz.getId();
    }

    private void uploadQuestionsToDB(JsonArray questionArr,int quizId){
        for(int i=0;i<questionArr.size();i++){
            JsonObject jsonObject = (JsonObject) questionArr.get(i);
            String type = jsonObject.get("type").getAsString();
            Class<?> obj = Question.getClass(type);
            try {
                Question question = (Question) obj.getConstructor(JsonObject.class,int.class,int.class,int.class).newInstance(jsonObject,-1,quizId,i);
                System.out.println(question.getType()+" "+question.getScore()+" "+question.getQuizId()+" "+question.getOrderNum());
                //TODO: save question to DB;
                Dao db = (Dao) getServletContext().getAttribute(Dao.DBID);
                db.addQuestion(question);

            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendResponse(HttpServletResponse response) throws IOException {
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
