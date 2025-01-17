package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class staticFiles extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        returnStatic(request,response);
    }

    private void returnStatic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getRequestURI();
        String realPath = getServletContext().getRealPath(url.substring(7));

        String mimeType = getServletContext().getMimeType(realPath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);

        File file = new File(realPath);
        response.setContentLength((int) file.length());


        try (FileInputStream fis = new FileInputStream(realPath);
             OutputStream os = response.getOutputStream()) {

            byte[] buffer = new byte[1024];
            while (fis.read(buffer) != -1) {
                os.write(buffer);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404
        }
    }

}
