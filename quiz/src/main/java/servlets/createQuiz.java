package servlets;

import com.google.gson.*;
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
import java.time.LocalDate;
import java.util.Date;

public class createQuiz extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO : if user is not logged in, then redirect to login page
        request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject quizObj = (JsonObject) readObj(request);

        JsonArray questionArr = quizObj.get("questions").getAsJsonArray();
        int quizId = uploadQuizInfo(quizObj,request);
        uploadQuestionsToDB(questionArr,quizId);

        sendResponse(response);
    }

    private int uploadQuizInfo(JsonObject quizObj,HttpServletRequest request) {
        int id = -1;
        int authorId = 1;//TODO ((User) request.getSession().getAttribute("user")).getId();
        Date creationDate = new Date();
        String title = quizObj.get("title").getAsString();
        String description = quizObj.get("description").getAsString();
        boolean isRandom = quizObj.get("isRandom").getAsBoolean();
        boolean immediateCorrection = quizObj.get("immediateCorrection").getAsBoolean();
        boolean practiceMode = quizObj.get("practiceMode").getAsBoolean();
        double duration = quizObj.get("duration").getAsInt();

        Quiz quiz = new Quiz(-1,authorId,title,creationDate,description,practiceMode,isRandom,duration,immediateCorrection);
        // TODO: save info to DB
        return quiz.getId();
    }

    private void uploadQuestionsToDB(JsonArray questionArr,int quizId){
        for(int i=0;i<questionArr.size();i++){
            JsonObject jsonObject = (JsonObject) questionArr.get(i);
            String type = jsonObject.get("type").getAsString();
            Class<?> obj = Question.getClass(type);
            try {
                Question question = (Question) obj.getConstructor(JsonObject.class,int.class,int.class).newInstance(jsonObject,quizId,i);
                System.out.println(question.getType()+" "+question.getScore()+" "+question.getQuizId()+" "+question.getOrderNum());
                //TODO: save question to DB;

            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
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
