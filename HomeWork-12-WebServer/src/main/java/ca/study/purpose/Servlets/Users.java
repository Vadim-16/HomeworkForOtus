package ca.study.purpose.Servlets;

import ca.study.purpose.HibUser;
import ca.study.purpose.HibUserDao;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Users extends HttpServlet {
    private final HibUserDao userDao;

    public Users(HibUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestURI = request.getRequestURI();
        String resultHtml = null;
        if (requestURI.equals("/usersInfo")) {
            resultHtml = buildUsersTableHtml();
        }
        if (requestURI.equals("/usersInfo/userCreator/")) {
            resultHtml = buildUserCreatorHtml();
        }
        if (resultHtml != null) {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter printWriter = response.getWriter();
            printWriter.print(resultHtml);
            printWriter.flush();
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HibUser user = new HibUser();
        String name1 = request.getParameter("name");
        user.setName(name1);
        String age1 = request.getParameter("age");
        try {
            user.setAge(Long.parseLong(age1));
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
        if (user.getName() != null || user.getAge() != 0) userDao.create(user);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/usersInfo");
    }

    private String buildUserCreatorHtml() throws IOException {
        Path src = Path.of(".\\HomeWork-12-WebServer\\src\\main\\resources\\userCreator");
        return Files.readString(src);
    }

    private String buildUsersTableHtml() {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<html>\n<head><title>Users table</title></head>  <h1>Users Table</h1>\n" +
                "    <table>\n      <tr>\n        <th>Id</th>\n        <th>Name</th>\n        <th>Age</th>\n      </tr>\n");
        List<HibUser> resultList = userDao.getHibUsers();
        for (HibUser hibUser : resultList) {
            responseBuilder.append("      <tr>\n        <td>").append(hibUser.getId()).append("</td>\n")
                    .append("        <td>").append(hibUser.getName()).append("</td>\n")
                    .append("        <td>").append(hibUser.getAge()).append("</td>\n      </tr>\n");
        }
        responseBuilder.append("    </table><br><br>");
        responseBuilder.append("<form action=\"/usersInfo/userCreator/\">\n" +
                "  <input type=\"submit\" value=\"add user\">\n" +
                "</form>" +
                "</html>");
        return responseBuilder.toString();
    }

}