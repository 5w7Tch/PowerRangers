package servlets;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;
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
}
