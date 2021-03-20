package ca.study.purpose.Servlets;

import ca.study.purpose.HibUser;
import ca.study.purpose.HibUserDaoImpl;
import com.google.gson.Gson;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Users extends HttpServlet {
    private HibUserDaoImpl hibUserDaoImpl;

    public Users(HibUserDaoImpl hibUserDaoImpl) {
        this.hibUserDaoImpl = hibUserDaoImpl;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<html>\n<head><title>Users table</title></head>  <h1>Users Table</h1>\n" +
                "    <table>\n      <tr>\n        <th>Id</th>\n        <th>Name</th>\n        <th>Age</th>\n      </tr>\n");

        Session session = hibUserDaoImpl.getSessionFactory().openSession();
        CriteriaQuery<HibUser> query = session.getCriteriaBuilder().createQuery(HibUser.class);
        query.from(HibUser.class);
        List<HibUser> resultList = session.createQuery(query).getResultList();

        for (HibUser hibUser : resultList) {
            responseBuilder.append("      <tr>\n        <td>").append(hibUser.getId()).append("</td>\n")
                    .append("        <td>").append(hibUser.getName()).append("</td>\n")
                    .append("        <td>").append(hibUser.getAge()).append("</td>\n      </tr>\n");
        }
        responseBuilder.append("    </table><br><br>");

        responseBuilder.append("<form action=\"/userCreator.html\">\n" +
                "  <input type=\"submit\" value=\"add user\">\n" +
                "</form>" +
                "</html>");

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(responseBuilder.toString());
        printWriter.flush();
    }
}