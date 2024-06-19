package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class staticFiles extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("static");
        returnStatic(request,response);
    }

    private void returnStatic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(request.getContentType());
        String realPath = getServletContext().getRealPath("/icons/error404.jpg");
        try (FileInputStream fis = new FileInputStream(realPath);
             OutputStream os = response.getOutputStream()) {

            byte[] buffer = new byte[1024];
            while (fis.read(buffer) != -1) {
                os.write(buffer);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
        }
    }

}
