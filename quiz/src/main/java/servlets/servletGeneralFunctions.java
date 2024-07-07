package servlets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class servletGeneralFunctions {
    public static JsonElement readObj(HttpServletRequest request){
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

    public static void saveLoginCookies(HttpServletRequest request, HttpServletResponse response, String name,String password){
        Cookie userNameCookie = new Cookie("username",name);
        Cookie userPasswordCookie = new Cookie("password",password);

        int maxAge = 60*60;

        userNameCookie.setMaxAge(maxAge);
        userPasswordCookie.setMaxAge(maxAge);

        response.addCookie(userNameCookie);
        response.addCookie(userPasswordCookie);
    }

    public static String[] readLoginCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            String[] userInfo = new String[2];
            String name = "";
            String password = "";
            for (Cookie cookie : cookies){
                switch (cookie.getName()){
                    case "username":
                        name = cookie.getValue();
                        break;
                    case "password":
                        password = cookie.getValue();
                        break;
                }
            }

            if(!name.isEmpty() && !password.isEmpty()){
                userInfo[0]=name;
                userInfo[1]=password;
                return userInfo;
            }

        }

        return null;
    }

    public static void removeLoginCookies(HttpServletRequest request, HttpServletResponse response) {
        for(Cookie cookie : request.getCookies()){
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
}
