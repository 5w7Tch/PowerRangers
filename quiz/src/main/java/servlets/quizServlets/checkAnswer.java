package servlets.quizServlets;

import models.questions.Question;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/checkAnswer")
public class checkAnswer extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        ArrayList<Question> quests = (ArrayList<Question>) request.getSession().getAttribute("questions");
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String string = sb.toString();
        System.out.println(string);
        string = string.substring(1, string.length() - 1);
        String[] ans = string.trim().split(",");
        int questNum = Integer.parseInt(ans[0].trim().split(":")[0]);
        for(int i = 0; i < ans.length; i++) {
            ans[i] = ans[i].trim().split(":")[1];
        }
        JSONObject json = new JSONObject();
        Double score = quests.get(questNum).checkAnswer(ans);
        if(score.equals(quests.get(questNum).getScore())) {
            json.put("res",2);
        }else if (score == 0.0) {
            json.put("res",0);
        }else {
            json.put("res",1);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
