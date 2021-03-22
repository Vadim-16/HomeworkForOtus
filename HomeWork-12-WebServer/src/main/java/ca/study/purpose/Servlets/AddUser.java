package ca.study.purpose.Servlets;

import ca.study.purpose.HibUser;
import ca.study.purpose.HibUserDao;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUser extends HttpServlet {
    private final HibUserDao hibUserDao;

    public AddUser(HibUserDao hibUserDao) {
        this.hibUserDao = hibUserDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/usersInfo");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (request != null) {
            String[] params = request.getQueryString().split("&");
            HibUser user = new HibUser();
            for (String param : params) {
                if (param.startsWith("name") && !param.endsWith("=")) {
                    String name = param.split("=")[1];
                    user.setName(name);
                }
                if (param.startsWith("age") && !param.endsWith("=")) {
                    String age = param.split("=")[1];
                    try {
                        user.setAge(Long.parseLong(age));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return;
                    }
                }
            }
            if (user.getName() != null && user.getAge() != 0) hibUserDao.create(user);
        }
    }
}