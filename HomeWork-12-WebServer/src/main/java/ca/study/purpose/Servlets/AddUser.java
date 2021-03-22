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
        if (user.getName() != null || user.getAge() != 0) hibUserDao.create(user);
    }
}