package servlets.quizServlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.quizes.questions.Question;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

@WebServlet("/practise")
public class practise extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Double> results = checkAnswers(request);
        JSONObject json = new JSONObject();

        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).equals(0.0)) {
                json.put(i, 0);

            }else{
                json.put(i, 1);
            }
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
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
}
