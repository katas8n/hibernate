package com.example.util;

import com.example.dao.UserDAO;
import com.example.model.User;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.example.servlets.HomeServlet;
import com.example.servlets.AboutUs;


public class JettyRunner {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        UserDAO userDAO = new UserDAO();

        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");

        userDAO.saveUser(user);

        context.addServlet(new ServletHolder(new HomeServlet()), "/home");
        context.addServlet(new ServletHolder(new AboutUs()), "/about-us");

        server.setHandler(context);

        server.start();
        server.join();
    }
}
