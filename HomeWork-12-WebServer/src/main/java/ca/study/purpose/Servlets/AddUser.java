package ca.study.purpose.Servlets;

import ca.study.purpose.HibUser;
import ca.study.purpose.HibUserDaoImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUser extends HttpServlet {
    private HibUserDaoImpl hibUserDaoImpl;

    public AddUser(HibUserDaoImpl hibUserDaoImpl) {
        this.hibUserDaoImpl = hibUserDaoImpl;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] params = request.getQueryString().split("&");
        String name = params[0].split("=")[1];
        String age = params[1].split("=")[1];

        HibUser user = new HibUser();
        user.setName(name);
        user.setAge(Long.parseLong(age));
        hibUserDaoImpl.create(user);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        response.sendRedirect("/usersInfo");

    }
}