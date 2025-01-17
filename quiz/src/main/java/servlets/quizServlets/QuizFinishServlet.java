package servlets.quizServlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.DAO.Dao;
import models.USER.WritenQuiz;
import models.achievement.UserAchievement;
import models.quizes.Quiz;
import models.USER.User;
import models.quizes.questions.Question;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Collections;
import java.util.Iterator;

@WebServlet("/finished")
public class QuizFinishServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date finishDate = new Date(System.currentTimeMillis());
        Date startDate = (Date) request.getSession(false).getAttribute("startTime");

        long time1 = finishDate.getTime();
        long time2 = startDate.getTime();


        double differenceInMinutes = Math.abs((time1-time2)/(1000.0 * 60.0));
        JSONObject json = new JSONObject();
        String practise = (String)request.getSession().getAttribute("practise");
        DecimalFormat df = new DecimalFormat("#.00");

        request.getSession().setAttribute("timeSpent", df.format(differenceInMinutes));
        request.getSession().setAttribute("startTime", null);

        if(differenceInMinutes>((Quiz)request.getSession(false).getAttribute("quiz")).getDuration()+1){
            json.put("bad", 1);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
        } else {
            ArrayList<Double> results = checkAnswers(request);
            json.put("bad", 0);
            request.getSession().setAttribute("results", results);
            Double score = 0.0;
            for (int i = 0; i < results.size(); i++) {
                score += results.get(i);
            }

            request.getSession().setAttribute("score", df.format(score));

            try {
                remember(score, results, startDate, differenceInMinutes, request);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
        }
    }

    private void remember(Double score, ArrayList<Double> results, Date startDate, Double time, HttpServletRequest request) throws SQLException {
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        Quiz quiz = (Quiz)request.getSession().getAttribute("quiz");
        User user = (User)request.getSession().getAttribute("user");
        db.insertIntoQuizHistory(((Integer)quiz.getId()).toString(), user.getId().toString(), startDate, time, score);
        tenQuizzesAchievement(user, db);
        highestScoreAchievement(user, quiz, db);
    }

    private ArrayList<Double> checkAnswers(HttpServletRequest request) throws IOException {
        ArrayList<Question> quests = (ArrayList<Question>) request.getSession().getAttribute("questions");
        ArrayList<Double> results = new ArrayList<>(Collections.nCopies(quests.size(), 0.0));
        Double totalScore = 0.0;
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonString = sb.toString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObject = mapper.readTree(jsonString);
        Iterator<String> fieldNames = jsonObject.fieldNames();
        ArrayList<String[]> answerCollections = new ArrayList<>();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            String answers = jsonObject.get(fieldName).toString();
            String stringWithoutBrackets = answers.substring(1, answers.length() - 1);
            String[] parts = stringWithoutBrackets.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim().replaceAll("^\"|\"$", "");
            }
            answerCollections.add(parts);
            Double score = quests.get(Integer.parseInt(fieldName)).checkAnswer(parts);
            results.set(Integer.parseInt(fieldName), score);
            totalScore += quests.get(Integer.parseInt(fieldName)).getScore();
        }

        request.getSession().setAttribute("realScore", totalScore);
        request.getSession().setAttribute("answerCollection", answerCollections);

        return results;
    }

    private void highestScoreAchievement(User user, Quiz quiz, Dao db) throws SQLException {
        Date currentTimestamp = new Date(System.currentTimeMillis());
        int userId = user.getId();
        int quizId = quiz.getId();
        int achievementId = db.getAchievementIdFromType(4);
        ArrayList<WritenQuiz> quizHistory = db.getQuizHistory(quizId);
        WritenQuiz highestScoreUser= quizHistory.get(0);
        if(highestScoreUser.getUserId() == userId){
            UserAchievement userAchievement = new UserAchievement(0, userId, achievementId, currentTimestamp);
            db.putUserAchievements(userAchievement);
        }
    }

    private void tenQuizzesAchievement(User user, Dao db) throws SQLException {
        Date currentTimestamp = new Date(System.currentTimeMillis());
        int userId = user.getId();
        int writtenQuizzesQuantity = db.getUserQuizActivity(userId).size();
        int achievementId = db.getAchievementIdFromType(3);
        if(writtenQuizzesQuantity == 10) {
            UserAchievement userAchievement = new UserAchievement(0, userId, achievementId, currentTimestamp);
            db.putUserAchievements(userAchievement);
        }
    }

    private void practiceQuizAchievement(HttpServletRequest request) throws SQLException {
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        User user = (User)request.getSession().getAttribute("user");
        Date currentTimestamp = new Date(System.currentTimeMillis());
        int userId = user.getId();
        int achievementId = db.getAchievementIdFromType(5);
        UserAchievement userAchievement = new UserAchievement(0, userId, achievementId, currentTimestamp);
        db.putUserAchievements(userAchievement);
    }

}
