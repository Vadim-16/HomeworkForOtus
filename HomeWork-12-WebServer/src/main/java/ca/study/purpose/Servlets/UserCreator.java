package ca.study.purpose.Servlets;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;


public class UserCreator extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(request);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        Path src = Path.of(".\\HomeWork-12-WebServer\\src\\main\\resources\\userCreator");
        String html = Files.readString(src);
        printWriter.print(html);
        printWriter.flush();
    }
}