package servlets.quizServlets;

import com.google.gson.*;
import models.DAO.Dao;
import models.USER.User;
import models.achievement.UserAchievement;
import models.quizes.Quiz;
import models.quizes.questions.Question;
import servlets.servletGeneralFunctions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Date;

public class createQuiz extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("user")==null){
            response.sendRedirect("/");
            return;
        }

        request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject quizObj = (JsonObject) servletGeneralFunctions.readObj(request);

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

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject quizObj = (JsonObject) servletGeneralFunctions.readObj(request);
        Quiz quiz = readQuizInfo(quizObj,request);
        quiz.setId(Integer.parseInt(request.getParameter("quizId")));
        Dao db = (Dao) getServletContext().getAttribute(Dao.DBID);
        try {
            db.updateQuiz(quiz);
            db.deleteQuestions(quiz.getId());
            JsonArray questionArr = quizObj.get("questions").getAsJsonArray();
            uploadQuestionsToDB(questionArr,quiz.getId());
            db.clearQuizHistory(""+quiz.getId());
        } catch (SQLException e) {
            // todo: erise 500 error
            throw new RuntimeException(e);
        }
        sendResponse(response);
    }

    private int uploadQuizInfo(JsonObject quizObj,HttpServletRequest request) throws SQLException {
        Quiz quiz = readQuizInfo(quizObj,request);
        Dao db = (Dao) getServletContext().getAttribute(Dao.DBID);
        db.addQuiz(quiz);
        createQuizzesAchievements(quiz, db);
        return quiz.getId();
    }

    private Quiz readQuizInfo(JsonObject quizObj,HttpServletRequest request){
        int id = -1;
        int authorId = ((User) request.getSession().getAttribute("user")).getId();
        Date creationDate = new Date(System.currentTimeMillis());
        String title = quizObj.get("title").getAsString();
        String description = quizObj.get("description").getAsString();
        boolean isRandom = quizObj.get("isRandom").getAsBoolean();
        boolean immediateCorrection = quizObj.get("immediateCorrection").getAsBoolean();
        boolean practiceMode = quizObj.get("practiceMode").getAsBoolean();
        double duration = quizObj.get("duration").getAsDouble();

        return new Quiz(id,authorId,title,creationDate,description,practiceMode,isRandom,duration,immediateCorrection);
    }

    private void uploadQuestionsToDB(JsonArray questionArr,int quizId){
        for(int i=0;i<questionArr.size();i++){
            JsonObject jsonObject = (JsonObject) questionArr.get(i);
            String type = jsonObject.get("type").getAsString();
            Class<?> obj = Question.getClass(type);
            try {
                Question question = (Question) obj.getConstructor(JsonObject.class,int.class,int.class,int.class).newInstance(jsonObject,-1,quizId,i);
                Dao db = (Dao) getServletContext().getAttribute(Dao.DBID);
                db.addQuestion(question);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | SQLException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObject object = new JsonObject();
        object.addProperty("status","Its OK");
        response.getWriter().write(object.toString());
        response.getWriter().flush();
    }

    private void createQuizzesAchievements(Quiz quiz, Dao db) throws SQLException {
        int userId = quiz.getAuthor();
        Date currentTimestamp = new Date(System.currentTimeMillis());
        int createQuizzesQuantity = db.getUserCreatedQuizzes(userId).size();

        int achievementId;
        UserAchievement userAchievement;
        switch (createQuizzesQuantity) {
            case 1:
                achievementId = db.getAchievementIdFromType(0);
                userAchievement = new UserAchievement(0, userId, achievementId, currentTimestamp);
                db.putUserAchievements(userAchievement);
                break;
            case 5:
                achievementId = db.getAchievementIdFromType(1);
                userAchievement = new UserAchievement(0, userId, achievementId, currentTimestamp);
                db.putUserAchievements(userAchievement);
                break;
            case 10:
                achievementId = db.getAchievementIdFromType(2);
                userAchievement = new UserAchievement(0, userId, achievementId, currentTimestamp);
                db.putUserAchievements(userAchievement);
                break;
            default:
                break;
        }
    }
}
