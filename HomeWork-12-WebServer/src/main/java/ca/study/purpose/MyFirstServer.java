package ca.study.purpose;

import ca.study.purpose.Servlets.*;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MyFirstServer {
    private final static int PORT = 8080;

    public static void main(String[] args) throws Exception {
        new MyFirstServer().start();
    }

    private void start() throws Exception {
        Server server = createServer(PORT, new HibUserDaoImpl());
        server.start();
        server.join();
    }

    public Server createServer(int port, HibUserDao hibUserDao) throws MalformedURLException {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new UserCreator()), "/usersInfo/userCreator/*");
        context.addServlet(new ServletHolder(new AddUser(hibUserDao)), "/usersInfo/addUser/*");
        context.addServlet(new ServletHolder(new Users(hibUserDao)), "/usersInfo");
        context.addServlet(new ServletHolder(new PublicInfo()), "/publicInfo");
        context.addServlet(new ServletHolder(new PrivateInfo()), "/privateInfo");
        context.addServlet(new ServletHolder(new Data()), "/data/*");

        context.addFilter(new FilterHolder(new SimpleFilter()), "/*", null);

        Server server = new Server(port);
        server.setHandler(new HandlerList(context));

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{createResourceHandler(), createSecurityHandler(context)});
        server.setHandler(handlers);
        return server;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        URL fileDir = MyFirstServer.class.getClassLoader().getResource("static");
        if (fileDir == null) {
            throw new RuntimeException("File Directory not found");
        }
        resourceHandler.setResourceBase(fileDir.getPath());
        return resourceHandler;
    }

    private SecurityHandler createSecurityHandler(ServletContextHandler context) throws MalformedURLException {
        Constraint constraint = new Constraint();
        constraint.setName("auth");
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{"admin"});

        ConstraintMapping mapping1 = new ConstraintMapping();
        mapping1.setPathSpec("/privateInfo/*");
        mapping1.setConstraint(constraint);

        ConstraintMapping mapping2 = new ConstraintMapping();
        mapping2.setPathSpec("/usersInfo/*");
        mapping2.setConstraint(constraint);

        List<ConstraintMapping> mappingList = new ArrayList<>();
        mappingList.add(mapping1);
        mappingList.add(mapping2);

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        //как декодировать стороку с юзером:паролем https://www.base64decode.org/
        security.setAuthenticator(new BasicAuthenticator());

        URL propFile = null;
        File realmFile = new File(".\\realm.properties");
        if (realmFile.exists()) {
            propFile = realmFile.toURI().toURL();
        }
        if (propFile == null) {
            System.out.println("local realm config not found, looking into Resources");
            propFile = MyFirstServer.class.getClassLoader().getResource("realm.properties");
        }

        if (propFile == null) {
            throw new RuntimeException("Realm property file not found");
        }

        security.setLoginService(new HashLoginService("MyRealm", propFile.getPath()));
        security.setHandler(new HandlerList(context));
        security.setConstraintMappings(mappingList);

        return security;
    }
}
